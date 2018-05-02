package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;

public interface CIndexWriterRepository {

    void save(CIndex cIndex);
    
}
