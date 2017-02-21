package br.poc.db;

import br.poc.entity.AbstractEntity;

public interface Database<E extends AbstractEntity> {
	
	public void insert(E entidade);
	public E find(Long id);
	public void delete(Long id);

}
