package br.poc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.poc.mapper.dto.MagDTO;
import br.poc.mapper.mag.MagMapper;

public class MagMapperTest {
	
	private static final String PARAMETRO_CONTENT_NULL_EMPTY = "Parâmetro \"content\" null/empty.";
	private MagMapper magMapper;
	private String jsonFine = "[[\"time_tag\",\"bx_gsm\",\"by_gsm\",\"bz_gsm\",\"lon_gsm\",\"lat_gsm\",\"bt\"],[\"2017-03-08 13:02:00.000\",\"4.31\",\"-3.52\",\"-0.60\",\"320.74\",\"-6.15\",\"5.61\"],[\"2017-03-08 13:03:00.000\",\"4.35\",\"-3.50\",\"-0.38\",\"321.19\",\"-3.88\",\"5.60\"]]";
	
	
	@Before
	public void beforeTest() {
		magMapper = new MagMapper();		
	}
	
	@Test
	public void map_calledWithNullString_throws() {
		RuntimeException re = null;
		
		try {
			magMapper.map(null);
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals(PARAMETRO_CONTENT_NULL_EMPTY, re.getMessage());
	}

	@Test
	public void map_calledWithAnEmptyString_throws() {
		RuntimeException re = null;
		
		try {
			magMapper.map("");
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals(PARAMETRO_CONTENT_NULL_EMPTY, re.getMessage());
	}
	
	@Test	
	public void map_calledWithInvalidFormatContent_throws() {
		RuntimeException re = null;
		
		try {
			magMapper.map("aaaaaa;5555");
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals("Parametro \"content\" formato invalido", re.getMessage());
	}
	
	@Test
	public void map_calledWithValidArguments_succeeds() {
		List<MagDTO> listDto = magMapper.map(jsonFine);
		assertNotNull(listDto);
		assertTrue(listDto.size() == 2);
		
		MagDTO dto = listDto.get(0);
		
		assertEquals(dto.getTimeTag().toString(), "2017-03-08T13:02");
		assertTrue(dto.getBxGsm() == 4.31);
		assertTrue(dto.getByGsm() == -3.52);
		assertTrue(dto.getBzGsm() == -0.60);
		assertTrue(dto.getLonGsm() == 320.74);
		assertTrue(dto.getLatGsm() == -6.15);
		assertTrue(dto.getBt() == 5.61);
	}
	
	@Test
	public void toString_called_succeeds() {
		List<MagDTO> listDto = magMapper.map(jsonFine);
		assertNotNull(listDto);
		assertTrue(listDto.size() == 2);
		
		MagDTO dto = listDto.get(0);
		
		String toString = dto.toString();
		
		assertNotNull(toString);
		assertEquals("MagDTO [timeTag=2017-03-08T13:02, bxGsm=4.31, byGsm=-3.52, bzGsm=-0.6, latGsm=-6.15, lonGsm=320.74, bt=5.61]", toString);
	}

}
