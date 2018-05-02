package br.inpe.climaespacial.swd.commons.factories;

import java.util.AbstractMap.SimpleEntry;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultSimpleEntryFactory<T1, T2>  implements SimpleEntryFactory<T1, T2> {

	@Override
	public SimpleEntry<T1, T2> create(T1 key, T2 value) {
		return new SimpleEntry<T1, T2>(key, value);
	}
}
