package kz.pompei.mars.registers.impl;

import kz.pompei.mars.registers.TestRegister;
import org.springframework.stereotype.Component;

@Component
public class TestRegisterImpl implements TestRegister {
  @Override
  public String asd1() {
    return "Hello from asd1 from register";
  }

  @Override
  public String asd2() {
    return "Hello from asd2 from register";
  }
}
