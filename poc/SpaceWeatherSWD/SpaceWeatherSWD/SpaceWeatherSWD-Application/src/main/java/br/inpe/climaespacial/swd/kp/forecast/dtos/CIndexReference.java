package br.inpe.climaespacial.swd.kp.forecast.dtos;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import javax.enterprise.context.Dependent;

@Dependent
public class CIndexReference {
    
    private int index;
    private CIndex reference;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CIndex getReference() {
        return reference;
    }

    public void setReference(CIndex reference) {
        this.reference = reference;
    }
    
}
