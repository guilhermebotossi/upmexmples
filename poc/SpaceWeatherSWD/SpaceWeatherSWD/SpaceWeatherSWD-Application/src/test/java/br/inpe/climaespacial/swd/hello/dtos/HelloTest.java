package br.inpe.climaespacial.swd.hello.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(Hello.class)
public class HelloTest {

    @Inject
    private Hello hello;

    @Test
    public void messageTest() {
        ZonedDateTime md1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        hello.setModificationDate(md1);
        ZonedDateTime md2 = hello.getModificationDate();

        assertEquals(md2, md1);
    }

}
