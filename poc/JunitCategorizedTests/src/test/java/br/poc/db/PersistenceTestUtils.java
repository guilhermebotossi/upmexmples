package br.poc.db;

import java.sql.DriverManager;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.poc.suite.DatabaseTestSuite;

public class PersistenceTestUtils {

	private static Logger logger = Logger.getLogger(DatabaseTestSuite.class.getName());
	private static EntityManagerFactory emFactory;
	private static EntityManager em;

	private EntityManager createEntityManager() {
		logger.info("BuildingEntityManager for unit tests");
		if (emFactory == null) {
			emFactory = Persistence.createEntityManagerFactory("testPU");
			em = emFactory.createEntityManager();
		}
		return em;
	}

	private void testConnection() throws Exception {
		logger.info("Starting memory database for unit tests");
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;create=true").close();
	}

	public static EntityManager getEntityManager() throws Exception {
		PersistenceTestUtils ptu = new PersistenceTestUtils();
		ptu.testConnection();
		return ptu.createEntityManager();

	}

	public static void shutdown() throws Exception {
		logger.info("Shuting Hibernate JPA layer.");
		if (em != null) {
			em.close();
		}

		if (emFactory != null) {
			emFactory.close();
		}
		
		
        logger.info("Stopping memory database.");
            DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;shutdown=true").close();

	}

}
