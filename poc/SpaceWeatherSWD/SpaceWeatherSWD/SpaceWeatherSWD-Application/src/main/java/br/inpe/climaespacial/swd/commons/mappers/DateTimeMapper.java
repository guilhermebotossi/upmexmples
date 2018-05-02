package br.inpe.climaespacial.swd.commons.mappers;

import java.time.ZonedDateTime;

public interface DateTimeMapper {

	ZonedDateTime map(String content);

}
