package br.poc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.poc.category.SimpleTestCategory;
import br.poc.category.StringOperationCategory;

@Category({SimpleTestCategory.class, StringOperationCategory.class})
public class StringOperationTests {
	
	@Test
	public void subString() {
		String str = "teste".substring(2);
		Assert.assertTrue(str.equalsIgnoreCase("ste"));
	}

}
