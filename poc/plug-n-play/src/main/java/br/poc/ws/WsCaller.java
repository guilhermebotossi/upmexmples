package br.poc.ws;

import br.poc.dto.WsData;

public interface WsCaller {
	
	public String formatUrl(WsData data);
	
	public String callWs(WsData data);

}
