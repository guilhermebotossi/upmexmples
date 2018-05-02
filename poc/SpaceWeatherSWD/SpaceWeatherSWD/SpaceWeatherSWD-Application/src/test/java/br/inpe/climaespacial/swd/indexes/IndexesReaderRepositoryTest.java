package br.inpe.climaespacial.swd.indexes;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultIndexesReaderRepository.class)
public class IndexesReaderRepositoryTest {

    @Produces
    @Mock
    private ListFactory<ZonedDateTime> zonedDateTimeListFactory;

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private IndexesReaderRepository indexesReaderRepository;

    @Test
    public void lastIndexesDate_calledWithEmptyDatabase_returnsNull() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());
        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(tq.getSingleResult()).thenReturn(null);
        when(entityManager.createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class)).thenReturn(tq);
        when(entityManager.createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class)).thenReturn(tq);
        when(entityManager.createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class)).thenReturn(tq);
        when(entityManager.createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNull(la);
        verify(entityManager).createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class);
    }

    @Test
    public void lastIndexesDate_calledWithBTableContainingData_returnsTimeTagFromBTable() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);
        when(tq1.getSingleResult()).thenReturn(zdt);
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        when(tq2.getSingleResult()).thenReturn(null);
        when(entityManager.createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class)).thenReturn(tq1);
        when(entityManager.createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class)).thenReturn(tq2);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertSame(zdt, la);
        verify(entityManager).createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class);
    }

    @Test
    public void lastIndexesDate_calledWithCTableContainingData_returnsTimeTagFromCTable() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);
        when(tq1.getSingleResult()).thenReturn(zdt);
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        when(tq2.getSingleResult()).thenReturn(null);
        when(entityManager.createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class)).thenReturn(tq1);
        when(entityManager.createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class)).thenReturn(tq2);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertSame(zdt, la);
        verify(entityManager).createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class);
    }

    @Test
    public void lastIndexesDate_calledWithZTableContainingData_returnsTimeTagFromZTable() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);
        when(tq1.getSingleResult()).thenReturn(zdt);
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        when(tq2.getSingleResult()).thenReturn(null);
        when(entityManager.createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class)).thenReturn(tq1);
        when(entityManager.createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class)).thenReturn(tq2);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertSame(zdt, la);
        verify(entityManager).createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class);
    }

    @Test
    public void lastIndexesDate_calledWithVTableContainingData_returnsTimeTagFromVTable() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);
        when(tq1.getSingleResult()).thenReturn(zdt);
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        when(tq2.getSingleResult()).thenReturn(null);
        when(entityManager.createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class)).thenReturn(tq1);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertSame(zdt, la);
        verify(entityManager).createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class);
    }

    @Test
    public void lastIndexesDate_calledWithFullDatabase_returnsBiggestTimeTag() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());

        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(tq1.getSingleResult()).thenReturn(zdt1);

        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        when(tq2.getSingleResult()).thenReturn(zdt2);

        TypedQuery<ZonedDateTime> tq3 = mockTypedQuery(ZonedDateTime.class);
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");
        when(tq3.getSingleResult()).thenReturn(zdt3);

        TypedQuery<ZonedDateTime> tq4 = mockTypedQuery(ZonedDateTime.class);
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        when(tq4.getSingleResult()).thenReturn(zdt4);

        when(entityManager.createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class)).thenReturn(tq1);
        when(entityManager.createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class)).thenReturn(tq2);
        when(entityManager.createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class)).thenReturn(tq3);
        when(entityManager.createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class)).thenReturn(tq4);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertSame(zdt4, la);
        verify(entityManager).createQuery("SELECT MAX(b.timeTag) FROM BIndexEntity b", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(c.timeTag) FROM CIndexEntity c", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(z.timeTag) FROM ZIndexEntity z", ZonedDateTime.class);
        verify(entityManager).createQuery("SELECT MAX(v.timeTag) FROM VIndexEntity v", ZonedDateTime.class);
    }

}
