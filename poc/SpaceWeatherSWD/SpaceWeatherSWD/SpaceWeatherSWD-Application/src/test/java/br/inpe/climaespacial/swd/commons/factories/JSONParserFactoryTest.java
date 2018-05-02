package br.inpe.climaespacial.swd.commons.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultJSONParserFactory.class})
public class JSONParserFactoryTest {

    @Inject
    private JSONParserFactory jsonParseFactory;

    @Test
    public void create_called_returnsInstanceOfList() { 
        JSONParser jp = jsonParseFactory.create();

        assertNotNull(jp);
        assertEquals(JSONParser.class, jp.getClass());
    }
}
