package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import java.time.ZonedDateTime;

public interface KPDownloadHistoryReaderRepository {

    ZonedDateTime getNextDateToBeDownloaded();

}
