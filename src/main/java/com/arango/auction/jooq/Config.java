package com.arango.auction.jooq;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TransactionProvider;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class Config {



//    @Bean
//    public DSLContext dslContext(@Qualifier("dsaDataSource") DataSource dataSource) {
//        DefaultConfiguration configuration = new DefaultConfiguration();
//        configuration.setSQLDialect(SQLDialect.MYSQL);
//        configuration.settings()
//                .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
//                .setRenderNameCase(RenderNameCase.LOWER);
//
//        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
//        configuration.setConnectionProvider(connectionProvider);
//        configuration.setTransactionProvider(new ThreadLocalTransactionProvider(connectionProvider));
//        configuration.set(new JooqExceptionTranslator());
//
//        return new DefaultDSLContext(configuration);
//    }
@Bean
public DSLContext doCreate() {
    org.jooq.Configuration defaultConfiguration = new DefaultConfiguration()
            .derive(connectionProviderDB())
            .derive(transactionProviderDB())
            .derive(SQLDialect.MYSQL);

    return new DefaultDSLContext(defaultConfiguration);
}

    DataSource datasource( ) {
        HikariDataSource datasource = new HikariDataSource();

        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setJdbcUrl("jdbc:mysql://localhost:3306/auction_system");
        datasource.setUsername("root");
        datasource.setPassword("");

        datasource.setConnectionTestQuery("select 1;");

        return datasource;
    }

    DataSourceTransactionManager transactionManagerDB( ) {
        return new DataSourceTransactionManager(datasource());
    }

    ConnectionProvider connectionProviderDB( ) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(datasource()));
    }

    TransactionProvider transactionProviderDB( ) {
        return new SpringTransactionProvider(transactionManagerDB());
    }



}

