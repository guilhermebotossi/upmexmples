package br.inpe.climaespacial.swd.commons.helpers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwCustom;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.NotUniformListException;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

@Dependent
public abstract class BaseDataFillerHelper<T extends TimeTagable> implements DataFillerHelper<T> {

    @Inject
    private ListFactory<T> tListFactory;

    @Override
    public List<T> fillByHours(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled) {
        if (listToBeFilled != null && listToBeFilled.isEmpty())
            return tListFactory.create();
        return fill(initialDate, finalDate, listToBeFilled, ChronoUnit.HOURS);
    }

    @Override
    public List<T> fillByMinutes(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled) {
        if (listToBeFilled != null && listToBeFilled.isEmpty())
            return tListFactory.create();
        return fill(initialDate, finalDate, listToBeFilled, ChronoUnit.MINUTES);
    }

    public List<T> fillByHoursAnyway(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled) {
        ChronoUnit unit = ChronoUnit.HOURS;
        return fill(initialDate, finalDate, listToBeFilled, unit);
    }

    public List<T> fillByMinutesAnyway(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled) {
        ChronoUnit unit = ChronoUnit.MINUTES;
        return fill(initialDate, finalDate, listToBeFilled, unit);
    }

    private List<T> fill(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled, ChronoUnit unit) {
        validation(initialDate, finalDate, listToBeFilled, unit);

        List<T> fl = tListFactory.create();

        int i = 0;
        int itensUsed = 0;

        for (ZonedDateTime zdt = initialDate; !zdt.isAfter(finalDate); zdt = sum(zdt, 1, unit)) {
            final ZonedDateTime tzdt = zdt;
            Optional<T> to = listToBeFilled.stream().filter(tt -> tt.getTimeTag().equals(tzdt)).findFirst();
            T t;

            if (to.isPresent()) {
                t = to.get();
                itensUsed++;
            } else {
                t = create();
                t.setTimeTag(tzdt);
            }

            fl.add(i++, t.deepClone());
        }

        if (itensUsed < listToBeFilled.size()) {
            throw createCustom("Argument \"listToBeFilled\" is not uniform.", NotUniformListException.class);
        }

        return fl;
    }

    private ZonedDateTime sum(ZonedDateTime zdt, int amountToAdd, ChronoUnit unit) {
        return zdt.plus(amountToAdd, unit);
    }

    protected abstract T create();

    private void validation(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled,
            ChronoUnit unit) {
        throwIfNull(initialDate, "initialDate");
        throwIfNull(finalDate, "finalDate");
        throwIfNull(listToBeFilled, "listToBeFilled");

        long targetSize = 1;
        Duration d = Duration.between(initialDate, finalDate);
        if (unit == ChronoUnit.HOURS) {
            targetSize += d.toHours();
        } else {
            targetSize += d.toMinutes();
        }

        if (listToBeFilled.size() > targetSize) {
            String message = "Argument \"listToBeFilled\" must have at most " + targetSize + " items.";
            throwCustom(message, ArgumentException.class);
        }

        if (finalDate.isBefore(initialDate)) {
            throwCustom("Argument \"initialDate\" cannot be after than \"finalDate\".", ArgumentException.class);
        }
    }

}
