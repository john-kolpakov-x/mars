package kz.pompei.mars.util;

public class EnvUtil {

  public static String env(String envName, String defaultValue) {

    String value = System.getenv(envName);

    if (value == null) {
      return defaultValue;
    }

    value = value.trim();

    if (value.length() == 0) {
      return defaultValue;
    }

    return value;

  }

}
