package br.poc.suite;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.poc.category.BasicOperationCategory;
import br.poc.tests.OperationsTest;

@RunWith(Categories.class)
@Categories.IncludeCategory(BasicOperationCategory.class)
@Suite.SuiteClasses({OperationsTest.class})
public class BasicOperationTestSuite {

}
