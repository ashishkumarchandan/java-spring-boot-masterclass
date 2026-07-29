package com.platform.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@SpringBootApplication
public class InstallingMysqlDbeaverApplication {

    private static final Logger log = LoggerFactory.getLogger(InstallingMysqlDbeaverApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(InstallingMysqlDbeaverApplication.class, args);
    }

    @Bean
    public CommandLineRunner databaseVerificationRunner(DataSource dataSource) {
        return args -> {
            log.info("==========================================================================");
            log.info("🔍 VERIFYING RDBMS DAEMON & JDBC CONNECTION METADATA");
            log.info("==========================================================================");

            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();

                log.info("✅ Connected to Database Engine: {} {}", 
                        metaData.getDatabaseProductName(), 
                        metaData.getDatabaseProductVersion());
                log.info("🔌 JDBC Driver Info: {} {}", 
                        metaData.getDriverName(), 
                        metaData.getDriverVersion());
                log.info("🌐 JDBC Connection URL: {}", metaData.getURL());
                log.info("👤 Connected Database User: {}", metaData.getUserName());
                log.info("⚡ Transaction Isolation Level: {}", connection.getTransactionIsolation());
            } catch (Exception e) {
                log.error("❌ Failed to connect to database: {}", e.getMessage(), e);
            }

            log.info("==========================================================================");
        };
    }
}
