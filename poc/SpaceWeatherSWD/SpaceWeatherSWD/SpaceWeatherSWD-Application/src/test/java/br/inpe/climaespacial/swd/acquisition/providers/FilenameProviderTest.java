package br.inpe.climaespacial.swd.acquisition.providers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;

@RunWith(CdiRunner.class)
@AdditionalClasses({ DefaultFilenameProvider.class,  })
public class FilenameProviderTest {

	@Produces
	@Mock
	@Any
	private LastRecordRepository lastRecordRepository;

	@Produces
	@Mock
	private DateTimeProvider dateTimeProvider;

	@Inject
	private FilenameProvider filenameProvider;

	@Before
	public void before() {

	}

	@Test
	public void getFilename_intervalLessThan5Minutes_return5MinFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:04:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("5-minute.json", fn);
	}

	@Test
	public void getFilename_intervalLessThan2Hours_return2HoursFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T13:59:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("2-hour.json", fn);
	}

	@Test
	public void getFilename_intervalLessThan6Hours_return6HoursFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T17:59:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("6-hour.json", fn);
	}

	@Test
	public void getFilename_intervalLessThan1Day_return1DayFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-02T11:59:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("1-day.json", fn);
	}

	@Test
	public void getFilename_intervalLessThan3Day_return3DayFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-03T11:59:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("3-day.json", fn);
	}

	@Test
	public void getFilename_intervalEquals5Minutes_return2HoursFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:05:00z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("2-hour.json", fn);
	}

	@Test
	public void getFilename_intervalEquals2Hours_return6HoursFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("6-hour.json", fn);
	}

	@Test
	public void getFilename_intervalEquals6Hours_return1DayFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T18:00:00z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("1-day.json", fn);
	}

	@Test
	public void getFilename_intervalEquals1Day_return3DayFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("3-day.json", fn);
	}

	@Test
	public void getFilename_intervalEquals3Day_return7DayFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("7-day.json", fn);
	}

	@Test
	public void getFilename_nullDate_returns7DayFilename() {
		ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:04:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(null);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("7-day.json", fn);
	}

	@Test
	public void getFilename_moreThan3Days_return7DayFilename() {
		ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime now = ZonedDateTime.parse("2017-01-07T11:59:59z[UTC]");
		when(lastRecordRepository.getLast()).thenReturn(lr);
		when(dateTimeProvider.getNow()).thenReturn(now);

		String fn = filenameProvider.getFilename();

		assertEquals("7-day.json", fn);
	}
}
