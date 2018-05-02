package br.inpe.climaespacial.swd.commons.providers;

import java.util.UUID;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultUUIDProvider implements UUIDProvider {

	@Override
	public UUID getUUID() {
		return UUID.randomUUID();
	}
}
