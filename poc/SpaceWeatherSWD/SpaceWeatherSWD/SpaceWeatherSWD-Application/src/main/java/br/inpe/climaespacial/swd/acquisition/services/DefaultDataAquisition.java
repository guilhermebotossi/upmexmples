package br.inpe.climaespacial.swd.acquisition.services;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.repositories.MagReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.MagWriterRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.PlasmaReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.PlasmaWriterRepository;

@Dependent
public class DefaultDataAquisition implements DataAquisition {

    @Inject
    private MagReaderRepository magReaderRepository;

    @Inject
    private PlasmaReaderRepository plasmaReaderRepository;

    @Inject
    private MagWriterRepository magWriterRepository;

    @Inject
    private PlasmaWriterRepository plasmaWriterRepository;

    @Override
    @Transactional(value=TxType.NOT_SUPPORTED)
    public void acquire() {
        List<Mag> ml = magReaderRepository.listLast();
        List<Plasma> pl = plasmaReaderRepository.listLast();
        magWriterRepository.save(ml);
        plasmaWriterRepository.save(pl);
    }
}
