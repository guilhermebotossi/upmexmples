package br.inpe.climaespacial.swd.hello.entities;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.inpe.climaespacial.swd.commons.entities.BaseEntity;

@Dependent
@Entity
@Table(name = "hello", schema = "swd")
public class HelloEntity extends BaseEntity {

}
