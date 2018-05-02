package br.inpe.climaespacial.swd.calculation.mappers;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.CalculatedValuesEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCalculatedValuesEntityMapper implements CalculatedValuesEntityMapper {

    @Inject
    private CalculatedValuesEntityFactory calculatedValuesEntityFactory;

    @Inject
    private ListFactory<CalculatedValuesEntity> calculatedValuesListFactory;

    @Override
    public List<CalculatedValuesEntity> map(List<CalculatedValues> calculatedValuesList) {

        throwIfNull(calculatedValuesList, "calculatedValuesList");

        List<CalculatedValuesEntity> cvel = calculatedValuesListFactory.create();

        calculatedValuesList.forEach((CalculatedValues cv) -> {
            CalculatedValuesEntity cve = calculatedValuesToCalculatedValuesEntity(cv);
            cvel.add(cve);
        });

        return cvel;
    }

    public CalculatedValuesEntity calculatedValuesToCalculatedValuesEntity(CalculatedValues cv) {
        CalculatedValuesEntity cve = calculatedValuesEntityFactory.create();

        cve.setTimeTag(cv.getTimeTag());
        cve.setEy(cv.getEy());
        cve.setDpr(cv.getDpr());
        cve.setRmp(cv.getRmp());

        return cve;
    }
}
