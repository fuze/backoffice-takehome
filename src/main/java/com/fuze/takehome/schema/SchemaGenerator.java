package com.fuze.takehome.schema;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuze.takehome.util.DummyDataGenerator;

public class SchemaGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaGenerator.class);
	
	//For now we hard-code the schema name.
	private static final String DB_SCHEMA_FORGE_NAME = "takeHome";	
	private static final String DB_SCRIPT_PATH_FORGE = "schema/create.schema.sql";
	
	@Inject
	private DataSource dataSource;

	@Inject
	private DummyDataGenerator dataGenerator;
	
	@PostConstruct
	public void generateSchemas() throws Exception {
		try(Connection conn = dataSource.getConnection()) {
			generateSchema(conn, DB_SCHEMA_FORGE_NAME, DB_SCRIPT_PATH_FORGE);
		}
		catch(Exception e) {
			throw new RuntimeException("Failed to initialize all schemas.", e);
		}
		
		//Generate some dummy data. Simply so that the DB isn't empty on app startup.
		dataGenerator.generateData();
	}
	
	public void generateSchema(Connection conn, String schema, String scriptPath) throws Exception
	{
		try {
			//Only create the schema if it does not exist
			if(doesSchemaExist(conn, schema)) {
				LOGGER.info(String.format("DB schema %s already exists. It will not be upgraded.", schema));
				return;
			}
			else {
				LOGGER.info(String.format("DB schema %s not found. Attempting to create it...", schema));
			}
			
			ScriptRunner scriptRunner = new ScriptRunner(conn);
			scriptRunner.setAutoCommit(false);
			scriptRunner.setStopOnError(true);
			
			InputStream in = SchemaGenerator.class.getClassLoader().getResourceAsStream(scriptPath);
			scriptRunner.runScript(new BufferedReader(new InputStreamReader(in, "UTF-8")));
			conn.commit();
		} catch(Exception e) {
			throw new RuntimeException(String.format("Failed to initialize %s DB Schema.", schema), e);
		}
	}	
	
	private boolean doesSchemaExist(Connection conn, String schema) throws SQLException {
		Statement s = null;
		s = conn.createStatement();
		s.execute("SELECT schema_name FROM information_schema.schemata WHERE schema_name = '" + schema + "';");
		return s.getResultSet().next();

	}
}
