package br.inpe.climaespacial.swd.kp.values.dtos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPForecastData.class)
public class KPForecastDataTest {

    @Inject
    private KPForecastData kpForecastData;
 
    @Test
    public void kpForecastEntries() {
       List<BaseKPForecastEntry> lista = new ArrayList<>();
       kpForecastData.setKpForecastEntries(lista);
         
      assertNotNull(kpForecastData.getKpForecastEntries());
      assertEquals(lista, kpForecastData.getKpForecastEntries());
    }
}
