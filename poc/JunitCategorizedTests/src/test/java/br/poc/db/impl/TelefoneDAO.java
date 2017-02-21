package br.poc.db.impl;

import javax.persistence.EntityManager;

import br.poc.db.Database;
import br.poc.entity.Telefone;

public class TelefoneDAO implements Database<Telefone> {

	
	private EntityManager em;
	
	@Override
	public void insert(Telefone tel) {
		em.persist(tel);
	}

	@Override
	public Telefone find(Long id) {
		return em.find(Telefone.class, id);
	}

	@Override
	public void delete(Long id) {
		Telefone tel = em.find(Telefone.class, id);
		em.remove(tel);
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	
}
