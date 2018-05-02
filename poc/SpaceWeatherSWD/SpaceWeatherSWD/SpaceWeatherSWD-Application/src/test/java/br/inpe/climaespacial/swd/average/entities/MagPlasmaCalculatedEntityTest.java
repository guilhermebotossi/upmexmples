
package br.inpe.climaespacial.swd.average.entities;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    MagPlasmaCalculatedEntity.class,
    MagEntity.class,
    PlasmaEntity.class,
    CalculatedValuesEntity.class
})
public class MagPlasmaCalculatedEntityTest {
    
    @Inject
    private MagPlasmaCalculatedEntity magPlasmaCalculatedEntity;
    
    @Test
    public void magEntityTest(){
        MagEntity magEntity = new MagEntity();
        
        magPlasmaCalculatedEntity.setMagEntity(magEntity);
        
        assertSame(magEntity, magPlasmaCalculatedEntity.getMagEntity());
    }
    
    @Test
    public void plasmaEntityTest(){
        PlasmaEntity plasmaEntity = new PlasmaEntity();
        
        magPlasmaCalculatedEntity.setPlasmaEntity(plasmaEntity);
        
        assertSame(plasmaEntity, magPlasmaCalculatedEntity.getPlasmaEntity());
    }
    
    @Test
    public void calculatedValuesEntityTest(){        
        CalculatedValuesEntity calculatedValuesEntity = new CalculatedValuesEntity();
        
        magPlasmaCalculatedEntity.setCalculatedValuesEntity(calculatedValuesEntity);
        
        assertSame(calculatedValuesEntity, magPlasmaCalculatedEntity.getCalculatedValuesEntity());
    }
    
    
    

}
