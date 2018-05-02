package br.inpe.climaespacial.swd.kp.values.factories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastData;
import br.inpe.climaespacial.swd.kp.values.dtos.BaseKPForecastEntry;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPForecastDataFactory.class,
    KPForecast.class
})
public class KPForecastDataFactoryTest {

    @Inject
    private KPForecastDataFactory kpForecastDataFactory;
    
    @Test
    public void create_called_returnsKpForecastData() {
        List<BaseKPForecastEntry> bkpfel = mockList(BaseKPForecastEntry.class);

        KPForecastData kpfd = kpForecastDataFactory.create(bkpfel);

        assertNotNull(kpfd);
        assertSame(bkpfel, kpfd.getKpForecastEntries());
    }

}
