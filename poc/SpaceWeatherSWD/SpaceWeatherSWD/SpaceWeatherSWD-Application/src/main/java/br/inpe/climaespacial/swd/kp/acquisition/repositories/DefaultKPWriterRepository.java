package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.mappers.KPEntityMapper;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultKPWriterRepository implements KPWriterRepository {

    @Inject
    private KPEntityMapper kpEntityMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(List<KP> kpl) {
        throwIfNull(kpl, "kpl");

        String sql = "SELECT DISTINCT(kpe) FROM KPEntity kpe LEFT JOIN FETCH kpe.kpValueList WHERE kpe.timeTag = :timeTag";
        TypedQuery<KPEntity> tqkpe = entityManager.createQuery(sql, KPEntity.class);
        List<KPEntity> kpel = kpEntityMapper.map(kpl);
        
        for (int i = 0; i < kpel.toArray().length; i++) {
            KPEntity kpe = kpel.get(i);
            tqkpe.setParameter("timeTag", kpe.getTimeTag());
            List<KPEntity> kpel2 = tqkpe.getResultList();
            if (kpel2.isEmpty()) {
                entityManager.persist(kpe);
                entityManager.flush();
                kpe.getKPValueList().forEach(kpve -> {
                    entityManager.persist(kpve);
                });
            } else {
                KPEntity kpe2 = kpel2.get(0);
                kpe.setId(kpe2.getId());
               
                kpe.getKPValueList().forEach(kpve -> {
                    kpve.setKPEntity(kpe);
                    Optional<KPValueEntity> okpe = kpe2.getKPValueList().stream().filter(kpve2 -> kpve.getTimeTag().equals(kpve2.getTimeTag())).findAny();
                    if(okpe.isPresent()) {
                        kpve.setId(okpe.get().getId());
                        entityManager.merge(kpve);
                    } else {
                        entityManager.persist(kpve);
                    }
                    entityManager.flush();
                });
                
                
                entityManager.merge(kpe);
                entityManager.flush();
                
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

}
