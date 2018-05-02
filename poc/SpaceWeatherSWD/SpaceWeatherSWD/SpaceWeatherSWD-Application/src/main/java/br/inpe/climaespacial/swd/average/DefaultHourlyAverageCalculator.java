package br.inpe.climaespacial.swd.average;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;

@Dependent
public class DefaultHourlyAverageCalculator implements HourlyAverageCalculator {

    private static final int MINIMUM_SIZE_TO_CALC_AVG = 31;

    @Override
    public HourlyAverage calculate(ZonedDateTime dateTime, List<MagPlasmaCalculated> magPlasmaCalculatedList) {

        throwIfNull(dateTime, "dateTime");

        throwIfNull(magPlasmaCalculatedList, "magPlasmaCalculatedList");

        HourlyAverage ha = new HourlyAverage();
        ha.setTimeTag(dateTime);

        if (magPlasmaCalculatedList.size() < MINIMUM_SIZE_TO_CALC_AVG) {
            return ha;
        }

        ha.setBt(getBtAvg(magPlasmaCalculatedList));
        ha.setBxGsm(getBxGsmAvg(magPlasmaCalculatedList));
        ha.setByGsm(getByGsmAvg(magPlasmaCalculatedList));
        ha.setBzGsm(getBzGsmAvg(magPlasmaCalculatedList));

        ha.setDensity(getDensityAvg(magPlasmaCalculatedList));
        ha.setTemperature(getTemperatureAvg(magPlasmaCalculatedList));
        ha.setSpeed(getSpeedAvg(magPlasmaCalculatedList));

        ha.setDpr(getDprAvg(magPlasmaCalculatedList));
        ha.setEy(getEyAvg(magPlasmaCalculatedList));
        ha.setRmp(getRmpAvg(magPlasmaCalculatedList));

        return ha;
    }

    private Double getRmpAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getRmp() != null, pair -> pair.getRmp());
    }

    private Double getEyAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getEy() != null, pair -> pair.getEy());
    }

    private Double getDprAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getDpr() != null, pair -> pair.getDpr());
    }

    private Double getSpeedAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getSpeed() != null, pair -> pair.getSpeed());
    }

    private Double getTemperatureAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getTemperature() != null, pair -> pair.getTemperature());
    }

    private Double getDensityAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getDensity() != null, pair -> pair.getDensity());
    }

    private Double getBzGsmAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getBzGsm() != null, pair -> pair.getBzGsm());
    }

    private Double getByGsmAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getByGsm() != null, pair -> pair.getByGsm());
    }

    private Double getBxGsmAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getBxGsm() != null, pair -> pair.getBxGsm());
    }

    private Double getBtAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList) {
        return getAvg(magPlasmaCalculatedList, mpc -> mpc.getBt() != null, pair -> pair.getBt());
    }
    
    private Double getAvg(List<MagPlasmaCalculated> magPlasmaCalculatedList, Predicate<? super MagPlasmaCalculated> predicate, ToDoubleFunction<? super MagPlasmaCalculated> mapper) {
        OptionalDouble average = OptionalDouble.empty();
        if(magPlasmaCalculatedList.stream().filter(predicate).count() >= MINIMUM_SIZE_TO_CALC_AVG) {
            average = magPlasmaCalculatedList.stream().filter(predicate).mapToDouble(mapper).average();
        }
        return average.isPresent() ? average.getAsDouble(): null; 
    }
}