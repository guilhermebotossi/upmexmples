package br.poc.mapper.plasma;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.poc.mapper.dto.PlasmaDTO;

public class PlasmaMapper {

	public List<PlasmaDTO> map(String content) {
		check(content);
		return mapAll(content);
	}

	private List<PlasmaDTO> mapAll(String content) {
		List<PlasmaDTO> listDTO = new ArrayList<>();
		
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

	private PlasmaDTO createMapper(JSONArray json) {
		PlasmaDTO dto = new PlasmaDTO();
					
		LocalDateTime dateTime = parseStringToDateTime(json);
		
		dto.setTimeTag(dateTime);
		dto.setDensity(Double.valueOf((String)json.get(1)).doubleValue());
		dto.setSpeed(Double.valueOf((String)json.get(2)).doubleValue());
		dto.setTemperature(Double.valueOf((String)json.get(3)).doubleValue());
		return dto;
	}

	private LocalDateTime parseStringToDateTime(JSONArray json) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime dateTime = LocalDateTime.parse((String) json.get(0), dtf);
		return dateTime;
	}

}
