package com.cjavellana.conf;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.GENERATE_STATISTICS;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.USE_SQL_COMMENTS;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@Profile(value = "mysql")
public class MySqlJpaConfiguration extends CommonJpaConfigurations {

    @Override
    protected Class<? extends Dialect> getDatabaseDialect() {
        return MySQL5Dialect.class;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        try {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(getDriverClassName());
            dataSource.setJdbcUrl(getUrl());
            dataSource.setUser(getUser());
            dataSource.setPassword(getPassword());
            dataSource.setTestConnectionOnCheckin(true);
            dataSource.setTestConnectionOnCheckout(true);
            dataSource.setPreferredTestQuery(getDatabaseValidationQuery());
            dataSource.setIdleConnectionTestPeriod(1800000);
            return dataSource;
        } catch (PropertyVetoException pve) {
            throw new RuntimeException("Unable to create oracle datasource", pve);
        }
    }

    @Override
    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBM2DDL_AUTO, getHbm2ddl());
        properties.setProperty(GENERATE_STATISTICS, super.getEnvironment().getProperty("database.hbm.showsql"));
        properties.setProperty(SHOW_SQL, super.getEnvironment().getProperty("database.hbm.showsql"));
        properties.setProperty(FORMAT_SQL, TRUE.toString());
        properties.setProperty(USE_SQL_COMMENTS, TRUE.toString());
        properties.setProperty("hibernate.connection.CharSet", getHibernateCharSet());
        properties.setProperty("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        return properties;
    }
}
