package br.inpe.climaespacial.swd.kp.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNullOrEmpty;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.KPFactory;
import br.inpe.climaespacial.swd.kp.factories.KPValueFactory;

@Dependent
public class DefaultKPMapper implements KPMapper {

    @Inject
    private KPFactory kpFactory;

    @Inject
    private ListFactory<KP> kpListFactory;
    
    @Inject
    private ListFactory<KPValue> kpValueListFactory;
    
    @Inject
    private KPValueFactory kpValueFactory;

    @Override
    public List<KP> map(List<KPEntity> kpEntityList) {
        throwIfNull(kpEntityList, "kpEntityList");

        List<KP> kpfl = kpListFactory.create();
        kpEntityList.forEach(kpe -> kpfl.add(createKP(kpe)));

        return kpfl;
    }

    private KP createKP(KPEntity kpe) {
        KP kp = kpFactory.create();
        kp.setTimeTag(kpe.getTimeTag());
        return kp;
    }

    @Override
    public List<KP> map(String content) {
        throwIfNullOrEmpty(content, "content");
        
        String[] ca = content.split("\\r?\\n");
        
        if(!ca[0].matches("^\\d{6}.*")) {
            throw new RuntimeException("Argument \"content\" invalid format.");
        }
        
        List<KP> kpl = kpListFactory.create();
        
       for(String line : ca) {
           Pattern pattern = Pattern.compile(getPattern());
           Matcher matcher = pattern.matcher(line);
           if(!matcher.find()) {
               break;
           }
           kpl.add(createKP(matcher));
       }
        
       return kpl;        

    }

    private String getPattern() {
        String timeTagPattern  = "^(\\d{6})\\s*";
        String kpValuePattern = "(\\d)?";
        String kpValueFlagPattern = "([+-o])?\\s*";
        String sumKPPattern = "(\\d{1,2})?([+-o])?\\s?";
        String apPattern = "(\\d{1,2})?\\s?";
        String cpPattern = "(\\d{1,3}\\.\\d{1,3})?";
        String mostDisturbedAndQuietDaysPattern = "(\\w\\d\\S?)?\\s*";
        
        StringBuilder sb = new StringBuilder();
        sb.append(timeTagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(kpValuePattern).append(kpValueFlagPattern);
        sb.append(sumKPPattern);
        sb.append(mostDisturbedAndQuietDaysPattern);
        sb.append(apPattern);
        sb.append(cpPattern);

        return sb.toString();
    }

    private KP createKP(Matcher matcher) {
        KP kp = kpFactory.create();
        kp.setTimeTag(getTimeTag(matcher.group(1)));
        kp.setSum(getLongValue(matcher.group(18)));
        kp.setSumFlag(getValueFlag(matcher.group(19)));
        kp.setMostDisturbedAndQuietDays(matcher.group(20));
        kp.setAp(getLongValue(matcher.group(21)));
        kp.setCp(getDoubleValue(matcher.group(22)));
        List<KPValue> kpvl = kpValueListFactory.create();
        
        ZonedDateTime timeTag = kp.getTimeTag();
        Long value1 = getLongValue(matcher.group(2));
        if(value1 != null) {
            kpvl.add(kpValueFactory.create(timeTag, value1, getValueFlag(matcher.group(3))));
        }
        
        Long value2 = getLongValue(matcher.group(4));
        if(value2 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(3), value2, getValueFlag(matcher.group(5))));
        }
        
        Long value3 = getLongValue(matcher.group(6));
        if(value3 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(6), value3, getValueFlag(matcher.group(7))));
        }
        
        Long value4 = getLongValue(matcher.group(8));
        if(value4 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(9), value4, getValueFlag(matcher.group(9))));
        }
        
        Long value5 = getLongValue(matcher.group(10));
        if(value5 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(12), value5, getValueFlag(matcher.group(11))));
        }

        Long value6 = getLongValue(matcher.group(12));
        if(value6 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(15), value6, getValueFlag(matcher.group(13))));
        }
        
        Long value7 = getLongValue(matcher.group(14));
        if(value7 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(18), value7, getValueFlag(matcher.group(15))));
        }
        
        Long value8 = getLongValue(matcher.group(16));
        if(value8 != null) {
            kpvl.add(kpValueFactory.create(timeTag.plusHours(21), value8, getValueFlag(matcher.group(17))));
        }
        
        kp.setKPValueList(kpvl);
        return kp;
    }

    private KPValueFlag getValueFlag(String value) {
        return value == null ? null :  KPValueFlag.getEnumByValue(value);
    }

    private Double getDoubleValue(String value) {
        return value == null ? null :  Double.valueOf(value);
    }
    
    private Long getLongValue(String value) {
        return value == null ? null :  Long.valueOf(value);
    }

    private ZonedDateTime getTimeTag(String date) {
        String dateStr[] = date.split("(?<=\\G.{2})");
        return ZonedDateTime.parse("20" + dateStr[0] + "-" + dateStr[1] + "-" + dateStr[2] + "T00:00:00z[UTC]");
    }


}
