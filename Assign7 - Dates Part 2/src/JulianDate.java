public class JulianDate extends Date {
    //the default constructor that gets the current date by doing calculations on the time in milliseconds
    public JulianDate() {
        //defaults by setting the date to 1/1/1, mostly for lolz
        currentDay = 1;
        currentMonth = 1;
        currentYear  = 1;

        //calculates how many days since 1/1/1970 starting with milliseconds, doing division to get to days
        long currentTimeDays = (((((System.currentTimeMillis() + java.util.TimeZone.getDefault().getRawOffset()) /*gets current time and offset */
                / 1000) /*gets millis to seconds  */
                / 60)   /*gets seconds to minutes */
                / 60)   /*gets minutes to hours   */
                / 24);  /*gets hours to days      */

        //this is the number of days since julian 1/1/1 to gregorian 1/1/1970
        long daysUpTo111970 = 719164;

        //this adds that number above ^^ to the current date found with the time
        long currentTimeDaysPlusDaysUpTo111970 = currentTimeDays + daysUpTo111970;

        //a loop that starts from year 1 and goes till the number of days found above, finds the year
        int countDays = 1;
        for(int startYear = 1; countDays < currentTimeDaysPlusDaysUpTo111970; ++startYear) {
            if(isLeapYear(startYear)) {
                countDays += 366;
            }
            else {
                countDays += 365;
            }
            currentYear = startYear;
        }

        //another loop that counts just how many days up to the current year
        int totalDaysFromYears = 0;
        for(int startYear = 1; startYear < currentYear; ++startYear) {
            if(isLeapYear(startYear)) {
                totalDaysFromYears += 366;
            }
            else {
                totalDaysFromYears += 365;
            }
        }

        //this is a big mess of a way to find the current day and month from everything found above
        int daysRemaining     = ((int)currentTimeDaysPlusDaysUpTo111970) - totalDaysFromYears + 1;
        int tempDaysRemaining = daysRemaining;
        int dayCounter        = 1;
        int monthCounter      = 1;
        while(dayCounter <= daysRemaining) {
            if(tempDaysRemaining > getNumberOfDaysInMonth(currentYear, monthCounter)) {
                tempDaysRemaining = tempDaysRemaining - getNumberOfDaysInMonth(currentYear, monthCounter);
                ++monthCounter;
                dayCounter       += getNumberOfDaysInMonth(currentYear, monthCounter);
            }
            else {
                currentMonth = monthCounter;
                currentDay = tempDaysRemaining;
                dayCounter  += getNumberOfDaysInMonth(currentYear, monthCounter);
            }
        }
    }

    //sets the "current" day/month/year, to the given day/month/year
    public JulianDate(int year, int month, int day) {
        currentMonth = month;
        currentYear = year;
        currentDay = day;
    }

    //method that checks if a given year is a leap year for the julian calendar
    private static boolean isLeapYear(int year) {
        //makes a variable to keep track if its a leap year
        boolean isItALeapYear = false;
        //for the julian calendar, if the remainder of the year divided by 4 is 0, then its a leap year
        if(year % 4 == 0) {
            //if so then it sets isleapyear to true
            isItALeapYear = true;
        }
        //returns the true/false found above
        return isItALeapYear;
    }

    //method tha takes a month and a year and returns how many days are in that month (accounts for leap years)
    private static int getNumberOfDaysInMonth(int year, int month) {
        //a switch statement which uses fallthrough to get number of days in month
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year) == true) {
                    return 29;
                }
                else {
                    return 28;
                }

        }
        //a back up return statement in case garbage is passes in
        return 0;
    }

    //calls isLeapYear with the current year
    @Override
    public boolean isLeapYear() {
        return isLeapYear(currentYear);
    }


}