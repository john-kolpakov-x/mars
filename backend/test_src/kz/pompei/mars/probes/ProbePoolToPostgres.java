package kz.pompei.mars.probes;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProbePoolToPostgres {
  public static void main(String[] args) throws Exception {


    HikariConfig config = new HikariConfig();

    config.setJdbcUrl("jdbc:postgresql://localhost:5432/mars");
    config.setUsername("i_use_mars");
    config.setPassword("111");

    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    try (HikariDataSource dataSource = new HikariDataSource(config)) {

      try (Connection connection = dataSource.getConnection()) {


        try (PreparedStatement ps = connection.prepareStatement("select * from client")) {

          try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

              System.out.println("id = " + rs.getInt("id") + ", fio = " + rs.getString("fio"));

            }

          }

        }


      }


    }

    System.out.println("ok");

  }
}
