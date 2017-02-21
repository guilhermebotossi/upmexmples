package br.poc.suite;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.poc.category.DatabaseTestCategory;
import br.poc.db.PersistenceTestUtils;
import br.poc.tests.TelefoneDatabaseTest;
import br.poc.tests.UserDatabaseTest;


@RunWith(Categories.class)
@Categories.IncludeCategory(DatabaseTestCategory.class)
@Suite.SuiteClasses({UserDatabaseTest.class, TelefoneDatabaseTest.class})
public class DatabaseTestSuite {
	
    @BeforeClass 
    public static void setUpClass() {      
        System.out.println("Banco UP");
        try {
        	PersistenceTestUtils.getEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    protected void tearDown() {
    	try {
			PersistenceTestUtils.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
