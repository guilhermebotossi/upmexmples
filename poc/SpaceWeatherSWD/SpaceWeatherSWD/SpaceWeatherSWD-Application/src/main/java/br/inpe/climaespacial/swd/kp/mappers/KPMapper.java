package br.inpe.climaespacial.swd.kp.mappers;

import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;

import java.util.List;

public interface KPMapper {
    
    List<KP> map(List<KPEntity> kpEntityList);

    List<KP> map(String content);
    
}
