public class Date {
    protected int currentYear;
    protected int currentMonth;
    protected int currentDay;

    //default constructor, does nothing
    public Date(){}

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
            if(currentDay + 1 <= getNumberOfDaysInMonth(currentYear, currentMonth)) {
                //if it wont, then just adds a day to the current day
                currentDay +=1;
            }
            //else if adding a day will go over a month
            else if(currentDay + 1 > getNumberOfDaysInMonth(currentYear, currentMonth)) {
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
                currentDay = getNumberOfDaysInMonth(currentYear, currentMonth);
            }
        }
    }

    //method that calls is leap year with the current year
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
        return getMonthName(this.currentMonth);
    }

    //just gives the current month
    public int getCurrentMonth() {
        return this.currentMonth;
    }

    //just gives the current year
    public int getCurrentYear() {
        return this.currentYear;
    }

    //just gives the current day
    public int getCurrentDayOfMonth() {
        return this.currentDay;
    }

    //isLeapYear function which just returns false, simply here to make things work in other functions in date
    private static boolean isLeapYear(int year) {
        return false;
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
                if (isLeapYear(year)) {
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