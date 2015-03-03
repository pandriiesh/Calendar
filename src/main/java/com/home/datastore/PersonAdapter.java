package com.home.datastore;

import com.home.common.Person;

public class PersonAdapter {

    private String personName;
    private String personEmail;
    private String login;
    private String password;

    public PersonAdapter() {
    }

    public PersonAdapter(Person person) {
        personName = person.getPersonName();
        personEmail = person.getPersonEmail();
        login = person.getLogin();
        password = person.getPassword();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonAdapter that = (PersonAdapter) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (personEmail != null ? !personEmail.equals(that.personEmail) : that.personEmail != null) return false;
        if (personName != null ? !personName.equals(that.personName) : that.personName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personName != null ? personName.hashCode() : 0;
        result = 31 * result + (personEmail != null ? personEmail.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonAdapter{" +
                "personName='" + personName + '\'' +
                ", personEmail='" + personEmail + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
