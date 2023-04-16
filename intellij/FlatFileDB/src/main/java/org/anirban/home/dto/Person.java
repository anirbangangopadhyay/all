package org.anirban.home.dto;

import org.anirban.home.enums.AgeCategory;

import java.util.Objects;

public class Person {
    private String id;
    private String name;
    private AgeCategory ageCategory;
    private Person linkedAdult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public void setAgeCategory(String ageCategoryStr) {
        this.ageCategory = AgeCategory.valueOf(ageCategoryStr);
    }

    public Person getLinkedAdult() {
        return linkedAdult;
    }

    public void setLinkedAdult(Person linkedAdult) {
        this.linkedAdult = linkedAdult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ageCategory=" + ageCategory +
                ", linkedAdult=" + linkedAdult +
                '}';
    }
}
