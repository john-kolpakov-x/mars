package kz.pompei.mars.registers.impl;

import kz.greetgo.util.RND;
import kz.pompei.mars.database.PostgresConnect;
import kz.pompei.mars.model.ClientRecord;
import kz.pompei.mars.registers.TestRegister;
import kz.pompei.mars.util.ParentTestNg;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRegisterImplTest extends ParentTestNg {

  @Autowired
  private PostgresConnect postgresConnect;

  private ClientRecord newClient(String clientGroup) throws Exception {

    ClientRecord record = new ClientRecord();
    record.id = RND.str(10);
    record.fio = "A" + RND.intStr(10) + " " + RND.str(10) + " " + RND.str(10);

    try (Connection connection = postgresConnect.getDataSource().getConnection()) {

      try (PreparedStatement ps = connection.prepareStatement(
        "insert into client (id, fio, client_group) values (?, ?, ?)"
      )) {

        ps.setString(1, record.id);
        ps.setString(2, record.fio);
        ps.setString(3, clientGroup);

        ps.executeUpdate();

      }

    }

    return record;
  }

  @Autowired
  private TestRegister testRegister;

  @Test
  public void clientList() throws Exception {

    String clientGroup = RND.str(10);

    List<ClientRecord> clientRecords = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      clientRecords.add(newClient(clientGroup));
    }

    clientRecords.sort(Comparator.comparing(x -> x.fio));

    //
    //
    List<ClientRecord> actualList = testRegister.clientList(clientGroup);
    //
    //

    assertThat(actualList).hasSameSizeAs(clientRecords);
    assertThat(actualList.get(2).id).isEqualTo(clientRecords.get(2).id);
    assertThat(actualList.get(2).fio).isEqualTo(clientRecords.get(2).fio);

  }

}
