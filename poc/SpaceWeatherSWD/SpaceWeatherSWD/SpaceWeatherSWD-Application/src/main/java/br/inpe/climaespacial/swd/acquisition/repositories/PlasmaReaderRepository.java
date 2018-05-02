package br.inpe.climaespacial.swd.acquisition.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;

public interface PlasmaReaderRepository {

    List<Plasma> listLast();

}