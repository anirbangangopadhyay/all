package org.anirban.home;

import org.anirban.home.dto.Account;
import org.anirban.home.dto.Person;
import org.anirban.home.duckdb.DuckDBCSVUtil;
import org.anirban.home.duckdb.embedded.DuckDBEmbeddedUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Investment {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
    }

    private static final Investment INVESTMENT = new Investment();

    public static void main( String[] args ) {
        try {
            Map<String, Person> personData = INVESTMENT.getPersonData();
            Map<String, Account> accountData = INVESTMENT.getAccountData(personData);
            Map<Calendar, BigDecimal> interests = INVESTMENT.calculateMonthlyInterestDates(accountData.values().iterator().next());
            for (Map.Entry<Calendar, BigDecimal> entry : interests.entrySet()) {
                System.out.println(entry.getKey().toInstant() + " = " + entry.getValue());
            }
        } finally {
            DuckDBEmbeddedUtil.closeConnection();
        }
    }

    protected Map<Calendar, BigDecimal> calculateInterests(Account account) {
        return switch (account.getMode()) {
            case YEARLY -> calculateYearlyInterests(account);
            case HALF_YEARLY -> calculateHalfYearlyInterestDates(account);
            case QUARTERLY -> calculateQuarterlyInterestDates(account);
            case MONTHLY -> calculateMonthlyInterestDates(account);
        };
    }

    protected Map<Calendar, BigDecimal> calculateYearlyInterests(Account account) {
        LinkedHashMap<Calendar, BigDecimal> interests = new LinkedHashMap<>();
        Calendar startDate = createCalendar(account.getStartDate());
        Calendar endDate = createCalendar(account.getEndDate());
        Calendar interestDate = (Calendar) startDate.clone();
        while (interestDate.before(endDate)) {
            interestDate.add(Calendar.YEAR, 1);
            if(interestDate.before(endDate)) {
                interests.put((Calendar) interestDate.clone(), account.getInterestAmount());
            }
        }
        Map.Entry<Calendar, BigDecimal> lastEntry = getLastEntry(interests);
        long between = ChronoUnit.DAYS.between(lastEntry.getKey().toInstant(), endDate.toInstant());
        interests.put((Calendar) endDate.clone(), account.getInterestAmount().divide(new BigDecimal(endDate.getActualMaximum(Calendar.DAY_OF_YEAR)), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
        return interests;
    }

    protected Map<Calendar, BigDecimal> calculateMonthlyInterestDates(Account account) {
        LinkedHashMap<Calendar, BigDecimal> interests = new LinkedHashMap<>();
        Calendar startDate = createCalendar(account.getStartDate());
        Calendar endDate = createCalendar(account.getEndDate());
        Calendar interestDate = (Calendar) startDate.clone();
        while (interestDate.before(endDate)) {
            interestDate.add(Calendar.MONTH, 1);
            interestDate.set(Calendar.DAY_OF_MONTH, 1);
            if(interestDate.before(endDate)) {
                if(interests.size() == 0) {
                    long between = ChronoUnit.DAYS.between(startDate.toInstant(), interestDate.toInstant());
                    interests.put((Calendar) interestDate.clone(), account.getInterestAmount().divide(new BigDecimal(startDate.getActualMaximum(Calendar.DAY_OF_MONTH)), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
                } else {
                    interests.put((Calendar) interestDate.clone(), account.getInterestAmount());
                }
            }
        }

        Map.Entry<Calendar, BigDecimal> lastEntry = getLastEntry(interests);
        long between = ChronoUnit.DAYS.between(lastEntry.getKey().toInstant(), endDate.toInstant());
        interests.put((Calendar) endDate.clone(), account.getInterestAmount().divide(new BigDecimal(endDate.getActualMaximum(Calendar.DAY_OF_MONTH)), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
        return interests;
    }

    protected Map<Calendar, BigDecimal> calculateQuarterlyInterestDates(Account account) {
        LinkedHashMap<Calendar, BigDecimal> interests = new LinkedHashMap<>();
        Calendar startDate = createCalendar(account.getStartDate());
        Calendar endDate = createCalendar(account.getEndDate());
        Calendar interestDate = (Calendar) startDate.clone();
        while (interestDate.before(endDate)) {
            interestDate.set(Calendar.DAY_OF_MONTH, 1);
            interestDate.set(Calendar.MONTH, interestDate.get(Calendar.MONTH)/3 * 3 + 2);
            interestDate.set(Calendar.DAY_OF_MONTH, interestDate.getActualMaximum(Calendar.DAY_OF_MONTH));
            interestDate.add(Calendar.DAY_OF_MONTH, 1);
            if(interestDate.before(endDate)) {
                if(interests.size() == 0) {
                    long between = ChronoUnit.DAYS.between(startDate.toInstant(), interestDate.toInstant());
                    // TODO quarter total days calculations
                    interests.put((Calendar) interestDate.clone(), account.getInterestAmount().divide(new BigDecimal(91), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
                } else {
                    interests.put((Calendar) interestDate.clone(), account.getInterestAmount());
                }
            }
        }

        Map.Entry<Calendar, BigDecimal> lastEntry = getLastEntry(interests);
        long between = ChronoUnit.DAYS.between(lastEntry.getKey().toInstant(), endDate.toInstant());
        // TODO quarter total days calculations
        interests.put((Calendar) endDate.clone(), account.getInterestAmount().divide(new BigDecimal(91), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
        return interests;
    }

    protected Map<Calendar, BigDecimal> calculateHalfYearlyInterestDates(Account account) {
        LinkedHashMap<Calendar, BigDecimal> interests = new LinkedHashMap<>();
        Calendar startDate = createCalendar(account.getStartDate());
        Calendar endDate = createCalendar(account.getEndDate());
        Calendar interestDate = (Calendar) startDate.clone();
        while (interestDate.before(endDate)) {
            interestDate.set(Calendar.DAY_OF_MONTH, 1);
            interestDate.set(Calendar.MONTH, interestDate.get(Calendar.MONTH)/6 * 6 + 5);
            interestDate.set(Calendar.DAY_OF_MONTH, interestDate.getActualMaximum(Calendar.DAY_OF_MONTH));
            interestDate.add(Calendar.DAY_OF_MONTH, 1);
            if(interestDate.before(endDate)) {
                if(interests.size() == 0) {
                    long between = ChronoUnit.DAYS.between(startDate.toInstant(), interestDate.toInstant());
                    // TODO half yearly total days calculations
                    interests.put((Calendar) interestDate.clone(), account.getInterestAmount().divide(new BigDecimal(183), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
                } else {
                    interests.put((Calendar) interestDate.clone(), account.getInterestAmount());
                }
            }
        }
        Map.Entry<Calendar, BigDecimal> lastEntry = getLastEntry(interests);
        long between = ChronoUnit.DAYS.between(lastEntry.getKey().toInstant(), endDate.toInstant());
        // TODO half yearly total days calculations
        interests.put((Calendar) endDate.clone(), account.getInterestAmount().divide(new BigDecimal(183), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(between)));
        return interests;
    }

    private Map.Entry<Calendar, BigDecimal> getLastEntry(LinkedHashMap<Calendar, BigDecimal> interests) {
        Iterator<Map.Entry<Calendar, BigDecimal>> iterator = interests.entrySet().iterator();
        Map.Entry<Calendar, BigDecimal> last = null;
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return last;
    }

    protected Calendar createCalendar(Date startDate) {
        Calendar startDateCal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
        startDateCal.set(Calendar.HOUR_OF_DAY, 0);
        startDateCal.set(Calendar.MINUTE, 0);
        startDateCal.set(Calendar.SECOND, 0);
        startDateCal.set(Calendar.MILLISECOND, 0);
        startDateCal.setTimeInMillis(startDate.getTime());
        return startDateCal;
    }

    protected Map<String, Account> getAccountData(Map<String, Person> personData) {
        Map<String, Account> data = new HashMap<>();
        Connection connection = DuckDBEmbeddedUtil.getConnection();
        String query = String.format("Select AccountNumber,Type,FirstHolder,SecondHolder,ThirdHolder,Nominee,Institution,Location,PrincipalAmount,Mode,InterestAmount,StartDate,EndDate,InterestPercent " +
                "from read_csv('%s', delim=',', header=True, columns={'AccountNumber': 'VARCHAR', 'Type': 'VARCHAR', 'FirstHolder': 'VARCHAR', 'SecondHolder': 'VARCHAR', 'ThirdHolder': 'VARCHAR', 'Nominee': 'VARCHAR', 'Institution': 'VARCHAR', 'Location': 'VARCHAR', 'PrincipalAmount': 'VARCHAR', 'Mode': 'VARCHAR', 'InterestAmount': 'VARCHAR', 'StartDate': 'VARCHAR', 'EndDate': 'VARCHAR', 'InterestPercent': 'VARCHAR'}) " +
                "order by FirstHolder nulls first", DuckDBCSVUtil.getTable("Account.csv"));
        System.out.println("Running query: " + query);
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            try(ResultSet resultSet = pstmt.executeQuery()) {
                while(resultSet != null && resultSet.next()) {
                    Account account = new Account();
                    account.setAccountNumber(resultSet.getString("AccountNumber"));
                    account.setAccountType(resultSet.getString("Type"));
                    account.setFirstHolder(personData.get(resultSet.getString("FirstHolder")));
                    account.setSecondHolder(personData.get(resultSet.getString("SecondHolder")));
                    account.setThirdHolder(personData.get(resultSet.getString("ThirdHolder")));
                    account.setNominee(personData.get(resultSet.getString("Nominee")));
                    account.setInstitution(resultSet.getString("Institution"));
                    account.setLocation(resultSet.getString("Location"));
                    account.setPrincipalAmount(resultSet.getString("PrincipalAmount"));
                    account.setMode(resultSet.getString("Mode"));
                    account.setInterestAmount(resultSet.getString("InterestAmount"));
                    account.setStartDate(resultSet.getString("StartDate"));
                    account.setEndDate(resultSet.getString("EndDate"));
                    account.setInterestPercent(resultSet.getString("InterestPercent"));
                    data.put(account.getAccountNumber(), account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    protected Map<String, Person> getPersonData() {
        Map<String, Person> data = new HashMap<>();
        Connection connection = DuckDBEmbeddedUtil.getConnection();
        String query = String.format("Select Id, Name, AgeCategory, LinkedAdult " +
                "from read_csv('%s', delim=',', header=True, columns={'Id': 'VARCHAR', 'Name': 'VARCHAR', 'AgeCategory': 'VARCHAR', 'LinkedAdult': 'VARCHAR'}) " +
                "order by LinkedAdult nulls first", DuckDBCSVUtil.getTable("Person.csv"));
        System.out.println("Running query: " + query);
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            try(ResultSet resultSet = pstmt.executeQuery()) {
                while(resultSet != null && resultSet.next()) {
                    Person person = new Person();
                    person.setId(resultSet.getString("Id"));
                    person.setName(resultSet.getString("Name"));
                    person.setAgeCategory(resultSet.getString("AgeCategory"));
                    person.setLinkedAdult(data.get(resultSet.getString("LinkedAdult")));
                    data.put(person.getId(), person);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
