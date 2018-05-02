package br.inpe.climaespacial.swd.commons.entities;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.inpe.climaespacial.swd.commons.converters.ZonedDateTimeAttributeConverter;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "creation_date", nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime creationDate;

    @Column(name = "modification_date", nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime modificationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(ZonedDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
