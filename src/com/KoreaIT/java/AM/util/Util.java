package com.KoreaIT.java.AM.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
  // 현재 날짜 시간
  public static String getNowDate() {
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return formatter.format(date);
  }
}
