package br.inpe.climaespacial.swd.indexes;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultIndexesReaderRepository implements IndexesReaderRepository {

    @Inject
    private ListFactory<ZonedDateTime> zonedDateTimeListFactory;

    @Inject
    private EntityManager entityManager;

    @Override
    public ZonedDateTime lastIndexesDate() {
        List<ZonedDateTime> zdtl = zonedDateTimeListFactory.create();
        zdtl.add(getMaxBTimeTag());
        zdtl.add(getMaxCTimeTag());
        zdtl.add(getMaxZTimeTag());
        zdtl.add(getMaxVTimeTag());

        Optional<ZonedDateTime> zdto = zdtl.stream()
                .filter(zdt -> zdt != null)
                .max((zdt1, zdt2) -> zdt1.compareTo(zdt2));

        return zdto.isPresent() ? zdto.get() : null;
    }

    private ZonedDateTime getMaxBTimeTag() {
        return executeQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b");
    }

    private ZonedDateTime getMaxCTimeTag() {
        return executeQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c");
    }

    private ZonedDateTime getMaxZTimeTag() {
        return executeQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z");
    }

    private ZonedDateTime getMaxVTimeTag() {
        return executeQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v");
    }

    private ZonedDateTime executeQuery(String query) {
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(query, ZonedDateTime.class);
        ZonedDateTime zdtl = tq.getSingleResult();
        return zdtl;
    }

}
