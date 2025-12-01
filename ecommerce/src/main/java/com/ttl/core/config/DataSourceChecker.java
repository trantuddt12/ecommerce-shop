package com.ttl.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceChecker implements CommandLineRunner{

	@Autowired
	private DataSource mvDataSource;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Data Source is : " + mvDataSource.getClass().getName());
		System.out.println("JDBC Url : " + mvDataSource.getConnection().getMetaData().getURL());
	}

}