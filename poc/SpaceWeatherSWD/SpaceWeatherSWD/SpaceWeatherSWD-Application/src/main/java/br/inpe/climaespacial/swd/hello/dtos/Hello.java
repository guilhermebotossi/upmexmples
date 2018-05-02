package br.inpe.climaespacial.swd.hello.dtos;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class Hello {

    private ZonedDateTime modificationDate;

    public ZonedDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(ZonedDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
