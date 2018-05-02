package br.inpe.climaespacial.swd.commons.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.entities.TestEntity;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultTestEntityFactory.class, TestEntity.class})
public class EntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private TestEntityFactory testEntityFactory;

    @Test
    public void create_called_returnsEntity() {
        LocalDateTime firstDay_2014 = LocalDateTime.of(2014, Month.JANUARY, 1, 1, 1);
        ZonedDateTime firstDay_2014_zoned = ZonedDateTime.of(firstDay_2014, ZoneId.of("UTC"));
        UUID expectedUUID = UUID.fromString("672e0316-df1f-4589-a1fe-9b8abc1fc286");
        when(uuidProvider.getUUID()).thenReturn(expectedUUID);
        when(dateTimeProvider.getNow()).thenReturn(firstDay_2014_zoned);

        TestEntity testEntity = testEntityFactory.create();

        assertNotNull(testEntity);
        assertThat(testEntity, instanceOf(TestEntity.class));
        assertEquals(expectedUUID, testEntity.getId());
        assertEquals(firstDay_2014_zoned, testEntity.getCreationDate());
        assertEquals(firstDay_2014_zoned, testEntity.getModificationDate());
    }
}
