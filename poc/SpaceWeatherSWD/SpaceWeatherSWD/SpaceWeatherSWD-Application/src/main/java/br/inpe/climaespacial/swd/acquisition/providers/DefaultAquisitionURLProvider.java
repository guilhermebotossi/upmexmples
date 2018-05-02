package br.inpe.climaespacial.swd.acquisition.providers;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultAquisitionURLProvider implements AquisitionURLProvider {

	private String MAIN_URL = "http://services.swpc.noaa.gov/products/solar-wind/";

	@Override
	public String getURL() {
		return MAIN_URL;
	}
}
