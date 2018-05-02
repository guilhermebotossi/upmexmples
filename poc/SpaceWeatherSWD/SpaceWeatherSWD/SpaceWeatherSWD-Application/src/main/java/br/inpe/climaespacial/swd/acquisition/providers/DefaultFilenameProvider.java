package br.inpe.climaespacial.swd.acquisition.providers;

import java.time.Duration;
import java.time.ZonedDateTime;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;

public class DefaultFilenameProvider implements FilenameProvider {

	private LastRecordRepository lastRecordRepository;

	private DateTimeProvider dateTimeProvider;

	@Inject
	public DefaultFilenameProvider(@Any LastRecordRepository lastRecordRepository, DateTimeProvider dateTimeProvider) {
		this.lastRecordRepository = lastRecordRepository;
		this.dateTimeProvider = dateTimeProvider;
	}

	@Override
	public String getFilename() {
		ZonedDateTime lr = lastRecordRepository.getLast();

		if (lr != null) {
			ZonedDateTime now = dateTimeProvider.getNow();
			Duration diff = Duration.between(lr, now);

			Duration limit3Days = Duration.ofDays(3);
			Duration limit1Day = Duration.ofDays(1);
			Duration limit6Hours = Duration.ofHours(6);
			Duration limit2Hours = Duration.ofHours(2);
			Duration limit5minutes = Duration.ofMinutes(5);

			if (diff.compareTo(limit5minutes) < 0) {
				return "5-minute.json";
			} else if (diff.compareTo(limit2Hours) < 0) {
				return "2-hour.json";
			} else if (diff.compareTo(limit6Hours) < 0) {
				return "6-hour.json";
			} else if (diff.compareTo(limit1Day) < 0) {
				return "1-day.json";
			} else if (diff.compareTo(limit3Days) < 0) {
				return "3-day.json";
			}
		}

		return "7-day.json";
	}
}
