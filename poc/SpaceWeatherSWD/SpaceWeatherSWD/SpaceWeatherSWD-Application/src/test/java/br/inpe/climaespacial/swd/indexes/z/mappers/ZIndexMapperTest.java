package br.inpe.climaespacial.swd.indexes.z.mappers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.factories.ZIndexFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultZIndexMapper.class)
public class ZIndexMapperTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Produces
    @Mock
    private ListFactory<ZIndex> zIndexListFactory;

    @Produces
    @Mock
    private ZIndexFactory zIndexFactory;

    @Inject
    private ZIndexMapper zIndexMapper;

    @Test
    public void map_calledWithNullList_throws() {
        RuntimeException re = null;

        try {
            List<ZIndexEntity> ziel = null;
            zIndexMapper.map(ziel);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"zIndexEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_calledWithValidList_returnsList() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        ZIndexEntity zi1 = new ZIndexEntity();
        zi1.setTimeTag(zdt1);
        zi1.setPreValue(VALUE);
        zi1.setPostValue(VALUE);

        List<ZIndexEntity> ziel = Arrays.asList(zi1);

        when(zIndexListFactory.create()).thenReturn(new ArrayList<ZIndex>());
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        List<ZIndex> zil = zIndexMapper.map(ziel);

        verify(zIndexListFactory, times(1)).create();
        verify(zIndexFactory, times(1)).create();

        assertNotNull(zil);
        assertThat(zil, is(not(empty())));

        ZIndex zi = zil.get(0);
        assertEquals(zi1.getPreValue(), zi.getPreValue(), DELTA);
        assertEquals(zi1.getPostValue(), zi.getPostValue(), DELTA);
        assertEquals(zi1.getTimeTag(), zi.getTimeTag());
    }
    
    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            ZIndexEntity zie = null;
            zIndexMapper.map(zie);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"zIndexEntity\" cannot be null.", re.getMessage()); 
    }

    @Test
    public void map_calledWithZindexEntity_returnsZindex() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        ZIndexEntity zie = new ZIndexEntity();
        zie.setTimeTag(zdt1);
        zie.setPreValue(VALUE);
        zie.setPostValue(VALUE);


        when(zIndexListFactory.create()).thenReturn(new ArrayList<ZIndex>());
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        ZIndex zi = zIndexMapper.map(zie);

        verify(zIndexFactory, times(1)).create();

        assertNotNull(zi);
        assertEquals(zie.getPreValue(), zi.getPreValue(), DELTA);
        assertEquals(zie.getPostValue(), zi.getPostValue(), DELTA);
        assertEquals(zie.getTimeTag(), zi.getTimeTag());
    }

}
