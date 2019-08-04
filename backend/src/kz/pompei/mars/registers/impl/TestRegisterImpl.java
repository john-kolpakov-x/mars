package kz.pompei.mars.registers.impl;

import kz.pompei.mars.database.PostgresConnect;
import kz.pompei.mars.model.ClientRecord;
import kz.pompei.mars.registers.TestRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestRegisterImpl implements TestRegister {


  @Autowired
  PostgresConnect postgresConnect;

  @Override
  public List<ClientRecord> clientList(String clientGroup) throws Exception {

    try (Connection connection = postgresConnect.getDataSource().getConnection()) {

      try (PreparedStatement ps = connection.prepareStatement(
        "select id, fio from client where client_group = ? order by fio"
      )) {

        ps.setString(1, clientGroup);

        try (ResultSet rs = ps.executeQuery()) {

          List<ClientRecord> ret = new ArrayList<>();

          while (rs.next()) {
            ClientRecord rec = new ClientRecord();
            rec.id = rs.getString("id");
            rec.fio = rs.getString("fio");
            ret.add(rec);
          }

          return ret;
        }
      }
    }

  }

}
