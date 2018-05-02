package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;

public interface KPDownloadHistoryWriterRepository {

    void save(KPDownloadHistory kpDownloadHistory);

    void markAsCompleted(ZonedDateTime period);

}
