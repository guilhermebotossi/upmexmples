package br.inpe.climaespacial.swd.kp.mappers;

import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;

import java.util.List;

public interface KPEntityMapper {
    
    List<KPEntity> map(List<KP> kpList);
    
}
