package com.searchmetrics.rapatao.rates.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@Profile("test")
@ConfigurationProperties("spring.data.mongodb")
@Data
public class FongoConfig extends AbstractMongoConfiguration {

    private String database = "exchange_rate";

    @Override
    public MongoClient mongoClient() {
        return new Fongo(database).getMongo();
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

}
