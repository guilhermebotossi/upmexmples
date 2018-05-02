package br.inpe.climaespacial.swd.commons.factories;

import java.util.AbstractMap.SimpleEntry;

public interface SimpleEntryFactory<T1, T2> {

	SimpleEntry<T1, T2> create(T1 key, T2 Value);

}
