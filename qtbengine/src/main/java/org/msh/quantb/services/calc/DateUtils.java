package org.msh.quantb.services.calc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.msh.quantb.services.mvp.Messages;

public class DateUtils {

	/**
	 * Return the number of days between two dates
	 * 
	 * @param a initial date
	 * @param b ending date
	 * @return number of days
	 */
	public static int daysBetween(Date a, Date b) {
		int tempDifference = 0;
		int difference = 0;
		Calendar earlier = Calendar.getInstance();
		Calendar later = Calendar.getInstance();

		if (a.compareTo(b) < 0) {
			earlier.setTime(a);
			later.setTime(b);
		} else {
			earlier.setTime(b);
			later.setTime(a);
		}

		while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
			tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
			difference += tempDifference;

			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
			tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
			difference += tempDifference;

			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		return difference;
	}

	/**
	 * Returns the dt parameter, incremented by <i>numDays</i> days
	 * 
	 * @param dt
	 * @param days
	 * @return
	 */
	static public Date incDays(Date dt, int numDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DAY_OF_MONTH, numDays);

		// fix bug in java... when incrementing in 1 day the date oct 17,2009, the hour information were changed 
		c.set(Calendar.HOUR_OF_DAY, 0);

		return c.getTime();
	}

	/**
	 * Returns the dt parameter, incremented by <i>numMonths</i> months
	 * 
	 * @param dt
	 * @param numMonths
	 * @return
	 */
	static public Date incMonths(Date dt, int numMonths) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, numMonths);
		return c.getTime();
	}

	/**
	 * Returns the dt parameter, incremented by <i>numMonths</i> months
	 * 
	 * @param dt
	 * @param numMonths
	 * @return
	 */
	static public Date incYears(Date dt, int numYears) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.YEAR, numYears);
		return c.getTime();
	}

	/**
	 * Increments a date <code>dt</code> in <code>numHours</code> hours
	 * 
	 * @param dt
	 * @param numHours
	 * @return
	 */
	static public Date incHours(Date dt, int numHours) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.HOUR_OF_DAY, numHours);
		return c.getTime();
	}

	/**
	 * Returns the year part of the date
	 * 
	 * @param dt - Date
	 * @return - year of the date dt
	 */
	static public int yearOf(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.YEAR);
	}

	/**
	 * Returns the year part of the date
	 * 
	 * @param dt - Date
	 * @return - year of the date dt
	 */
	static public int monthOf(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.MONTH);
	}

	/**
	 * Returns the year part of the date
	 * 
	 * @param dt - Date
	 * @return - year of the date dt
	 */
	static public int dayOf(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Return number of hours between 2 dates
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	static public int hoursBetween(Date first, Date second) {

		double milliElapsed = second.getTime() - first.getTime();
		double hoursElapsed = (milliElapsed / 3600F / 1000F);
		return Math.round((Math.round(hoursElapsed * 100F) / 100F));
	}

	/**
	 * Return the number of minutes between two dates
	 * 
	 * @param dtIni initial date
	 * @param dtEnd end date
	 * @return minutes between dtIni and dtEnd
	 */
	static public int minutesBetween(Date dtIni, Date dtEnd) {
		return Math.round(DateUtils.secondsBetween(dtIni, dtEnd) / 60);
	}

	/**
	 * Return the number of seconds between two dates
	 * 
	 * @param dtIni initial date
	 * @param dtEnd end date
	 * @return seconds between the two dates
	 */
	static public int secondsBetween(Date dtIni, Date dtEnd) {
		long milliElapsed = dtIni.getTime() - dtEnd.getTime();
		if (milliElapsed < 0) milliElapsed = -milliElapsed;
		return Math.round(milliElapsed / 1000);
	}

	/**
	 * Returns the number of months between 2 dates
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	static public int monthsBetween(Date dt1, Date dt2) {
		if (dt1.after(dt2)) {
			Date aux = dt1;
			dt1 = dt2;
			dt2 = aux;
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(dt1);
		c2.setTime(dt2);

		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int m = (c2.get(Calendar.MONTH) + (y * 12)) - c1.get(Calendar.MONTH);

		if (c2.get(Calendar.DAY_OF_MONTH) < c1.get(Calendar.DAY_OF_MONTH)) m--;

		return m;
	}

	/**
	 * Return the number of years between two dates
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static int yearsBetween(Date dt1, Date dt2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(dt1);
		c2.setTime(dt2);

		if (c1.compareTo(c2) > 0) {
			Calendar aux = c1;
			c1 = c2;
			c2 = aux;
		}
		int num = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		if (num > 0) {
			if (c1.get(Calendar.DAY_OF_YEAR) > c2.get(Calendar.DAY_OF_YEAR)) num--;
		}

		return num;
	}

	public static String FormatDateTime(String dateFormat, Date dt) {
		SimpleDateFormat f = new SimpleDateFormat(dateFormat);
		return f.format(dt);
	}

	/**
	 * Return the current date without time information
	 * 
	 * @return
	 */
	public static Date getDate() {
		Calendar c = Calendar.getInstance();
		Calendar dtNow = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, dtNow.get(Calendar.YEAR));
		c.set(Calendar.MONTH, dtNow.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, dtNow.get(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * Remove time information of a {@link Date} object
	 * 
	 * @param dt date to remove time information
	 * @return {@link Date} instance without time information
	 */
	public static Date getDatePart(Date dt) {
		Calendar c = Calendar.getInstance();
		Calendar aux = Calendar.getInstance();
		aux.setTime(dt);
		c.clear();
		c.set(Calendar.YEAR, aux.get(Calendar.YEAR));
		c.set(Calendar.MONTH, aux.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_YEAR, aux.get(Calendar.DAY_OF_YEAR));

		return c.getTime();
	}

	/**
	 * Return the day of the week, starting in sun = 1
	 * 
	 * @param dt Date to return the week day
	 * @return week day
	 */
	public static int dayOfWeek(Date dt) {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Calculate the day of the month according to the month, year, week in the
	 * month and weekday
	 * 
	 * @param year
	 * @param month
	 * @param week
	 * @param weekday - 1 is the first day of the week and 7 is the last day of
	 *        the week
	 * @return
	 */
	public static int calcMonthDay(int year, int month, int week, int weekday) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		int wd = c.get(Calendar.DAY_OF_WEEK);

		wd = weekday - wd + 1;
		if (wd <= 0) {
			if (week == 1) return 1;
			wd += 7;
			week--;
		}

		if (week == 1) return wd;

		int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int d = wd + (week - 1) * 7;
		if (d > max) return max;
		else
			return d;
	}

	/**
	 * Returns the number of days in a specific month/year
	 * 
	 * @param year
	 * @param month
	 * @return number of days in the month/year
	 */
	public static int daysInAMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.MONTH, month);
		c.set(Calendar.YEAR, year);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Create a new instance of a Date class
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return date object
	 */
	public static Date newDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		return c.getTime();
	}

	public static String formatDate(Date dt, String format) {
		//SimpleDateFormat f = new SimpleDateFormat(format, new Locale(Messages.getLanguage(), Messages.getCountry()));
		Locale l = Locale.of(Messages.getLanguage(), Messages.getCountry());
		SimpleDateFormat f = new SimpleDateFormat(format, l);
		return f.format(dt);
	}

	/**
	 * Return the difference between two dates. The result is stored in an
	 * instance of {@link Calendar} class
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Calendar calcDifference(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		long len = c1.getTimeInMillis() - c2.getTimeInMillis();
		if (len < 0) len = -len;

		c1.setTimeInMillis(len);
		return c1;
	}

	/**
	 * Check if a data has time information
	 * 
	 * @param dt1
	 * @return
	 */
	public static boolean hasTimePart(Date dt1) {
		if (dt1 instanceof java.sql.Date) return false;

		Calendar c = Calendar.getInstance();
		return (c.get(Calendar.HOUR) > 0) || (c.get(Calendar.MINUTE) > 0) | (c.get(Calendar.SECOND) > 0) || (c.get(Calendar.MILLISECOND) > 0);
	}

	/**
	 * Clean time component from calendar given
	 * 
	 * @param value
	 */
	public static void cleanTime(Calendar value) {
		/*
		 * value.set(Calendar.HOUR_OF_DAY, 0); value.set(Calendar.MINUTE, 0);
		 * value.set(Calendar.SECOND, 0); value.set(Calendar.MILLISECOND, 0);
		 */
		value.setTime(getcleanDate(value));

	}

	/**
	 * return date represents as log file name i.e. YYYY-MM-DD
	 * 
	 * @param cal
	 * @return
	 */
	public static String getLogFileName(Calendar cal) {
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		String day = "" + cal.get(Calendar.DAY_OF_MONTH);
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		return "" + cal.get(Calendar.YEAR) + "-" + month + "-" + day;
	}

	/**
	 * Get very clean date without timezones etc
	 * 
	 * @param cal calendar
	 * @return
	 */
	public static Date getcleanDate(Calendar cal) {
		/*int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH);
		int d = cal.get(Calendar.DATE);
		
		cal.clear();
		
		cal.set(Calendar.YEAR, y);
	    cal.set(Calendar.MONTH, m);
	    cal.set(Calendar.DATE, d);*/
	    
		return getcleanDate(cal.getTime());
	}
	
	/**
	 * Get very clean date without timezones etc
	 * 
	 * @param cal date
	 * @return
	 */
	public static Date getcleanDate(Date cal) {
		Calendar calendar = Calendar.getInstance();
		
		if(cal != null) {
			calendar.setTimeInMillis(cal.getTime());
		}
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DATE);
		
		calendar.clear();
		calendar.set(Calendar.YEAR, y);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.DATE, d);
		
		java.util.Date utilDate = calendar.getTime();
		
		return utilDate;
	    
		//return new LocalDate(cal).toDate();

		
/*		String date = DateFormat.getDateInstance().format(cal);
		try {
			return DateFormat.getDateInstance().parse(date);
		} catch (ParseException e) {
			return null;
		}*/
	}
	public static LocalDate convert(Calendar cal) {
		if(cal == null)
			cal = Calendar.getInstance();
		LocalDate ld = LocalDate.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
		//LocalDateTime.ofInstant(cm.getFrom().toInstant(), cm.getFrom().getTimeZone().toZoneId()).toLocalDate());
		return ld;
	}
	
	public static LocalDate convert(Date d) {
		LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ld;
	}
	
	public static Date convertToDate(LocalDate ld) {
		Date d = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return d;
	}
	
	public static Calendar convert(LocalDate ld) {
		Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(date);
		return calendar;
	}
	
	public static Calendar convertToCalendar(Date d) {
		//Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(d);
		return calendar;
	}
	/**
	 * Compare two calendar dates, without times and timezones Return like
	 * compareTo: -1 first less second, 0 first equal second, 1 first greater
	 * second
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static int compareDates(Calendar first, Calendar second) {
		Date fd = getcleanDate(first);
		Date sd = getcleanDate(second);
		return fd.compareTo(sd);
	}
	/**
	 * Get calendar without time
	 * @param year as is
	 * @param month from 0 to 11
	 * @param date as is
	 * @return
	 */
	public static Calendar getCleanCalendar(int year, int month, int date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, month, date);
		DateUtils.cleanTime(cal);
		return cal;
	}
	
	/**
	 * Returns properly medium formatted text representation of the cal
	 * @param cal
	 * @return
	 */
	public static String formatMedium(Calendar cal){
		if (cal != null){
			//DateFormat f = getMediumDateFormat();
			//return f.format(cal.getTime());
			return convertCalendarToString(cal);
		}else{
			return "";
		}
	}
	
	public static String formatMedium(LocalDate date) {
		if (date != null){
			//DateFormat f = getMediumDateFormat();
			//return f.format(date);
			return convertToString(date);
		}else{
			return "";
		}
	}

	/**
	 * DateFormat.MEDIUM isn`t correct formate date
	 * @return
	 */
	public static DateFormat getMediumDateFormat() {
		Locale l = new Locale.Builder().setLanguage(Messages.getLanguage()).setRegion(Messages.getCountry()).build();
		return DateFormat.getDateInstance(DateFormat.MEDIUM, l);
	}
	/**
	 * Is this day last in the month
	 * @param origDate
	 * @return
	 */
	public static boolean isLastDayOfMonth(Calendar origDate) {
		//LocalDate dt = new LocalDate(origDate);
		LocalDate dt = convert(origDate);
				//LocalDateTime.ofInstant(origDate.toInstant(), origDate.getTimeZone().toZoneId()).toLocalDate();
		LocalDate dt1 = dt.plusDays(1);
		return dt.getMonthValue() != dt1.getMonthValue();
	}

	public static String convertToString(String format, LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}
	
	public static String convertToString(/* String format, */LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Messages.getString("JDateChooser.dateFormatString"));
		return date.format(formatter);
	}
	
	public static String convertToString(/* String format, */Date date) {
		LocalDate ld = convert(date);
		return convertToString(ld);
	}
	
	/*public static String convertToString(Calendar date) {
		LocalDate ld = convert(date);
		return convertToString(ld);
	}*/
	
	public static String convertCalendarToString(/* String format, */Calendar date) {
		if(date != null) {
			LocalDate ld = convert(date);
			return convertToString(ld);
		}
		return "";
	}
	
	public static Calendar convertToCalendar(String format, String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		cal.setTime(sdf.parse(date));
		return cal;
	}
}
