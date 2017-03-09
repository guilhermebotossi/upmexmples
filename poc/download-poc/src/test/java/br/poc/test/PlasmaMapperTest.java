package br.poc.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.poc.mapper.dto.PlasmaDTO;
import br.poc.mapper.plasma.PlasmaMapper;

public class PlasmaMapperTest {
	
	private static final String PARÂMETRO_CONTENT_NULL_EMPTY = "Parâmetro \"content\" null/empty.";

	private static final String JSON_FINE = "[[\"time_tag\",\"density\",\"speed\",\"temperature\"],[\"2017-03-08 13:02:00.000\",\"5.44\",\"528.5\",\"246572\"],[\"2017-03-08 13:03:00.000\",\"5.49\",\"529.3\",\"246319\"],[\"2017-03-08 13:04:00.000\",\"5.45\",\"528.0\",\"251581\"]]";
	
	private PlasmaMapper plasmaMapper;
	
	@Before
	public void beforeTest() {
		plasmaMapper = new PlasmaMapper();
	}
	
	@Test
	public void map_calledWithNullString_throws() {
		RuntimeException re = null;
		
		try {
			plasmaMapper.map(null);
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals(PARÂMETRO_CONTENT_NULL_EMPTY, re.getMessage());
	}
	
	@Test
	public void map_calledWithEmptyString_throws() {
		RuntimeException re = null;
		
		try {
			plasmaMapper.map("");
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals(PARÂMETRO_CONTENT_NULL_EMPTY, re.getMessage());
	}
	
	@Test
	public void map_calledWithInvalidFormatArgument_throws() {
		RuntimeException re = null;
		
		try {
			plasmaMapper.map("ajdfdnad;555");
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals("Parametro \"content\" formato invalido", re.getMessage());
	}

	
	@Test
	public void map_calledWithValidFormatArgument_succeeds() {
		List<PlasmaDTO> listDTO = plasmaMapper.map(JSON_FINE);
		
		assertNotNull(listDTO);
		assertTrue(listDTO.size() == 3);
		
		PlasmaDTO dto = listDTO.get(0);
		
		assertEquals(dto.getTimeTag().toString(), "2017-03-08T13:02");
		assertTrue(dto.getDensity() == 5.44);
		assertTrue(dto.getSpeed() == 528.5);
		assertTrue(dto.getTemperature() == 246572);
	}
	
	@Test
	public void toString_called_succeeds() {
		List<PlasmaDTO> listDTO = plasmaMapper.map(JSON_FINE);
		
		assertNotNull(listDTO);
		assertTrue(listDTO.size() == 3);
		
		PlasmaDTO dto = listDTO.get(0);
		
		String toString = dto.toString();
		
		assertNotNull(toString);
		assertEquals("PlasmaDTO [timeTag=2017-03-08T13:02, density=5.44, speed=528.5, temperature=246572.0]", toString);
	}
	

}
