package br.poc.registrator;

import br.poc.dto.WsDef;
import br.poc.ws.WsCaller;

@SuppressWarnings("rawtypes")
public interface Registrator {
	
	public void register(String service, WsDef wsDef, Class wsCaller);
	
	public WsDef getWsDef(String service);
	
	public Class getWsCallerClass(String service);
	
	public WsCaller getInstance(String service) throws Exception;

}
