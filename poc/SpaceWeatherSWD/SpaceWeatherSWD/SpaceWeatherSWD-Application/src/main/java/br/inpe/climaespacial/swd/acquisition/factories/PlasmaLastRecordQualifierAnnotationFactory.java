package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;
import javax.enterprise.util.AnnotationLiteral;

public interface PlasmaLastRecordQualifierAnnotationFactory {

	AnnotationLiteral<PlasmaLastRecordQualifier> create();

}
