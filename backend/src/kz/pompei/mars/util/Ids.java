package kz.pompei.mars.util;

import java.security.SecureRandom;
import java.util.Random;

public class Ids {

  @SuppressWarnings("SpellCheckingInspection")
  private static final String ENG = "abcdefghijklmnopqrstuvwxyz";
  private static final String DEG = "0123456789";
  private static final char[] ALL_CHARS = (ENG.toLowerCase() + ENG.toUpperCase() + DEG).toCharArray();

  private static final Random RANDOM = new SecureRandom();

  public static String generate() {
    return str(13);
  }

  public static String str(int len) {
    char[] ret = new char[len];

    for (int i = 0; i < len; i++) {
      ret[i] = ALL_CHARS[RANDOM.nextInt(ALL_CHARS.length)];
    }

    return new String(ret);
  }
}
