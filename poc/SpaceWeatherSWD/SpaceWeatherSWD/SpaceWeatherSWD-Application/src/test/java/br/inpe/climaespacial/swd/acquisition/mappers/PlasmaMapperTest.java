package br.inpe.climaespacial.swd.acquisition.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.factories.PlasmaFactory;
import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.InvalidFormatException;
import br.inpe.climaespacial.swd.commons.factories.JSONParserFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.mappers.DateTimeMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultPlasmaMapper.class)
public class PlasmaMapperTest {

    private static final String MESSAGE = "Argument \"content\" cannot be null or empty.";
    
    private static final String JSON_FINE = "[[\"time_tag\",\"density\",\"speed\",\"temperature\"],[\"2017-03-08 13:02:00.000\",\"5.44\",\"528.5\",\"246572\"],[\"2017-03-08 13:03:00.000\",\"5.49\",\"529.3\",\"246319\"]]";

    @Produces
    @Mock
    private DateTimeMapper dateTimeMapper;

    @Produces
    @Mock
    private JSONParserFactory jsonParserFactory;

    @Produces
    @Mock
    private ListFactory<Plasma> plasmaListFactory;

    @Inject
    private PlasmaMapper plasmaMapper;

    @Produces
    @Mock
    private PlasmaFactory plasmaFactory;

    @Test
    public void map_calledWithNullString_throws() {
        ArgumentException ae = null;

        try {
            plasmaMapper.map(null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(MESSAGE, ae.getMessage());
    }

    @Test
    public void map_calledWithEmptyString_throws() {
        ArgumentException ae = null;

        try {
            plasmaMapper.map("");
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(MESSAGE, ae.getMessage());
    }

    @Test
    public void map_calledWithInvalidFormatArgument_throws() {
        InvalidFormatException ife = null;
        when(jsonParserFactory.create()).thenReturn(new JSONParser());

        try {
            plasmaMapper.map("ajdfdnad;555");
        } catch (InvalidFormatException e) {
            ife = e;
        }

        assertNotNull(ife);
        assertEquals(InvalidFormatException.class, ife.getClass());
        assertEquals("Argument \"content\" has an invalid format.", ife.getMessage());
        verify(jsonParserFactory).create();
    }
    
    @Test
    public void map_calledWithValidFormatArgument_succeeds() {
        ZonedDateTime tt = ZonedDateTime.parse("2017-03-08T13:02Z[UTC]");

        String dt = "2017-03-08 13:02:00.000";

        when(plasmaFactory.create()).thenAnswer(a -> new Plasma());
        when(dateTimeMapper.map(dt)).thenReturn(tt);
        when(plasmaListFactory.create()).thenReturn(new ArrayList<>());
        when(jsonParserFactory.create()).thenReturn(new JSONParser());

        List<Plasma> pl = plasmaMapper.map(JSON_FINE);

        verify(dateTimeMapper, times(1)).map(dt);
        verify(plasmaFactory, times(2)).create();

        assertNotNull(pl);
        assertEquals(2, pl.size());

        Plasma p = pl.get(0);

        assertEquals(p.getTimeTag(), tt);
        double delta = 0.001;
        assertEquals(5.44, p.getDensity(), delta);
        assertEquals(528.5, p.getSpeed(), delta);
        assertEquals(246572, p.getTemperature(), delta);
        verify(plasmaListFactory).create();
        verify(jsonParserFactory).create();
    }
}