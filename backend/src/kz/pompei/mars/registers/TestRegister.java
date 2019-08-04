package kz.pompei.mars.registers;

import kz.pompei.mars.model.ClientRecord;

import java.util.List;

public interface TestRegister {

  List<ClientRecord> clientList(String clientGroup) throws Exception;

}
