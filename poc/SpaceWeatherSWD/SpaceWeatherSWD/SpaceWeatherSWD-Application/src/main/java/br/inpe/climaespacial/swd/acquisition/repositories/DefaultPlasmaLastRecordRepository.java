package br.inpe.climaespacial.swd.acquisition.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;

@Dependent
@PlasmaLastRecordQualifier
@Any
public class DefaultPlasmaLastRecordRepository implements LastRecordRepository {

	@Inject
	private EntityManager entityManager;

	@Override
	public ZonedDateTime getLast() {
		Query query = entityManager.createQuery("SELECT MAX(pe.timeTag) FROM PlasmaEntity pe");
		@SuppressWarnings("unchecked")
		List<ZonedDateTime> zdtl = query.setMaxResults(1).getResultList();
		return zdtl.isEmpty() ? null : zdtl.get(0);
	}

}
