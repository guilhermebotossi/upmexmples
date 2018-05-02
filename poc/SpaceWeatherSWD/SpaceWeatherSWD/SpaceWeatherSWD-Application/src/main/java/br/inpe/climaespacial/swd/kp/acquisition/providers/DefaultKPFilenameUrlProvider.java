package br.inpe.climaespacial.swd.kp.acquisition.providers;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPDownloadHistoryReaderRepository;

@Dependent
public class DefaultKPFilenameUrlProvider implements KPFilenameUrlProvider {

    @Inject
    private DateTimeProvider dateTimeProvider;

    @Inject
    private KPDownloadHistoryReaderRepository kpLastRecordRepository;

    @Override
    public String getFilenameUrl() {
        ZonedDateTime last = kpLastRecordRepository.getNextDateToBeDownloaded();
        if (last == null) {
            return "ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/tab/kp1102.tab";
        }
        
        last = last.plusMonths(1);
        int lmv = last.getMonthValue();
        int ly = last.getYear();
        ZonedDateTime now = dateTimeProvider.getNow();
        int nmv = now.getMonthValue();
        int ny = now.getYear();
        
//        if (ly <= ny | lmv + 1 <= nmv ) {
        if (ly < ny ) {
            return getName(last, lmv);
        }
        
//        if (lmv + 1 == nmv && ly == ny) {
        if (ly == ny && lmv < nmv ) {
            return getName(last, lmv);
        } else if(ly == ny && lmv == nmv) {
            return getKPRealtimeUrl() ;
        }
        
        throw new RuntimeException("Invalid URL");
    }

    private String getName(ZonedDateTime last, int lmv) {
        String year = String.valueOf(last.getYear()).substring(2);
        String month = "0" + String.valueOf(lmv);
        month = month.substring(month.length() - 2);
        return "ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/tab/" + "kp" + year + month + ".tab";
    }

	@Override
	public String getKPRealtimeUrl() {
		return "http://www-app3.gfz-potsdam.de/kp_index/qlyymm.tab";
	}

}
