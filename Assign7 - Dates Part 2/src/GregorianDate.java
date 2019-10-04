import java.util.TimeZone;

public class GregorianDate extends Date {

    //sets the current day/month/year to the current day/month/year as calculated by the current time
    public GregorianDate() {
        //by default initializes the day to 1/1/1970, mostly for fun
        currentDay = 1;
        currentMonth = 1;
        currentYear  = 1970;

        //calculates how many days since 1/1/1970 starting with milliseconds, doing division to get to days
        long currentTimeDays = (((((System.currentTimeMillis() + TimeZone.getDefault().getRawOffset()) /*gets current time and offset */
                / 1000) /*gets millis to seconds  */
                / 60)   /*gets seconds to minutes */
                / 60)   /*gets minutes to hours   */
                / 24);  /*gets hours to days      */

        //initializes a counter variable to count how many days per year up till the current day
        int countDays = 0;
        //for loop to increment the year (starting at 1970) till the current day
        for(int startYear = 1970; countDays < currentTimeDays; startYear++) {
            if(isLeapYear(startYear)) {
                countDays += 366;
            }
            else {
                countDays += 365;
            }
            currentYear = startYear;
        }

        //gets another count of how many days since 1/1/1970
        int totalDaysFromYears = 0;
        for(int startYear = 1970; startYear < currentYear; ++startYear) {
            if(isLeapYear(startYear)) {
                totalDaysFromYears += 366;
            }
            else {
                totalDaysFromYears += 365;
            }
        }

        //a rather convoluted way to get the day and month
        int daysRemaining     = ((int)currentTimeDays) - totalDaysFromYears + 1;
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
    public GregorianDate(int year, int month, int day) {
        currentYear  = year;
        currentMonth = month;
        currentDay = day;
    }

    //a method that takes a year and returns true if it is a leap year (gregorian)
    private static boolean isLeapYear(int year) {
        //initializes a bool to false, keeps track if it is a leap year
        boolean isLeapYear = false;
        //checks to see if year is divisible by 400, or divisible by 4 but not divisible by 100
        if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
            //sets to true if true
            isLeapYear = true;
        }
        //returns the bool isLeapYear (I know this whole thing could be one line, oh well ;)
        return isLeapYear;
    }

    //method that takes a year and month and returns how many days in the month
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

    //method that calls is leap year with the current year
    @Override
    public boolean isLeapYear() {
        return isLeapYear(currentYear);
    }
}