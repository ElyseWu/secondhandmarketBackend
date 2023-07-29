package team3.secondhand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.sql.*;

@SpringBootApplication
@EnableCaching
public class SecondhandApplication {

	public static void main(String[] args) {

//		SpringApplication.run(SecondhandApplication.class, args);
		Logger logger = LoggerFactory.getLogger(SecondhandApplication.class);
		Connection connection = null;
		Statement statement = null;
		String postgresUrl = "localhost";
		try {
			logger.debug("Creating database if not exist...");
			connection = DriverManager.getConnection("jdbc:postgresql://" + postgresUrl + ":5432/", "postgres", "secret");
			statement = connection.createStatement();
			statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = 'secondhand'");
			ResultSet resultSet = statement.getResultSet();
			resultSet.next();
			int count = resultSet.getInt(1);

			if (count <= 0) {
				statement.executeUpdate("CREATE DATABASE secondhand");
				logger.debug("Database created.");
			} else {
				logger.debug("Database already exist.");
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} finally {
			try {
				if (statement != null) {
					statement.close();
					logger.debug("Closed Statement.");
				}
				if (connection != null) {
					logger.debug("Closed Connection.");
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}

		SpringApplication.run(SecondhandApplication.class, args);
	}
}
