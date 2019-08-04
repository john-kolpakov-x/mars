package kz.pompei.mars.mars;

import kz.pompei.mars.controller.ImporterController;
import kz.pompei.mars.registers.impl.ImporterRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ImporterController.class, ImporterRegister.class})
public class MarsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MarsApplication.class, args);
  }

}
