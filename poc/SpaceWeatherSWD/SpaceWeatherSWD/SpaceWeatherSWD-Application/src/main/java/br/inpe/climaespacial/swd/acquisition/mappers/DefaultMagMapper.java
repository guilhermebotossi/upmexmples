package br.inpe.climaespacial.swd.acquisition.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNullOrEmpty;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.factories.MagFactory;
import br.inpe.climaespacial.swd.commons.exceptions.InvalidFormatException;
import br.inpe.climaespacial.swd.commons.factories.JSONParserFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.mappers.DateTimeMapper;

@Dependent
public class DefaultMagMapper implements MagMapper {

    @Inject
    private ListFactory<Mag> magListFactory;

    @Inject
    private MagFactory magFactory;

    @Inject
    private JSONParserFactory jsonParserFactory;

    @Inject
    private DateTimeMapper dateTimeMapper;

    public List<Mag> map(String content) {
        throwIfNullOrEmpty(content, "content");

        return mapAll(content);
    }

    private List<Mag> mapAll(String content) {
        List<Mag> ml = magListFactory.create();

        JSONArray jarray = parseStringToJson(content);

        for (int i = 1; i < jarray.size(); i++) {
            ml.add(createMapper((JSONArray) jarray.get(i)));
        }

        return ml;
    }

    private JSONArray parseStringToJson(String content) {
        try {
            JSONParser parser = jsonParserFactory.create();
            return (JSONArray) parser.parse(content);
        } catch (ParseException e) {
            throw createCustom("Argument \"content\" has an invalid format.", InvalidFormatException.class);
        }
    }

    private Mag createMapper(JSONArray json) {
        Mag m = magFactory.create();

        ZonedDateTime zdt = dateTimeMapper.map(json.get(0).toString());

        m.setTimeTag(zdt);
        m.setBxGsm(getWrappedDoubleValue(json.get(1)));
        m.setByGsm(getWrappedDoubleValue(json.get(2)));
        m.setBzGsm(getWrappedDoubleValue(json.get(3)));
        m.setLonGsm(getWrappedDoubleValue(json.get(4)));
        m.setLatGsm(getWrappedDoubleValue(json.get(5)));
        m.setBt(getWrappedDoubleValue(json.get(6)));

        return m;
    }

    private double getDoubleValue(Object obj) {
        return Double.valueOf(((String) obj));
    }

    private Double getWrappedDoubleValue(Object obj) {
        return obj != null ? getDoubleValue(obj) : null;
    }
}
