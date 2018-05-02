package br.inpe.climaespacial.swd.acquisition.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;

@Dependent
@MagLastRecordQualifier
@Any
public class DefaultMagLastRecordRepository implements LastRecordRepository {

	@Inject
	private EntityManager entityManager;

	@Override
	public ZonedDateTime getLast() {
		Query q = entityManager.createQuery("SELECT MAX(m.timeTag) FROM MagEntity m");
		@SuppressWarnings("unchecked")
		List<ZonedDateTime> l = q.setMaxResults(1).getResultList();
		return !l.isEmpty() ? l.get(0) : null;
	}

}
