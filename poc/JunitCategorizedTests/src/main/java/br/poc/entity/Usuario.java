package br.poc.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario extends AbstractEntity {
	
	@Id
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "usuario")
	private List<Telefone> telefones;
	
	
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + "]";
	}
	
	
	
	

}
