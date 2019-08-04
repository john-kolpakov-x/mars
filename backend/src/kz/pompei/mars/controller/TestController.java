package kz.pompei.mars.controller;

import kz.pompei.mars.model.ClientRecord;
import kz.pompei.mars.registers.TestRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private TestRegister testRegister;

  @GetMapping("/client-list")
  public List<ClientRecord> clientList() throws Exception {
    return testRegister.clientList("top");
  }

}
