package com.arango.auction.jooq;

import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean("dsaDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dsaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("dsaDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dsaDataSource(@Qualifier("dsaDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DSLContext dslContext(@Qualifier("dsaDataSource") DataSource dataSource) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setSQLDialect(SQLDialect.MYSQL);
        configuration.settings()
                .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
                .setRenderNameCase(RenderNameCase.LOWER);

        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
        configuration.setConnectionProvider(connectionProvider);
        configuration.setTransactionProvider(new ThreadLocalTransactionProvider(connectionProvider));
        configuration.set(new JooqExceptionTranslator());

        return new DefaultDSLContext(configuration);
    }

}

