package br.inpe.climaespacial.swd.kp.acquisition.mappers;

import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;

public interface KPDownloadHistoryEntityMapper {
    
    KPDownloadHistoryEntity map(KPDownloadHistory kpDownloadHistory);
    
}
