public class JulianDate {
    private int currentYear;
    private int currentMonth;
    private int currentDay;

    //the default constructor that gets the current date by doing calculations on the time in milliseconds
    JulianDate() {
        //defaults by setting the date to 1/1/1, mostly for lolz
        currentDay   = 1;
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
        long currentTimeDaysPlusdaysUpTo111970 = currentTimeDays + daysUpTo111970;

        //a loop that starts from year 1 and goes till the number of days found above, finds the year
        int countDays = 1;
        for(int startYear = 1; countDays < currentTimeDaysPlusdaysUpTo111970; ++startYear) {
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
        int daysRemaining     = ((int)currentTimeDaysPlusdaysUpTo111970) - totalDaysFromYears + 1;
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
                currentDay   = tempDaysRemaining;
                dayCounter  += getNumberOfDaysInMonth(currentYear, monthCounter);
            }
        }
    }

    //sets the "current" day/month/year, to the given day/month/year
    JulianDate(int year, int month, int day) {
        currentMonth = month;
        currentYear = year;
        currentDay = day;
    }

    //a method that adds a number of days to the current day/month/year
    public void addDays(int days) {
        //makes 2 variables, one is max days so to speak, and the other represents the initial current day
        int newCurrentDay = currentDay + days;
        int tempCurrentDay = currentDay;

        //this loop will run while the temp current days is less then the added days (effectively just how manys days added)
        while(tempCurrentDay < newCurrentDay) {
            //increment temp variable
            tempCurrentDay += 1;
            //checks to see if adding a day will add a month
            if(currentDay + 1 <= getNumberOfDaysInMonth(currentYear,currentMonth)) {
                //if it wont, then just adds a day to the current day
                currentDay +=1;
            }
            //else if adding a day will go over a month
            else if(currentDay + 1 > getNumberOfDaysInMonth(currentYear,currentMonth)) {
                //resets the current day to 1
                currentDay = 1;
                //checks if adding a month will add a year
                if(currentMonth + 1 <= 12) {
                    //if it wont, then it just adds a month to current month
                    currentMonth += 1;
                }
                //else if adding a month will go over a year
                else if(currentMonth + 1 > 12) {
                    //resets months to 1, and adds a year
                    currentMonth = 1;
                    currentYear += 1;
                }
            }
        }
    }

    //a method that subtracts a number of days to the current day/month/year
    public void subtractDays(int days) {
        //makes 2 variables, one keeps track of smallest day so to speak, and the other the "current" day
        int newCurrentDay = currentDay - days;
        int tempCurrentDay = currentDay;

        //runs until tempdays is equal to newdays (effectively running for the number of days give in)
        while(tempCurrentDay > newCurrentDay) {
            //subtracts one day from temp variable
            tempCurrentDay -= 1;
            //checks to see if taking away a day will take away a month
            if(currentDay - 1 >= 1) {
                //if not, it will safely subtract a day from the current day
                currentDay -= 1;
            }
            //else if taking a day away will go into a new month
            else if(currentDay - 1 < 1) {
                //checks to see if going back a month will go back a year
                if(currentMonth - 1 >= 1) {
                    //if not it will safely subtract a month
                    currentMonth -= 1;
                }
                //else if going back a month will go back a year
                else if(currentMonth - 1 < 1) {
                    //sets the new current month to 12, and takes away a year
                    currentYear -= 1;
                    currentMonth = 12;
                }
                //finally with the new year and month worked out,
                //it will set the current day to the number of days in the new month
                currentDay = getNumberOfDaysInMonth(currentYear,currentMonth);
            }
        }
    }

    //calls isLeapYear with the current year
    public boolean isLeapYear() {
        return isLeapYear(currentYear);
    }

    //prints out the current day in form mm/dd/yyyy
    public void printShortDate() {
        System.out.print(currentMonth + "/" + currentDay + "/" + currentYear);
    }

    //prints out the current day in form month dd, yyyy
    public void printLongDate() {
        System.out.print(getCurrentMonthName() + " " + currentDay + ", " + currentYear);
    }

    //method that calls the getmonthname with the current month
    public String getCurrentMonthName() {
        return getMonthName(currentMonth);
    }

    //just gives the current month
    public int getCurrentMonth() {
        return currentMonth;
    }

    //just gives the current year
    public int getCurrentYear() {
        return currentYear;
    }

    //just gives the current day
    public int getCurrentDayOfMonth() {
        return currentDay;
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

    //method that takes a month number and converts it into the month name
    private static String getMonthName(int month) {
        //just a switch statement that returns the month name
        switch (month) {
            case 1:  return "January";
            case 2:  return "February";
            case 3:  return "March";
            case 4:  return "April";
            case 5:  return "May";
            case 6:  return "June";
            case 7:  return "July";
            case 8:  return "August";
            case 9:  return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
        }
        //if given number isn't a month, returns error
        return "Error";
    }
}