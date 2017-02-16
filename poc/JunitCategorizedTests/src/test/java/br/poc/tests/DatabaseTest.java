package br.poc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.poc.category.DatabaseTestCategory;
import br.poc.db.Database;
import br.poc.db.impl.DatabaseImpl;

@Category({DatabaseTestCategory.class})
public class DatabaseTest {
	
	private Database db = new DatabaseImpl();
	
	@Test
	public void insert(){
		Long id = db.insert(1L);
		Assert.assertTrue(id.equals(1L));
	}
	
	@Test
	public void find() {
		Long id = db.find(1L);
		Assert.assertTrue(id.equals(1L));		
	}
	
	@Test
	public void delete() {
		Long id = db.delete(1L);
		Assert.assertTrue(id.equals(1L));
	}

}
