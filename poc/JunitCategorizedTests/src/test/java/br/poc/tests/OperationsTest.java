package br.poc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.poc.category.BasicOperationCategory;
import br.poc.category.GenerationOperationCategory;
import br.poc.category.SimpleTestCategory;


@Category({SimpleTestCategory.class})
public class OperationsTest {

	@Test
	@Category({BasicOperationCategory.class})
	public void soma() {
		int result = 1+1;
		Assert.assertTrue(result == 2);
	}
	
	
	@Test
	@Category({BasicOperationCategory.class})
	public void subtração() {
		int result = 1-1;
		Assert.assertTrue(result == 0);
	}
	
	@Test
	@Category({BasicOperationCategory.class})
	public void divisao() {
		int result = 1/1;
		Assert.assertTrue(result == 1);
	}
	
	@Test
	@Category({BasicOperationCategory.class})
	public void mutilplicacao() {
		int result = 1*1;
		Assert.assertTrue(result == 1);
	}
	
	
	@Test
	@Category({GenerationOperationCategory.class})
	public void random() {
		Double result = Math.random();
		Assert.assertTrue(result != null);
	}
	
	

}
