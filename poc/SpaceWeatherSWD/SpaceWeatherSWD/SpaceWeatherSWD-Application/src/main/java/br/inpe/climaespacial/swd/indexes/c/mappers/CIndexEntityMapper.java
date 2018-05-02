package br.inpe.climaespacial.swd.indexes.c.mappers;

import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;

public interface CIndexEntityMapper {

	CIndexEntity map(CIndex cIndex);

}
