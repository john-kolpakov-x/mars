package kz.pompei.mars.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Component
public class PostgresConnect {

  private HikariDataSource dataSource;

  @PostConstruct
  private void createPool() {

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl("jdbc:postgresql://localhost:5432/mars");
    config.setUsername("i_use_mars");
    config.setPassword("111");

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

}
