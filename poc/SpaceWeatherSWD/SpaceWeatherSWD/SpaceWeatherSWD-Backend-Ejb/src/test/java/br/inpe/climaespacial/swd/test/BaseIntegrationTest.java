package br.inpe.climaespacial.swd.test;

import br.inpe.climaespacial.swd.commons.DefaultTimeZoneConfigurator;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class BaseIntegrationTest {

    private static boolean wasInitialized = false;
    private static boolean wasShutdown = false;

    @BeforeClass
    public static void beforeClass() {
        if (!wasInitialized) {
            new DefaultTimeZoneConfigurator().init();
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa-1;create=true").close();
                DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa-2;create=true").close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            wasInitialized = true;
        }
    }

    @AfterClass
    public static void shutdown() {
        if (!wasShutdown) {
            try {
                DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa-1;shutdown=true").close();
            } catch (SQLException e) {
            }
            try {
                DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa-2;shutdown=true").close();
            } catch (SQLException e) {
            }
            wasShutdown = true;
        }
    }

}
