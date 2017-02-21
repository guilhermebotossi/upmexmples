package br.poc.tests;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.poc.category.DatabaseTestCategory;
import br.poc.db.PersistenceTestUtils;
import br.poc.db.impl.TelefoneDAO;
import br.poc.db.impl.UsuarioDAO;
import br.poc.entity.Telefone;
import br.poc.entity.Usuario;
import br.poc.enums.TipoTelefone;

@Category({DatabaseTestCategory.class})
public class TelefoneDatabaseTest {
	
	private static TelefoneDAO db = new TelefoneDAO();
	private static UsuarioDAO dbUser = new UsuarioDAO();
	
    @BeforeClass 
    public static void setUpClass() throws Exception {  
    	EntityManager em = PersistenceTestUtils.getEntityManager();
    	db.setEm(em);
    	dbUser.setEm(em);
    	
    	Usuario user = criaUsuario();
    	dbUser.insert(user);
    	
    	
    	Telefone tel = criaTelefone();
    	tel.setId(1L);
    	db.insert(tel);
    	
    	
    	tel = criaTelefone();
    	tel.setId(2L);
    	db.insert(tel);

    	
    	tel = criaTelefone();
    	tel.setId(3L);
    	db.insert(tel);
    }
	
	@Test
	public void insert(){
		Telefone tel = criaTelefone();
		db.insert(tel);
		Telefone usuario = db.find(tel.getId());
		Assert.assertTrue(tel.getId().equals(usuario.getId()));
	}
	
	@Test
	public void find() {
		Telefone tel = db.find(1L);
		Assert.assertTrue(tel.getId().equals(1L));		
	}
	
	@Test
	public void delete() {
		db.delete(2L);
		Telefone tel = db.find(2L);
		Assert.assertNull(tel);
	}
	
	private static Usuario criaUsuario() {
		Usuario user = new Usuario();
		user.setId(100L);
		return user;
	}
	
	
	private static Telefone criaTelefone() {
		Random rand = new Random();
		Telefone tel = new Telefone();
		tel.setId(rand.nextLong());
		tel.setTelefone("tel" + rand.nextLong());
		tel.setTipo(TipoTelefone.values()[ThreadLocalRandom.current().nextInt(0, 3)]);
		tel.setUsuario(criaUsuario());
		return tel;
	}
	
}
