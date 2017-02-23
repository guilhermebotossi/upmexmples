package br.poc.mutation.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.poc.mutation.Mutation;

public class MutationTest {

	private Mutation mutation;
	
	@Before
	public void init() {
		mutation = new Mutation();
	}
	
	@Test
	public void shouldFailWhenGivenFalse() {
	  assertEquals("FAIL", mutation.foo(false));
	}

	@Test
	public void shouldBeOkWhenGivenTrue() {
	  assertEquals("OK", mutation.foo(true));
	}
	
	//@Test
	public void shouldBeOne() {
		mutation.foo(true);
		assertTrue(mutation.getA() == 1);
	}
	
	
	@Test
	public void shouldReturnFooWhenGiven1() {
	  assertEquals("foo", mutation.foo2(1));
	}
	
	//@Test
	public void shouldReturnFooWhenGiven0() {
	  assertEquals("foo", mutation.foo2(0));
	}

	@Test
	public void shouldReturnBarWhenGivenMinus1() {
	  assertEquals("bar", mutation.foo2(-1));
	}

	
}
