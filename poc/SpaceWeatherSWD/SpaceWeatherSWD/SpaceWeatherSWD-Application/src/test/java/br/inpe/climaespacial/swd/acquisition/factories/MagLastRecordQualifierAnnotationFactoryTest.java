
package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;
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
@AdditionalClasses(DefaultMagLastRecordQualifierAnnotationFactory.class)
public class MagLastRecordQualifierAnnotationFactoryTest {
    
    @Inject
    MagLastRecordQualifierAnnotationFactory magLastRecordQualifierAnnotationFactory;
    
    @Test
    public void create_called_returnsMagLastRecordQualifierAnnotationFactoryAnnotationLiteral() {
        AnnotationLiteral<MagLastRecordQualifier> mlrqal = magLastRecordQualifierAnnotationFactory.create();
        assertThat(mlrqal, instanceOf(AnnotationLiteral.class));
        assertEquals(mlrqal.annotationType(), MagLastRecordQualifier.class);    
    }

}
