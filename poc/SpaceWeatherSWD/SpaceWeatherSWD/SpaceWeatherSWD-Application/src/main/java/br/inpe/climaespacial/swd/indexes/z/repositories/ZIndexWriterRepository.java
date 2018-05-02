package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;

public interface ZIndexWriterRepository {

    void save(ZIndex zIndex);
    
}
