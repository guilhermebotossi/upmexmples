package br.inpe.climaespacial.swd.acquisition.factories;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;

@Dependent
public class DefaultPlasmaLastRecordQualifierAnnotationFactory implements PlasmaLastRecordQualifierAnnotationFactory {

    @SuppressWarnings("serial")
	@Override
    public AnnotationLiteral<PlasmaLastRecordQualifier> create() {
        return new AnnotationLiteral<PlasmaLastRecordQualifier>() {
        };
    }

}
