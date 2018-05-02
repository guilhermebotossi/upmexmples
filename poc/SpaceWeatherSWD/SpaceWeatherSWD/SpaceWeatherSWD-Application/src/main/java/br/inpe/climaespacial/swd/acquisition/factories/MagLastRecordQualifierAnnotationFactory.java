package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;
import javax.enterprise.util.AnnotationLiteral;

public interface MagLastRecordQualifierAnnotationFactory {

	AnnotationLiteral<MagLastRecordQualifier> create();

}
