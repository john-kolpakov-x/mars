package kz.pompei.mars.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@SpringBootApplication
@Import(ImporterAll.class)
public class MarsApplication implements ApplicationListener {

  public static void main(String[] args) {

    System.out.println(new Wow().toString());

    SpringApplication.run(MarsApplication.class, args);
  }


  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ContextRefreshedEvent) {
      ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
      Map<RequestMappingInfo, HandlerMethod> handlerMethods =
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();

      for (Map.Entry<RequestMappingInfo, HandlerMethod> e : handlerMethods.entrySet()) {

        System.out.println("RequestMappingInfo " + e.getKey() + " -> " + e.getValue());

      }

    }
  }
}
