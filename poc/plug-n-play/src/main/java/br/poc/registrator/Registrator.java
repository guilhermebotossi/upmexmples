package br.poc.registrator;

import br.poc.dto.WsDef;
import br.poc.ws.WsCaller;

public interface Registrator {
	
	public void register(String service, WsDef wsDef, Class<WsCaller> wsCaller);
	
	public WsDef getWsDef(String service);
	
	public Class<WsCaller> getWsCallerClass(String service);
	
	public WsCaller getInstance(String service) throws Exception;

}
