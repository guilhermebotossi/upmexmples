
package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultPlasmaLastRecordQualifierAnnotationFactory.class)
public class PlasmaLastRecordQualifierAnnotationFactoryTest {
    
    @Inject
    PlasmaLastRecordQualifierAnnotationFactory plasmaLastRecordQualifierAnnotationFactory;
    
    @Test
    public void create_called_returnsPlasmaLastRecordQualifierAnnotationLiteral() {
        AnnotationLiteral<PlasmaLastRecordQualifier> plrqal = plasmaLastRecordQualifierAnnotationFactory.create();
        assertThat(plrqal, instanceOf(AnnotationLiteral.class));
        assertEquals(plrqal.annotationType(), PlasmaLastRecordQualifier.class);        
    }

}
