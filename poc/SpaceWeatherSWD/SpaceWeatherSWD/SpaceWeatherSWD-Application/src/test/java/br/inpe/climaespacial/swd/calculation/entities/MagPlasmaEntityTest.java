package br.inpe.climaespacial.swd.calculation.entities;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;

public class MagPlasmaEntityTest {

	@Test
	public void before() {
		MagEntity magEntity = new MagEntity();
		PlasmaEntity plasmaEntity = new PlasmaEntity();
		MagPlasmaEntity magPlasmaEntity = new MagPlasmaEntity(magEntity, plasmaEntity);
		assertSame(magEntity, magPlasmaEntity.getMagEntity());
		assertSame(plasmaEntity, magPlasmaEntity.getPlasmaEntity());
	}

}
