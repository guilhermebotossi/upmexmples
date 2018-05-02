package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultRmpValueCalculator implements RmpValueCalculator {

    @Override
    public Double calculate(MagPlasma magPlasma, Double dpr) {

        throwIfNull(magPlasma, "magPlasma");

        if (magPlasma.getBzGsm() == null || dpr == null) {
            return null;
        }

        Double rmp = (10.22 + 1.29 * (Math.tanh(0.184 * (magPlasma.getBzGsm() + 8.14)))) * Math.pow(dpr, (-1.0 / 6.6));

        Double formatedRmp = new BigDecimal(rmp).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return formatedRmp;
    }

}
