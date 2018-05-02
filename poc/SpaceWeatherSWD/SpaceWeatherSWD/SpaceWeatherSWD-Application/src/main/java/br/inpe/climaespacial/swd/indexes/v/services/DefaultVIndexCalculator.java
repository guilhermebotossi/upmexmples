package br.inpe.climaespacial.swd.indexes.v.services;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexFactory;

@Dependent
public class DefaultVIndexCalculator implements VIndexCalculator {

    private VIndexFactory vIndexFactory;

    private ListFactory<Double> doubleListFactory;

    private static final int HA_LAST_INDEX = 25;

    @Inject
    public DefaultVIndexCalculator(VIndexFactory vIndexFactory, ListFactory<Double> doubleListFactory) {
        this.vIndexFactory = vIndexFactory;
        this.doubleListFactory = doubleListFactory;
    }

    @Override
    public VIndex calculate(List<HourlyAverage> hourlyAverageList, List<VIndex> vIndexList) {

        throwIfNull(hourlyAverageList, "hourlyAverageList");

        throwIfNull(vIndexList, "vIndexList");

        if (hourlyAverageList.isEmpty() || hourlyAverageList.size() != 26) {
            throw createCustom("Argument \"hourlyAverageList\" shall have 26 elements.", ArgumentException.class);
        }

        if (vIndexList.isEmpty() || vIndexList.size() != 24) {
            throw createCustom("Argument \"vIndexList\" shall have 24 elements.", ArgumentException.class);
        }

        ZonedDateTime vIndexTimeTag = hourlyAverageList.get(HA_LAST_INDEX).getTimeTag();

        if (hourlyAverageList.stream().filter(ha -> ha.getSpeed() != null).count() <= 1) {
            return getVIndexNull(vIndexTimeTag);
        }

        if (hourlyAverageList.get(HA_LAST_INDEX).getSpeed() == null) {
            return getVIndexNull(vIndexTimeTag);
        }

        List<Double> stdl = doubleListFactory.create();

        for (int i = 2; i <= HA_LAST_INDEX; i++) {
            
            Double currHAS = hourlyAverageList.get(i).getSpeed();
            Double std;
            
            if (i == 2 && hourlyAverageList.get(i - 1).getSpeed() == null &&
                    vIndexList.get(23).getPreValue() != null
                    && hourlyAverageList.get(24).getSpeed() != null
                    && hourlyAverageList.get(25).getSpeed() != null) {

                std = vIndexList.get(23).getPreValue() + (hourlyAverageList.get(25).getSpeed() - hourlyAverageList.get(24).getSpeed());
                stdl.clear();
                stdl.add(std);
                break;
            } else {
            
                Optional<HourlyAverage> prevHASNonNull = hourlyAverageList.stream().limit(i).filter(ha -> ha.getSpeed() != null).reduce((a, b) -> b);
                std = currHAS != null && prevHASNonNull.isPresent() && prevHASNonNull.get().getSpeed() != null ? currHAS - prevHASNonNull.get().getSpeed() : null;
            }
            stdl.add(std);
        }

        boolean hasSlicePoint = false;
        int posix = 0;
        
        for (int i = 1; i < 24; i++) {            
            if (vIndexList.get(i).getPreValue() != null && (vIndexList.get(i).getPreValue() >= 100.0 || vIndexList.get(i).getIsCycleBegin())) {
                hasSlicePoint = true;
                posix = i;
            }
        }

        Double sumStd;
        
        VIndex viResult = null;

        if (hasSlicePoint) {
            sumStd = vIndexList.get(posix).getPreValue();
            if(posix < stdl.size())
            {
                sumStd += stdl.subList(posix, stdl.size()).stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
            }
            viResult = getVIndex(sumStd, false, vIndexTimeTag);
        }

        boolean hadEvent24hsAgo = vIndexList.get(0).getPreValue() != null && vIndexList.get(0).getPreValue() >= 100.0
                && vIndexList.get(0).getPreValue() > vIndexList.subList(1, 24).stream()
                .filter(vi -> vi.getPreValue() != null).max((vi1, vi2) -> Double
                .compare(vi1.getPreValue().doubleValue(), vi2.getPreValue().doubleValue()))
                .get().getPreValue();
        
        if (hadEvent24hsAgo && cycleHasNotBeganWithinLast24Hours(vIndexList)) {
            viResult = getVIndex(stdl.get(23), true, vIndexTimeTag);
        }
        
        if(viResult != null){
            return viResult;
        }

        sumStd = stdl.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
        return getVIndex(sumStd, false, vIndexTimeTag);

    }

    private boolean cycleHasNotBeganWithinLast24Hours(List<VIndex> vIndexList) {
        return !vIndexList.stream().anyMatch(vi -> vi.getIsCycleBegin());
    }

    private VIndex getVIndex(Double preValue, boolean isCycleBegin, ZonedDateTime vIndexTimeTag) {
        VIndex vi = vIndexFactory.create();
        vi.setPreValue(preValue);
        vi.setPostValue(calcPostValue(preValue));
        vi.setTimeTag(vIndexTimeTag);
        vi.setIsCycleBegin(isCycleBegin);
        return vi;
    }

    private VIndex getVIndexNull(ZonedDateTime vIndexTimeTag) {
        VIndex vi = vIndexFactory.create();
        vi.setTimeTag(vIndexTimeTag);
        return vi;
    }

    private double calcPostValue(double preValue) {
        double[] limits = {0.0, 100.0, 200.0, 300.0, 400.0, 500.0, 1000.0};
        Double postValue = 0.0;

        for (int i = 0; i <= 5; i++) {
            if (Double.compare(preValue, limits[i]) >= 0) {
                postValue = i + (preValue - limits[i]) / (limits[i + 1] - limits[i]);
            }
        }

        if (postValue.compareTo(6.0) >= 0) {
            postValue = 5.99;
        }

        return postValue;
    }

}
