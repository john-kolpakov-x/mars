package kz.pompei.mars.probes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProbeConnectToPostgres {
  public static void main(String[] args) throws Exception {


    Class.forName("org.postgresql.Driver");

    try (Connection conn = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/mars", "i_use_mars", "111"
    )) {

      System.out.println("conn = " + conn);

      try (PreparedStatement ps = conn.prepareStatement("select * from client")) {

        try (ResultSet rs = ps.executeQuery()) {

          while (rs.next()) {

            System.out.println("id = " + rs.getInt("id") + ", fio = " + rs.getString("fio"));

          }

        }

      }


    }


    System.out.println("asd");
  }
}
