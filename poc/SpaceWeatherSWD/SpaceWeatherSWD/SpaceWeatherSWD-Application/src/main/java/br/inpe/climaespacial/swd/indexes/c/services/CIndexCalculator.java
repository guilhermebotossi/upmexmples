package br.inpe.climaespacial.swd.indexes.c.services;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

public interface CIndexCalculator {

    CIndex calculate(HourlyAverage hourlyAverage);
    
}
