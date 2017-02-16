package br.poc.suite;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.poc.category.DatabaseTestCategory;
import br.poc.tests.DatabaseTest;


@RunWith(Categories.class)
@Categories.IncludeCategory(DatabaseTestCategory.class)
@Suite.SuiteClasses({DatabaseTest.class})
public class DatabaseTestSuite {
	
	
    @BeforeClass 
    public static void setUpClass() {      
        System.out.println("Banco UP");
        
        try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			DriverManager.getConnection("jdbc:derby:memory:unit-testing;create=true").close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @AfterClass 
    public static void tearDownClass() { 
        System.out.println("Banco Down");
    }

}
