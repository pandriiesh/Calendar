package com.home.datastore;

public enum PeriodDayOfWeek {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public int getValue() {

        int value = 0;

        switch(this) {
            case SUNDAY:
                value = 1;
                break;
            case MONDAY:
                value = 2;
                break;
            case TUESDAY:
                value = 3;
                break;
            case WEDNESDAY:
                value = 4;
                break;
            case THURSDAY:
                value = 5;
                break;
            case FRIDAY:
                value = 6;
                break;
            case SATURDAY:
                value = 7;
                break;
        }

        return value;
    }
}
