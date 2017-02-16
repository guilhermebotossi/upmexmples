package br.poc.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.poc.tests.OperationsTest;
import br.poc.tests.StringOperationTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({OperationsTest.class, StringOperationTests.class})
public class AllTestsSuite {

}
