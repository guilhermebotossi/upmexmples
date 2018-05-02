package br.inpe.climaespacial.swd.acquisition.factories;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;

@Dependent
public class DefaultMagLastRecordQualifierAnnotationFactory implements MagLastRecordQualifierAnnotationFactory {

    @Override
    public AnnotationLiteral<MagLastRecordQualifier> create() {
        return new AnnotationLiteral<MagLastRecordQualifier>() {

			private static final long serialVersionUID = -2380624744067464979L;
        };
    }

}
