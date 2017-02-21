package br.poc.db.impl;

import javax.persistence.EntityManager;

import br.poc.db.Database;
import br.poc.entity.Usuario;

public class UsuarioDAO implements Database<Usuario> {

	
	private EntityManager em;
	
	@Override
	public void insert(Usuario user) {
		em.persist(user);
	}

	@Override
	public Usuario find(Long id) {
		return em.find(Usuario.class, id);
	}

	@Override
	public void delete(Long id) {
		Usuario user = em.find(Usuario.class, id);
		em.remove(user);
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	
}
