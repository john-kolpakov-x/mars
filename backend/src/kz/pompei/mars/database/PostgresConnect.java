package kz.pompei.mars.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class PostgresConnect {

  private HikariDataSource dataSource;

  private String getUrl() {
    return "jdbc:postgresql://localhost:5432/mars";
  }

  private String getUsername() {
    return "i_use_mars";
  }

  private String getPassword() {
    return "111";
  }

  @PostConstruct
  private void createPool() throws Exception {

    applyLiquibase();

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(getUrl());
    config.setUsername(getUsername());
    config.setPassword(getPassword());

    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    dataSource = new HikariDataSource(config);

  }

  @Bean
  public DataSource getDataSource() {
    return dataSource;
  }

  @PreDestroy
  private void destroyPool() {
    dataSource.close();
  }

  private void applyLiquibase() throws Exception {

    Class.forName("org.postgresql.Driver");

    try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {

      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
      Liquibase liquibase = new Liquibase("liquibase/main-change-log.xml", new ClassLoaderResourceAccessor(), database);
      liquibase.update((String) null);

    }

  }

  public static void main(String[] args) throws Exception {
    PostgresConnect postgresConnect = new PostgresConnect();
    postgresConnect.applyLiquibase();
  }

}
