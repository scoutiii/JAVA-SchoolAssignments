import org.junit.Assert;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class JulianDateTest {
    /**
     * @brief Tests that the default constructor correctly calculates today
     */
    @org.junit.Test
    public void JulianDateTestDefaultConstructor() {
        JulianDate date = new JulianDate();

        GregorianCalendar today = new GregorianCalendar();
        today.setGregorianChange(new java.util.Date(Long.MAX_VALUE));
        Assert.assertEquals(today.get(Calendar.YEAR), date.getCurrentYear());
        Assert.assertEquals(today.get(Calendar.MONTH) + 1, date.getCurrentMonth());
        Assert.assertEquals(today.getDisplayName(Calendar.MONTH, Calendar.LONG, new java.util.Locale("en","US")), date.getCurrentMonthName());
        Assert.assertEquals(today.get(Calendar.DAY_OF_MONTH), date.getCurrentDayOfMonth());
    }

    /**
     * @brief Tests that the constructor correctly sets day, month, and year
     */
    @org.junit.Test
    public void JulianDateTestConstructorWithParameters() {
        int year = 2026;
        int month = 5;
        int day = 15;
        GregorianDate date = new GregorianDate(year, month, day);

        Assert.assertEquals(year, date.getCurrentYear());
        Assert.assertEquals(month, date.getCurrentMonth());
        Assert.assertEquals("May", date.getCurrentMonthName());
        Assert.assertEquals(day, date.getCurrentDayOfMonth());
    }

    /**
     * @brief This test add days from a known date to jump days, months, and years
     */
    @org.junit.Test
    public void JulianDateTestAddDaysMethod() {
        int year = 2016;
        int month = 4;
        int day = 15;

        // Test an increase in days
        for (int i = 0; i < 10; i++) {
            JulianDate date = new JulianDate(year, month, day);
            date.addDays(i);

            Assert.assertEquals(day + i, date.getCurrentDayOfMonth());
            Assert.assertEquals(year, date.getCurrentYear());
            Assert.assertEquals(month, date.getCurrentMonth());
        }

        // Test an increase in months
        for (int i = 0; i < 5; i++) {
            int dayIncrease = i * 28;
            JulianDate date = new JulianDate(year, month, day);
            date.addDays(dayIncrease);

            Assert.assertEquals(year, date.getCurrentYear());
            Assert.assertEquals(month + i, date.getCurrentMonth());
        }

        // Test an increase in years
        for (int i = 0; i < 10; i++) {
            int dayIncrease = i * 365;
            JulianDate date = new JulianDate(year, month, day);
            date.addDays(dayIncrease);

            Assert.assertEquals(year + i, date.getCurrentYear());
            Assert.assertEquals(month, date.getCurrentMonth());
        }
    }


    /**
     * @brief This test subtracts days from a known date to jump days, months, and years
     */
    @org.junit.Test
    public void JulianDateTestSubtractDaysMethod() {
        int year = 2016;
        int month = 12;
        int day = 15;

        // Test an increase in days
        for (int i = 0; i < 10; i++) {
            JulianDate date = new JulianDate(year, month, day);
            date.subtractDays(i);

            Assert.assertEquals(day - i, date.getCurrentDayOfMonth());
            Assert.assertEquals(year, date.getCurrentYear());
            Assert.assertEquals(month, date.getCurrentMonth());
        }

        // Test an increase in months
        for (int i = 0; i < 5; i++) {
            int dayIncrease = i * 28;
            JulianDate date = new JulianDate(year, month, day);
            date.subtractDays(dayIncrease);

            Assert.assertEquals(year, date.getCurrentYear());
            Assert.assertEquals(month - i, date.getCurrentMonth());
        }

        // Test an increase in years
        for (int i = 0; i < 10; i++) {
            int dayIncrease = i * 365;
            JulianDate date = new JulianDate(year, month, day);
            date.subtractDays(dayIncrease);

            Assert.assertEquals(year - i, date.getCurrentYear());
            Assert.assertEquals(month, date.getCurrentMonth());
        }
    }

    /**
     * @brief Tests that the leap year is correctly calculated
     */
    @org.junit.Test
    public void JulianDateTestIsLeapYearMethod() {
        int month = 12;
        int day = 15;

        for (int year = 1; year < 4000; year++) {
            JulianDate date = new JulianDate(year, month, day);

            if (year % 4 == 0) {
                Assert.assertTrue(date.isLeapYear());
            }
            else {
                Assert.assertFalse(date.isLeapYear());
            }
        }
    }
}