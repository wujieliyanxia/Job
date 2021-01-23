package utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

import java.time.Month;
import java.util.Locale;
import java.util.TimeZone;

public class Clock {

  public static final long MILLS_PER_SECOND = 1000L;
  public static final long MILLS_PER_MINUTE = 60 * MILLS_PER_SECOND;
  public static final long MILLS_PER_HOUR = 60 * MILLS_PER_MINUTE;
  public static final long MILLS_PER_DAY = 24 * MILLS_PER_HOUR;

  public static final long SECOND_PER_MINUTE = 60;
  public static final long SECOND_PER_HOUR = 60 * SECOND_PER_MINUTE;
  public static final long SECOND_PER_DAY = 24 * SECOND_PER_HOUR;
  public static final long SECOND_PER_WEEK = 7 * SECOND_PER_DAY;

  public static long getMaxMillisOfDay(DateTime dateTime) {
    return dateTime.millisOfDay().withMaximumValue().getMillis();
  }

  public static long getMaxMillisOfDay(Long millis, TimeZone timeZone) {
    return Clock.date(millis, timeZone).millisOfDay().withMaximumValue().getMillis();
  }

  public static long getMaxMillisOfHour(long millis, TimeZone timeZone) {
    DateTime dateTime = date(millis, timeZone);
    int hour = dateTime.getHourOfDay();
    return dateTime.millisOfDay().withMaximumValue().minusHours(23 - hour).getMillis();
  }

  public static long getMinMillisOfPlusDays(long millis, int days, TimeZone timeZone) {
    return Clock.date(millis, timeZone).plusDays(days).millisOfDay().withMinimumValue().getMillis();
  }

  public static long getMaxMillisOfPlusDays(long millis, int days, TimeZone timeZone) {
    return Clock.date(millis, timeZone).plusDays(days).millisOfDay().withMaximumValue().getMillis();
  }

  public static long getMaxMillisOfMonth(Long timestamp, TimeZone timeZone) {
    return getMaxMillisOfDay(
        new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
            .plusMonths(1)
            .withDayOfMonth(1)
            .minusDays(1)
            .getMillis(),
        timeZone);
  }

  public static long getMinMillisOfPlusMonths(Long timestamp, int months, TimeZone timeZone) {
    return getMinMillisOfDay(
        new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
            .plusMonths(months)
            .getMillis(),
        timeZone);
  }

  public static long getMaxMillisOfPlusMonths(Long timestamp, int months, TimeZone timeZone) {
    return getMaxMillisOfDay(
        new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
            .plusMonths(months)
            .getMillis(),
        timeZone);
  }

  public static int getHourOfDay(Long timestamp, TimeZone timeZone) {
    return new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
        .getHourOfDay();
  }

  public static long getMinMillisOfDay(DateTime dateTime) {
    return dateTime.millisOfDay().withMinimumValue().getMillis();
  }

  public static long getMinMillisOfDay(long millis, TimeZone timeZone) {
    return date(millis, timeZone).millisOfDay().withMinimumValue().getMillis();
  }

  public static long getMinMillsOfHourInDay(long millis, int hour, TimeZone timeZone) {
    return date(millis, timeZone).millisOfDay().withMinimumValue().hourOfDay().setCopy(hour).getMillis();
  }

  public static long getMinMillisOfHour(long millis, TimeZone timeZone) {
    DateTime dateTime = date(millis, timeZone);
    int hour = dateTime.getHourOfDay();
    return dateTime.millisOfDay().withMinimumValue().plusHours(hour).getMillis();
  }

  public static long getMinMillisOfYear(Long timestamp, TimeZone timeZone) {
    return new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
        .withDayOfYear(1).withTimeAtStartOfDay().getMillis();
  }

  public static long getMinMillisOfMonth(Long timestamp, TimeZone timeZone) {
    return new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
        .withDayOfMonth(1).withTimeAtStartOfDay().getMillis();
  }

  public static long getMinMillisOfWeek(Long timestamp, TimeZone timeZone) {
    return new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone))
        .withDayOfWeek(1).withTimeAtStartOfDay().getMillis();
  }

  // 获取当时的时间时间戳
  public static long now() {
    return DateTimeUtils.currentTimeMillis();
  }

  public static long nowInSec() {
    return DateTimeUtils.currentTimeMillis() / MILLS_PER_SECOND;
  }


  public static String dateTimeStrFromTimestampPlusDays(Long timestamp, String format, TimeZone timeZone, int days) {
    return plusOffsetDays(timestamp, timeZone, days).toString(DateTimeFormat.forPattern(format));
  }

  public static int hourOfDay(Long timestamp, TimeZone timeZone) {
    return date(timestamp, timeZone).hourOfDay().get();
  }

  /**
   * 获取两个日期之间的天数差
   */
  public static int getCalenderDaysBetween(long startMillis, long endMills, TimeZone timeZone) {
    return Days.daysBetween(date(getMinMillisOfDay(startMillis, timeZone), timeZone).toLocalDate(),
        date(getMinMillisOfDay(endMills, timeZone), timeZone).toLocalDate())
        .getDays();
  }

  public static long getHoursBefore(long millis, int hours, TimeZone timeZone) {
    return date(millis, timeZone)
        .minusHours(hours)
        .getMillis();
  }



  public static long nowDateInMillis(TimeZone timeZone) {
    return nowDate(timeZone).withTimeAtStartOfDay().getMillis();
  }

  public static int year(long timestamp, TimeZone timeZone) {
    return Clock.date(timestamp, timeZone).year().get();
  }

  public static int month(long timestamp, TimeZone timeZone) {
    return Clock.date(timestamp, timeZone).monthOfYear().get();
  }

  public static int dayOfMonth(long timestamp, TimeZone timeZone) {
    return Clock.date(timestamp, timeZone).dayOfMonth().get();
  }

  public static Month monthEnum(long timestamp, TimeZone timeZone) {
    return Month.of(Clock.date(timestamp, timeZone).monthOfYear().get());
  }

  public static DateTime nowDate(TimeZone timeZone) {
    return new DateTime(DateTimeZone.forTimeZone(timeZone));
  }

  public static DateTime date(Long timestamp, TimeZone timeZone) {
    return new DateTime(timestamp, DateTimeZone.forTimeZone(timeZone));
  }

  public static int getDaysBetween(Long startMillis, Long endMills, TimeZone timeZone) {
    return Days.daysBetween(date(startMillis, timeZone), Clock.date(endMills, timeZone)).getDays() + 1;
  }

  public static int getMonthsBetween(Long startMillis, Long endMills, TimeZone timeZone) {
    return Months.monthsBetween(date(startMillis, timeZone), Clock.date(endMills, timeZone)).getMonths() + 1;
  }

  public static int getHoursBetween(Long startMills, Long endMills, TimeZone timeZone) {
    return Hours.hoursBetween(date(startMills, timeZone), date(endMills, timeZone)).getHours();
  }

  public static int getYearsBetween(Long startMills, Long endMills, TimeZone timeZone) {
    return Years.yearsBetween(date(startMills, timeZone), date(endMills, timeZone)).getYears();
  }

  public static long getSecondsBetween(Long startMillis, Long endMills) {
    return (endMills - startMillis) / 1000;
  }

  public static boolean isSameDay(long firstMillis, long secondMillis, TimeZone timeZone) {
    return getCalenderDaysBetween(firstMillis, secondMillis, timeZone) == 0;
  }

  /**
   * 来源于YqgClock，找n天前 0点整的毫秒数
   *
   * @param daysAgo  天数
   * @param timeZone 时区
   * @return 具体的值
   */
  public static long daysAgoInMillis(int daysAgo, TimeZone timeZone) {
    return nowDate(timeZone).minusDays(daysAgo).withTimeAtStartOfDay().getMillis();
  }

  public static DateTime plusOffsetDays(long timestamp, TimeZone timeZone, int offset) {
    return Clock.date(timestamp, timeZone).plusDays(offset);
  }

  public static String plusOffsetYears(long timestamp, TimeZone timeZone, int offset, String format) {
    return date(timestamp, timeZone).plusYears(offset).toString(DateTimeFormat.forPattern(format));
  }

  public static Long plusOffsetDaysMills(long timestamp, TimeZone timeZone, int offset) {
    return plusOffsetDays(timestamp, timeZone, offset).getMillis();
  }

  public static Long dateStringToLong(String dateStr, String format, TimeZone timeZone) {
    if (dateStr == null) return 0L;
    DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
    LocalDateTime localDateTime = DateTimeFormat.forPattern(format).withZone(dateTimeZone).parseLocalDateTime(dateStr);
    try {
      //获取南半球夏令时开始当天零点的时间戳可能会有异常，处理异常的办法是使用 toDateTimeAtStartOfDay 获取当天的日期
      return localDateTime.toDateTime(dateTimeZone).getMillis();
    } catch (IllegalInstantException ignored) {
      return localDateTime.toLocalDate().toDateTimeAtStartOfDay(dateTimeZone).getMillis();
    }
  }

  public static String getNumericDateStr(String dateStr) {
    return dateStr.codePoints()
        .map(c -> Character.isDigit(c) ? Character.forDigit(Character.getNumericValue(c), 10) : c)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  //如果需要使用"MMM"格式，请使用带locale的方法
  public static Long i18nDateStringToLong(String dateStr, String format, TimeZone timeZone) {
    if (StringUtils.isBlank(dateStr)) return 0L;
    return dateStringToLong(getNumericDateStr(dateStr), format, timeZone);
  }


  public static Long dateStringToLong(String dateStr, String format, TimeZone timeZone, Locale locale) {
    if (StringUtils.isBlank(dateStr)) return 0L;
    return DateTimeFormat.forPattern(format)
        .withZone(DateTimeZone.forTimeZone(timeZone))
        .withLocale(locale).parseDateTime(dateStr).getMillis();
  }

  public static Long i18nDateStringToLong(String dateStr, String format, TimeZone timeZone, Locale locale) {
    if (StringUtils.isBlank(dateStr)) return 0L;
    return dateStringToLong(getNumericDateStr(dateStr), format, timeZone, locale);
  }

  public static Integer yearsToNow(Long start, TimeZone timeZone) {
    if (start == null) {
      return null;
    }
    DateTime dateTime = new DateTime(start, DateTimeZone.forTimeZone(timeZone));
    DateTime now = DateTime.now(DateTimeZone.forTimeZone(timeZone));
    return Years.yearsBetween(dateTime, now).getYears();
  }

  public static long getRemainingMillisOfMonth(TimeZone timeZone) {
    return getMaxMillisOfMonth(now(), timeZone) - now();
  }

  public static long getPassedMillsOfDay(Long time, TimeZone timeZone) {
    return time - getMinMillisOfDay(time, timeZone);
  }

  public static long getRemainingMillisOfDay(TimeZone timeZone) {
    return getMaxMillisOfDay(now(), timeZone) - now();
  }

  public static int getRemainingSecondOfDay(TimeZone timeZone) {
    return nowDate(timeZone).secondOfDay().withMaximumValue().getSecondOfDay() - nowDate(timeZone).getSecondOfDay();
  }

  public static boolean isWeekend(long timestamp, TimeZone timeZone) {
    return date(timestamp, timeZone).dayOfWeek().get() > 5;
  }

  public static long getMaxMillisDayOfThisMonth(long time, int dayOfMonth, TimeZone timeZone) {
    return getMaxMillisOfPlusDays(
        getMaxMillisOfMonth(getMaxMillisOfPlusMonths(time, -1, timeZone), timeZone),
        dayOfMonth,
        timeZone);
  }

  public static long getMaxMillisDayOfNextMonth(long time, int dayOfMonth, TimeZone timeZone) {
    return getMaxMillisOfPlusDays(getMaxMillisOfMonth(time, timeZone), dayOfMonth, timeZone);
  }

  public static boolean isMatchFormat(String dateStr, String format) {
    if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
      return false;
    }
    try {
      DateTimeFormat.forPattern(format).parseDateTime(dateStr).getMillis();
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
