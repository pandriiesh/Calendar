package com.home.common;

public class Person {
    private final String personName;
    private final String personEmail;
    private final String login;
    private final String password;

    public Person(Builder builder) {
        personName = builder.personName;
        personEmail = builder.personEmail;
        login = builder.login;
        password = builder.password;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    static class Builder {
        private String personName;
        private String personEmail;
        private String login;
        private String password;

        public Builder() {

        }

        public Builder(Person person) {
            personName = person.personName;
            personEmail = person.personEmail;
            login = person.login;
            password = person.password;
        }

        public Builder personName(String personName) {
            this.personName = personName;
            return this;
        }

        public Builder personEmail(String personEmail) {
            this.personEmail = personEmail;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

}
