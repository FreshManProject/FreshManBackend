package com.freshman.freshmanbackend.global.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import lombok.experimental.UtilityClass;

/**
 * 날짜/시간 유틸
 *
 * @author 송병선
 */
@UtilityClass
public class DateTimeUtils {

  /**
   * 기본 Date 형식 (yyyy-MM-dd)
   */
  public final String DEFAULT_DATE = "yyyy-MM-dd";
  /**
   * 기본 DateTime 형식 (yyyy-MM-dd HH:mm:ss)
   */
  public final String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";

  /**
   * yyyy-MM-dd 문자열 -> LocalDate
   */
  public LocalDate convertToDate(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE, Locale.KOREA);
    return LocalDate.parse(dateString, formatter);
  }

  /**
   * yyyy-MM-dd HH:mm:ss 문자열 -> LocalDateTime
   */
  public LocalDateTime convertToDateTime(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME, Locale.KOREA);
    return LocalDateTime.parse(dateString, formatter);
  }

  /**
   * LocalDateTime -> yyyy-MM-dd HH:mm:ss 문자열 변환
   */
  public String convertToString(LocalDateTime dateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    return dateTime.format(formatter);
  }

  /**
   * LocalDate -> yyyy-MM-dd 문자열 변환
   */
  public String convertToString(LocalDate date, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    return date.format(formatter);
  }

  /**
   * LocalDateTime -> 24. 6. 17. (월) 23:51 형식의 문자열 반환
   */
  public String formatLocalDateTime(LocalDateTime dateTime) {
    // 요일
    String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

    // 날짜와 시간을 포맷팅 (yyyy, MM, dd, HH, mm을 필요에 맞게 포맷팅)
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy. M. d. ");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // 포맷팅된 날짜와 시간
    String formattedDate = dateTime.format(dateFormatter);
    String formattedTime = dateTime.format(timeFormatter);

    // 최종 형식으로 결합
    return formattedDate + "(" + dayOfWeek + ") " + formattedTime;
  }

  /**
   * 날짜 문자열 포맷 유효성 체크
   *
   * @param dateString 날짜 문자열
   * @param pattern    패턴
   * @return 유효여부
   */
  public boolean validFormat(String dateString, String pattern) {
    if (StringUtils.isBlank(dateString)) {
      return false;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    try {
      if (StringUtils.containsAny(pattern, "H", "m", "s", "S")) {
        LocalDateTime.parse(dateString, formatter);
      } else {
        LocalDate.parse(dateString, formatter);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
