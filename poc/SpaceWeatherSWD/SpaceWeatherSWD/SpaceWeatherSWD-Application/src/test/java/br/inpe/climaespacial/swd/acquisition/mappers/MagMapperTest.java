package br.inpe.climaespacial.swd.acquisition.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.factories.MagFactory;
import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.InvalidFormatException;
import br.inpe.climaespacial.swd.commons.factories.JSONParserFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.mappers.DateTimeMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses({ DefaultMagMapper.class })
public class MagMapperTest {

    private static final String MESSAGE = "Argument \"content\" cannot be null or empty.";

    private String json = "[[\"time_tag\",\"bx_gsm\",\"by_gsm\",\"bz_gsm\",\"lon_gsm\",\"lat_gsm\",\"bt\"],[\"2017-03-08 13:02:00.000\",\"4.31\",\"-3.52\",\"-0.60\",\"320.74\",\"-6.15\",\"5.61\"],[\"2017-03-08 13:03:00.000\",\"4.35\",\"-3.50\",\"-0.38\",\"321.19\",\"-3.88\",\"5.60\"]]";

    private String jsonContainingNulls = "[[\"time_tag\",\"bx_gsm\",\"by_gsm\",\"bz_gsm\",\"lon_gsm\",\"lat_gsm\",\"bt\"],[\"2017-03-08 13:02:00.000\",\"4.31\",\"-3.52\",\"-0.60\",null,null,\"5.61\"],[\"2017-03-08 13:03:00.000\",\"4.35\",\"-3.50\",\"-0.38\",\"321.19\",null,\"5.60\"]]";

    @Produces
    @Mock
    private ListFactory<Mag> magListFactory;

    @Produces
    @Mock
    private JSONParserFactory jsonParserFactory;

    @Produces
    @Mock
    private DateTimeMapper dateTimeMapper;

    @Produces
    @Mock
    private MagFactory magFactory;

    @Inject
    private MagMapper magMapper;

    private double delta = 0.001;

    @Test
    public void map_calledWithNullString_throws() {
        ArgumentException ae = null;

        try {
            magMapper.map(null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(MESSAGE, ae.getMessage());
    }

    @Test
    public void map_calledWithAnEmptyString_throws() {
        ArgumentException ae = null;

        try {
            magMapper.map("");
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(MESSAGE, ae.getMessage());
    }

    @Test
    public void map_calledWithInvalidFormatContent_throws() {
        InvalidFormatException ife = null;
        when(jsonParserFactory.create()).thenReturn(new JSONParser());

        try {
            magMapper.map("aaaaaa;5555");
        } catch (InvalidFormatException e) {
            ife = e;
        }

        assertNotNull(ife);
        assertEquals(InvalidFormatException.class, ife.getClass());
        assertEquals("Argument \"content\" has an invalid format.", ife.getMessage());
        verify(jsonParserFactory).create();
    }

    @Test
    public void map_calledWithValidArguments_succeeds() {
        when(magListFactory.create()).thenReturn(new ArrayList<>());

        ZonedDateTime tt = ZonedDateTime.parse("2017-03-08T13:02Z[UTC]");

        String dt = "2017-03-08 13:02:00.000";

        when(magFactory.create()).thenAnswer(a -> new Mag());
        when(dateTimeMapper.map(dt)).thenReturn(tt);
        when(jsonParserFactory.create()).thenReturn(new JSONParser());

        List<Mag> ml = magMapper.map(json);
        assertNotNull(ml);
        assertEquals(2, ml.size());

        Mag m = ml.get(0);

        verify(magListFactory).create();
        verify(dateTimeMapper).map(dt);
        verify(jsonParserFactory).create();

        assertEquals(tt, m.getTimeTag());
        assertEquals(4.31, m.getBxGsm(), delta);
        assertEquals(-3.52, m.getByGsm(), delta);
        assertEquals(-0.60, m.getBzGsm(), delta);
        assertEquals(320.74, m.getLonGsm(), delta);
        assertEquals(-6.15, m.getLatGsm(), delta);
        assertEquals(5.61, m.getBt(), delta);
    }

    @Test
    public void map_calledWithValidArgumentsContainingNullLatLong_succeeds() {
        when(magListFactory.create()).thenReturn(new ArrayList<>());

        ZonedDateTime tt = ZonedDateTime.parse("2017-03-08T13:02Z[UTC]");

        String dt = "2017-03-08 13:02:00.000";

        when(magFactory.create()).thenAnswer(a -> new Mag());
        when(dateTimeMapper.map(dt)).thenReturn(tt);
        when(jsonParserFactory.create()).thenReturn(new JSONParser());

        List<Mag> ml = magMapper.map(jsonContainingNulls);
        assertNotNull(ml);
        assertEquals(2, ml.size());

        Mag m = ml.get(0);

        verify(magListFactory, times(1)).create();
        verify(dateTimeMapper, times(1)).map(dt);
        verify(magFactory, times(2)).create();
        verify(jsonParserFactory).create();

        assertEquals(tt, m.getTimeTag());
        assertEquals(4.31, m.getBxGsm(), delta);
        assertEquals(-3.52, m.getByGsm(), delta);
        assertEquals(-0.60, m.getBzGsm(), delta);
        assertNull(m.getLonGsm());
        assertNull(m.getLatGsm());
        assertEquals(5.61, m.getBt(), delta);
    }

}
