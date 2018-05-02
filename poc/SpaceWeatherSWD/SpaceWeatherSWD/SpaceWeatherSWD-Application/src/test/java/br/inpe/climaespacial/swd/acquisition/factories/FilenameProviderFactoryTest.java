package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.acquisition.providers.DefaultFilenameProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultFilenameProviderFactory.class})
public class FilenameProviderFactoryTest {

    @Inject
    private FilenameProviderFactory filenameProviderFactory;

    @Test
    public void create_calledWithNullLastRecordRepository_throws() {
        RuntimeException re = null;

        try {
            filenameProviderFactory.create(null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"lastRecordRepository\" cannot be null.", re.getMessage());
    }

    @Test
    public void create_calledWithNullDateTimeProvider_throws() {
        RuntimeException re = null;

        try {
            filenameProviderFactory.create(mock(LastRecordRepository.class), null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"dateTimeProvider\" cannot be null.", re.getMessage());
    }

    @Test
    public void create_called_returnsInstance() {
        LastRecordRepository lrr = mock(LastRecordRepository.class);
        DateTimeProvider dtp = mock(DateTimeProvider.class);

        FilenameProvider fp = filenameProviderFactory.create(lrr, dtp);

        assertNotNull(fp);
        assertThat(fp, instanceOf(DefaultFilenameProvider.class));
    }

}
