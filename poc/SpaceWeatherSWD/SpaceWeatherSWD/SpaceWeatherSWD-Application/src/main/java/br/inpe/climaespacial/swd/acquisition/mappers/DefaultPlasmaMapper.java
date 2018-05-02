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

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.factories.PlasmaFactory;
import br.inpe.climaespacial.swd.commons.exceptions.InvalidFormatException;
import br.inpe.climaespacial.swd.commons.factories.JSONParserFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.mappers.DateTimeMapper;

@Dependent
public class DefaultPlasmaMapper implements PlasmaMapper {

    @Inject
    private DateTimeMapper dateTimeMapper;

    @Inject
    private ListFactory<Plasma> plasmaListFactory;

    @Inject
    private PlasmaFactory plasmaFactory;
    
    @Inject
    private JSONParserFactory jsonParserFactory;

    public List<Plasma> map(String content) {
        throwIfNullOrEmpty(content, "content");
        return mapAll(content);
    }

    private List<Plasma> mapAll(String content) {
        List<Plasma> pl = plasmaListFactory.create();

        JSONArray jarray = parseStringToJson(content);

        for (int i = 1; i < jarray.size(); i++) {
            pl.add(createMapper((JSONArray) jarray.get(i)));
        }

        return pl;
    }

    private JSONArray parseStringToJson(String content) {
        try {
            
            JSONParser parser = jsonParserFactory.create();
            return (JSONArray) parser.parse(content);
        } catch (ParseException e) {
            throw createCustom("Argument \"content\" has an invalid format.", InvalidFormatException.class);
        }
    }

    private Plasma createMapper(JSONArray json) {
        Plasma p = plasmaFactory.create();

        ZonedDateTime zdt = dateTimeMapper.map(json.get(0).toString());

        p.setTimeTag(zdt);
        p.setDensity(getWrappedDoubleValue(json.get(1)));
        p.setSpeed(getWrappedDoubleValue(json.get(2)));
        p.setTemperature(getWrappedDoubleValue(json.get(3)));
        return p;
    }

    private double getDoubleValue(Object obj) {
        return Double.parseDouble(((String) obj));
    }

    private Double getWrappedDoubleValue(Object obj) {
        return obj != null ? getDoubleValue(obj) : null;
    }
    
}
