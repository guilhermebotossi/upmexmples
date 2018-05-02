package br.inpe.climaespacial.swd.kp.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

public interface KPValueFactory {

    KPValue create(ZonedDateTime timeTag, Long kpValue, KPValueFlag flag);

}
