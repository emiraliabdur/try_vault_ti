package com.example.tryvault_ti.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoadAmountDeserializer extends StdDeserializer<BigDecimal> {

    public LoadAmountDeserializer() {
        super(BigDecimal.class);
    }

    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        String value = parser.getCodec().readValue(parser, String.class);

        return new BigDecimal(value.substring(1)).setScale(2, RoundingMode.HALF_UP);
    }
}
