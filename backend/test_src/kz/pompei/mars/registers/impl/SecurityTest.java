package kz.pompei.mars.registers.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SecurityTest {

  @Test
  public void pbkdf2() {

    Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();

    long time1 = System.nanoTime();
    String result = encoder.encode("myPassword");
    long time2 = System.nanoTime();

    System.out.println("result = " + result);

    long time3 = System.nanoTime();
    boolean matches = encoder.matches("myPassword", result);
    long time4 = System.nanoTime();

    assertThat(matches).isTrue();

    System.out.println("encode time = " + (time2 - time1));
    System.out.println("match time = " + (time4 - time3));
  }

  @Test
  public void BCrypt() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(18);

    long time1 = System.nanoTime();
    String result = encoder.encode("myPassword");
    long time2 = System.nanoTime();

    System.out.println("result = " + result);

    long time3 = System.nanoTime();
    boolean matches = encoder.matches("myPassword", result);
    long time4 = System.nanoTime();

    assertThat(matches).isTrue();

    System.out.println("encode time " + (time2 - time1));
    System.out.println("match time " + (time4 - time3));
  }

}
