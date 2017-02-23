package br.poc.mutation;

public class Mutation {

	public String foo(boolean b) {
		if (b) {
			performVitallyImportantBusinessFunction();
			return "OK";
		}

		return "FAIL";
	}

	public String foo2(int i) {
		if (i >= 0) {
			return "foo";
		} else {
			return "bar";
		}
	}

	private void performVitallyImportantBusinessFunction() {
		a++;
	}
	
	private long a;

	public long getA() {
		return a;
	}
	
	
	

}
