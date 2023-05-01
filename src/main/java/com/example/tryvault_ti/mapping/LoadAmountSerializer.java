package com.example.tryvault_ti.mapping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoadAmountSerializer extends StdSerializer<BigDecimal> {

    public LoadAmountSerializer() {
        super(BigDecimal.class);
    }

    @Override
    public void serialize(BigDecimal money, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeString("$" + money.setScale(2, RoundingMode.HALF_UP));
    }
}
