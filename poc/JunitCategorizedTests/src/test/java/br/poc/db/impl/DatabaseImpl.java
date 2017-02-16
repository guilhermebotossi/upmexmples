package br.poc.db.impl;

import br.poc.db.Database;

public class DatabaseImpl implements Database {

	@Override
	public Long insert(Long id) {
		System.out.println("Inserido Objeto de id" + id);
		return id;

	}

	@Override
	public Long find(Long id) {
		System.out.println("Localizado Objeto  de id : " + id);
		return id;
	}

	@Override
	public Long delete(Long id) {
		System.out.println("Deletado Objeto  de id : " + id);
		return id;
	}

}
