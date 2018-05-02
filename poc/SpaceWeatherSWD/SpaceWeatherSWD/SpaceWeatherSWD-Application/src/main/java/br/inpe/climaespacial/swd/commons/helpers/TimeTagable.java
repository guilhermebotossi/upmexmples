package br.inpe.climaespacial.swd.commons.helpers;

import java.time.ZonedDateTime;

public interface TimeTagable extends DeepCloneable {

    ZonedDateTime getTimeTag();

    void setTimeTag(ZonedDateTime timeTag);

}
