package br.inpe.climaespacial.swd.hello.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.exceptions.DataException;
import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import br.inpe.climaespacial.swd.hello.factories.HelloEntityFactory;
import br.inpe.climaespacial.swd.hello.mappers.HelloMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses({ DefaultHelloRepository.class })
public class HelloRepositoryTest {

    private static final String SQL = "SELECT h FROM HelloEntity h";

    private static final String MSG_ERRO_LAST_UPDATED = "Não foi possível obter a última data, verifique se o método save foi chamado ao menos uma vez.";

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Produces
    @Mock
    private HelloEntityFactory helloEntityFactory;

    @Produces
    @Mock
    private HelloMapper helloMapper;

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private HelloRepository helloRepository;

    @Test
    public void save_calledWithInvalidEntity_throwsEntityExistsException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        HelloEntity helloEntity = mock(HelloEntity.class);
        when(helloEntityFactory.create()).thenReturn(helloEntity);
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);
        doThrow(EntityExistsException.class).when(entityManager).merge(helloEntity);
        DataException de = null;

        try {
            helloRepository.save();
        } catch (DataException e) {
            de = e;
        }

        assertNotNull(de);
        assertEquals(DataException.class, de.getClass());
        assertEquals("Não foi possível salvar hello.", de.getMessage());
        assertThat(de.getCause(), instanceOf(EntityExistsException.class));
        verify(helloEntityFactory, times(1)).create();
        verify(helloEntity, times(1)).setModificationDate(now);
        verify(entityManager, times(1)).merge(helloEntity);

    }

    @Test
    public void save_calledWithInvalidEntity_throwsIllegalArgumentException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        HelloEntity helloEntity = mock(HelloEntity.class);
        when(helloEntityFactory.create()).thenReturn(helloEntity);
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);
        doThrow(IllegalArgumentException.class).when(entityManager).merge(helloEntity);
        DataException de = null;

        try {
            helloRepository.save();
        } catch (DataException e) {
            de = e;
        }

        assertNotNull(de);
        assertEquals(DataException.class, de.getClass());
        assertEquals("Não foi possível salvar hello.", de.getMessage());
        assertThat(de.getCause(), instanceOf(IllegalArgumentException.class));
        verify(helloEntityFactory, times(1)).create();
        verify(helloEntity, times(1)).setModificationDate(now);
        verify(entityManager, times(1)).merge(helloEntity);
    }

    @Test
    public void save_calledWithInvalidEntity_throwsTransactionRequiredException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        HelloEntity helloEntity = mock(HelloEntity.class);
        when(helloEntityFactory.create()).thenReturn(helloEntity);
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);
        doThrow(TransactionRequiredException.class).when(entityManager).merge(helloEntity);
        RuntimeException re = null;

        try {
            helloRepository.save();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Não foi possível salvar hello.", re.getMessage());
        assertThat(re.getCause(), instanceOf(TransactionRequiredException.class));
        verify(helloEntityFactory, times(1)).create();
        verify(helloEntity, times(1)).setModificationDate(now);
        verify(entityManager, times(1)).merge(helloEntity);
    }

    @Test
    public void save_calledWithNewEntity_persists() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        HelloEntity helloEntity = mock(HelloEntity.class);
        when(helloEntityFactory.create()).thenReturn(helloEntity);
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);

        helloRepository.save();

        verify(helloEntityFactory, times(1)).create();
        verify(helloEntity, times(1)).setModificationDate(now);
        verify(entityManager, times(1)).merge(helloEntity);
        verify(entityManager, times(1)).flush();
    }

    @Test
    public void save_calledWithExistingEntity_updates() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        HelloEntity helloEntity = (HelloEntity) mock(HelloEntity.class);
        UUID id = UUID.randomUUID();
        when(helloEntity.getId()).thenReturn(id);
        when(query.getResultList()).thenReturn(Arrays.asList(helloEntity));
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);

        helloRepository.save();

        verify(helloEntity, times(1)).setModificationDate(now);
        verify(entityManager, times(1)).merge(helloEntity);
    }

    @Test
    public void getLastUpdate_called_throwsNoResultException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(NoResultException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(NoResultException.class));
    }

    @Test
    public void getLastUpdate_called_throwsNonUniqueResultException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(NonUniqueResultException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(NonUniqueResultException.class));
    }

    @Test
    public void getLastUpdate_called_throwsIllegalStateException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(IllegalStateException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(IllegalStateException.class));
    }

    @Test
    public void getLastUpdate_called_throwsQueryTimeoutException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(QueryTimeoutException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(QueryTimeoutException.class));
    }

    @Test
    public void getLastUpdate_called_throwsTransactionRequiredException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(TransactionRequiredException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(TransactionRequiredException.class));
    }

    @Test
    public void getLastUpdate_called_throwsPessimisticLockException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(PessimisticLockException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(PessimisticLockException.class));
    }

    @Test
    public void getLastUpdate_called_throwsLockTimeoutException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(LockTimeoutException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(LockTimeoutException.class));
    }

    @Test
    public void getLastUpdate_called_throwsPersistenceException() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        doThrow(PersistenceException.class).when(query).getResultList();
        RuntimeException re = null;

        try {
            helloRepository.getHello();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(MSG_ERRO_LAST_UPDATED, re.getMessage());
        assertThat(re.getCause(), instanceOf(PersistenceException.class));
    }

    @Test
    public void getLastUpdate_called_succeeds() {
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        HelloEntity he = new HelloEntity();
        Hello h1 = mock(Hello.class);
        when(helloMapper.map(he)).thenReturn(h1);
        ZonedDateTime now = ZonedDateTime.now();
        he.setModificationDate(now);
        List<HelloEntity> hel = mockList(HelloEntity.class);
        when(hel.isEmpty()).thenReturn(false);
        when(hel.get(0)).thenReturn(he);
        when(query.getResultList()).thenReturn(hel);

        Hello h2 = helloRepository.getHello();

        assertNotNull(h2);
        assertSame(h2, h1);
        verify(entityManager, times(1)).createQuery("SELECT h FROM HelloEntity h");
        verify(query, times(1)).setMaxResults(1);
        verify(query, times(1)).getResultList();
        verify(helloMapper, times(1)).map(he);
    }

}
