package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;

public interface VIndexWriterRepository {

    void save(VIndex cIndex);
    
}
