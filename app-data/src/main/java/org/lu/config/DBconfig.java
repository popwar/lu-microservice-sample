package org.lu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories(basePackages = { "org.lu.repository" })
public class DBconfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "mydb";
	}

	@Bean
	@Override
	public Mongo mongo() throws Exception {
		MongoClient mongo = new MongoClient("localhost");
		mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}
}
