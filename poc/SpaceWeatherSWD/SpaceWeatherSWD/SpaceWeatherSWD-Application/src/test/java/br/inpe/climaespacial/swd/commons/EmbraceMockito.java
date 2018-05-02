package br.inpe.climaespacial.swd.commons;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.persistence.TypedQuery;

import org.mockito.Mockito;


@SuppressWarnings({"unchecked"})
public class EmbraceMockito extends Mockito {
	public static <T> List<T> mockList(Class<T> list) {
		return (List<T>) Mockito.mock(List.class, Mockito.withSettings());
	}
	
	public static <T> TypedQuery<T> mockTypedQuery(Class<T> queryType) {
		return (TypedQuery<T>) Mockito.mock(TypedQuery.class, Mockito.withSettings());
	}
	
	public static <T> Instance<T> mockIntance(Class<T> instanceType) {
		return (Instance<T>) Mockito.mock(Instance.class, Mockito.withSettings());
	}

	public static <T1, T2> SimpleEntry<T1, List<T2>> mockSimpleEntryWithList(Class<T1> t1, Class<T2> t2) {
		return (SimpleEntry<T1,List<T2>>) Mockito.mock(SimpleEntry.class, Mockito.withSettings());
	}
}
