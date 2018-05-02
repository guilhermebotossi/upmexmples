package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;

public interface BIndexWriterRepository {

    void save(BIndex bIndex);
    
}
