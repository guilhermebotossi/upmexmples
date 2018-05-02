package br.inpe.climaespacial.swd.values.services;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.indexes.RangeDate;
import br.inpe.climaespacial.swd.values.dtos.ValuesData;

public interface ValuesService {

    ValuesData getValuesData(ZonedDateTime idt, ZonedDateTime fdt);

    RangeDate getRangeDate();

}
