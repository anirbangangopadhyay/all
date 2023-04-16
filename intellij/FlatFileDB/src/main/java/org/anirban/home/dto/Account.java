package org.anirban.home.dto;

import org.anirban.home.enums.AccountType;
import org.anirban.home.enums.CreditMode;
import org.anirban.home.enums.Institution;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Account {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy|MM|dd");
    private String accountNumber;
    private AccountType accountType;
    private Person firstHolder;
    private Person secondHolder;
    private Person thirdHolder;
    private Person nominee;
    private Institution institution;
    private String location;
    private BigDecimal principalAmount;
    private CreditMode mode;
    private BigDecimal interestAmount;
    private Date startDate;
    private Date endDate;
    private Double interestPercent;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setAccountType(String accountTypeStr) {
        this.accountType = AccountType.valueOf(accountTypeStr);
    }

    public Person getFirstHolder() {
        return firstHolder;
    }

    public void setFirstHolder(Person firstHolder) {
        this.firstHolder = firstHolder;
    }

    public Person getSecondHolder() {
        return secondHolder;
    }

    public void setSecondHolder(Person secondHolder) {
        this.secondHolder = secondHolder;
    }

    public Person getThirdHolder() {
        return thirdHolder;
    }

    public void setThirdHolder(Person thirdHolder) {
        this.thirdHolder = thirdHolder;
    }

    public Person getNominee() {
        return nominee;
    }

    public void setNominee(Person nominee) {
        this.nominee = nominee;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public void setInstitution(String institutionStr) {
        this.institution = Institution.valueOf(institutionStr);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public void setPrincipalAmount(String principalAmountStr) {
        this.principalAmount = principalAmountStr == null ? null : new BigDecimal(principalAmountStr);
    }

    public CreditMode getMode() {
        return mode;
    }

    public void setMode(CreditMode mode) {
        this.mode = mode;
    }

    public void setMode(String modeStr) {
        this.mode = CreditMode.valueOf(modeStr);
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public void setInterestAmount(String interestAmountStr) {
        this.interestAmount = interestAmountStr == null ? null : new BigDecimal(interestAmountStr);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String startDateStr) {
        this.startDate = getDate(startDateStr);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String endDateStr) {
        this.endDate = getDate(endDateStr);
    }

    public Double getInterestPercent() {
        return interestPercent;
    }

    public void setInterestPercent(Double interestPercent) {
        this.interestPercent = interestPercent;
    }

    public void setInterestPercent(String interestPercentStr) {
        this.interestPercent = interestPercentStr == null ? null : Double.parseDouble(interestPercentStr);
    }

    protected Date getDate(String dateStr) {
        LocalDateTime localDateTime = LocalDate.from(DATE_TIME_FORMATTER.parse(dateStr)).atStartOfDay();
        return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(getAccountNumber(), account.getAccountNumber()) && getAccountType() == account.getAccountType() && Objects.equals(getFirstHolder(), account.getFirstHolder()) && Objects.equals(getSecondHolder(), account.getSecondHolder()) && Objects.equals(getThirdHolder(), account.getThirdHolder()) && Objects.equals(getNominee(), account.getNominee()) && getInstitution() == account.getInstitution() && Objects.equals(getLocation(), account.getLocation()) && Objects.equals(getPrincipalAmount(), account.getPrincipalAmount()) && getMode() == account.getMode() && Objects.equals(getInterestAmount(), account.getInterestAmount()) && Objects.equals(getStartDate(), account.getStartDate()) && Objects.equals(getEndDate(), account.getEndDate()) && Objects.equals(getInterestPercent(), account.getInterestPercent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getAccountType(), getFirstHolder(), getSecondHolder(), getThirdHolder(), getNominee(), getInstitution(), getLocation(), getPrincipalAmount(), getMode(), getInterestAmount(), getStartDate(), getEndDate(), getInterestPercent());
    }

    @Override
    public String toString() {
        return "Account{" +
                " accountNumber=" + accountNumber +
                ", accountType=" + accountType +
                ", firstHolder=" + firstHolder +
                ", secondHolder=" + secondHolder +
                ", thirdHolder=" + thirdHolder +
                ", nominee=" + nominee +
                ", institution=" + institution +
                ", location=" + location +
                ", principalAmount=" + principalAmount +
                ", mode=" + mode +
                ", interestAmount=" + interestAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", interestPercent=" + interestPercent +
                '}';
    }
}
