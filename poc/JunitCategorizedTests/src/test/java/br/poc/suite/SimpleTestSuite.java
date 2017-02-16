package br.poc.suite;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.poc.category.SimpleTestCategory;
import br.poc.tests.OperationsTest;
import br.poc.tests.StringOperationTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(SimpleTestCategory.class)
@Suite.SuiteClasses({OperationsTest.class, StringOperationTests.class})
public class SimpleTestSuite {

}
