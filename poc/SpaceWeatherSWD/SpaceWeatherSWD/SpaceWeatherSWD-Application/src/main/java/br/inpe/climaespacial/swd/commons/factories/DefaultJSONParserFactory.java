package br.inpe.climaespacial.swd.commons.factories;

import javax.enterprise.context.Dependent;

import org.json.simple.parser.JSONParser;

@Dependent
public class DefaultJSONParserFactory implements JSONParserFactory {

    @Override
    public JSONParser create() {
        return new JSONParser();
    }


}
