package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultDprValueCalculator implements DprValueCalculator {

    @Override
    public Double calculate(MagPlasma magPlasma) {
        throwIfNull(magPlasma, "magPlasma");

        if (magPlasma.getSpeed() == null || magPlasma.getDensity() == null) {
            return null;
        }

        double dpr = 1.672E-6 * magPlasma.getDensity() * magPlasma.getSpeed() * magPlasma.getSpeed();
        dpr = BigDecimal.valueOf(dpr).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return dpr;
    }

}
