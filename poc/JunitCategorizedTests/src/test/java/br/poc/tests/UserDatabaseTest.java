package br.poc.tests;

import java.util.Random;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.poc.category.DatabaseTestCategory;
import br.poc.db.PersistenceTestUtils;
import br.poc.db.impl.UsuarioDAO;
import br.poc.entity.Usuario;

@Category({DatabaseTestCategory.class})
public class UserDatabaseTest {
	
	private static UsuarioDAO db = new UsuarioDAO();
	
    @BeforeClass 
    public static void setUpClass() throws Exception {  
    	EntityManager em = PersistenceTestUtils.getEntityManager();
    	db.setEm(em);
    	Usuario user = criaUsuario();
    	user.setId(1L);
    	db.insert(user);
    	
    	user = criaUsuario();
    	user.setId(2L);
    	db.insert(user);
    	
    	user = criaUsuario();
    	user.setId(3L);
    	db.insert(user);
    	
    }
	
	@Test
	public void insert(){
		Usuario user = criaUsuario();
		db.insert(user);
		Usuario usuario = db.find(user.getId());
		Assert.assertTrue(user.getId().equals(usuario.getId()));
	}
	
	@Test
	public void find() {
		Usuario user = db.find(1L);
		Assert.assertTrue(user.getId().equals(1L));		
	}
	
	@Test
	public void delete() {
		db.delete(2L);
		Usuario user = db.find(2L);
		Assert.assertNull(user);
	}
	
	private static Usuario criaUsuario() {
		Usuario user = new Usuario();
		Random rand = new Random();
		user.setId(rand.nextLong());
		user.setNome("Teste"+ rand.nextLong());
		return user;
	}

}
