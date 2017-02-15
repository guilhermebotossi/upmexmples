package br.poc.registrator.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import br.poc.dto.WsDef;
import br.poc.registrator.Registrator;
import br.poc.ws.WsCaller;

@Singleton
@Startup
public class RegistratorImpl implements Registrator {
	
	Map<String, WsDef> webServices = new HashMap<String, WsDef>();
	Map<String, Class<WsCaller>> webServiceCallers = new HashMap<String, Class<WsCaller>>();

	public WsDef getWsDef(String service) {
		return webServices.get(service);
	}


	public void register(String service, WsDef wsDef, Class<WsCaller> wsCaller) {
		webServices.put(service, wsDef);
		webServiceCallers.put(service, wsCaller);
	}

	public Class<WsCaller> getWsCallerClass(String service) {
		return webServiceCallers.get(service);
	}


	public WsCaller getInstance(String service) throws Exception {
		Class<WsCaller> clazz = webServiceCallers.get(service);
		
		return clazz.newInstance();
	}
	
}
