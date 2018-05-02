package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.kp.dtos.KP;

public interface KPWriterRepository {

    void save(List<KP> kpl);

}
