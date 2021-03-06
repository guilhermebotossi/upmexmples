package br.poc.mapper.mag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.poc.mapper.dto.MagDTO;

public class MagMapper {

	public List<MagDTO> map(String content) {
		check(content);
		return mapAll(content);
	}
	
	private List<MagDTO> mapAll(String content) {
		List<MagDTO> listDTO = new ArrayList<>();
		
		JSONArray jarray = parseStringToJson(content);
		
		for(int i = 1; i < jarray.size(); i++) {
			listDTO.add(createMapper((JSONArray)jarray.get(i)));
		}
		
		return listDTO;
	}
	
	private JSONArray parseStringToJson(String content) {
		try {
			
			JSONParser parser = new JSONParser();
			return (JSONArray) parser.parse(content);
		} catch (ParseException e) {
			throw new RuntimeException("Parametro \"content\" formato invalido");
		}
	}
	
	private void check(String content) {
		if(content == null || content.isEmpty()) {
			throw new RuntimeException("Parâmetro \"content\" null/empty.");
		}
	}

	private MagDTO createMapper(JSONArray json) {
		MagDTO dto = new MagDTO();
		
		LocalDateTime ldt = parseStringToDateTime(json.get(0).toString());
		
		dto.setTimeTag(ldt);
		dto.setBxGsm(getDoubleValue(json.get(1)));
		dto.setByGsm(getDoubleValue(json.get(2)));
		dto.setBzGsm(getDoubleValue(json.get(3)));
		dto.setLonGsm(getDoubleValue(json.get(4)));
		dto.setLatGsm(getDoubleValue(json.get(5)));
		dto.setBt(getDoubleValue(json.get(6)));
		
		return dto;
	}

	private double getDoubleValue(Object obj) {
		return Double.valueOf(((String)obj)).doubleValue();
	}

	private LocalDateTime parseStringToDateTime(String dateTime) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		return LocalDateTime.parse(dateTime, dtf);
	}


}
