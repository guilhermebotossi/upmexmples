package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultEyValueCalculator implements EyValueCalculator {

    @Override
    public Double calculate(MagPlasma magPlasma) {
        throwIfNull(magPlasma, "magPlasma");

        if (magPlasma.getSpeed() == null || magPlasma.getBzGsm() == null) {
            return null;
        }

        double ey = -0.9965 * magPlasma.getSpeed() * magPlasma.getBzGsm() / 1000.0;

        ey = BigDecimal.valueOf(ey).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return ey;
    }
}
