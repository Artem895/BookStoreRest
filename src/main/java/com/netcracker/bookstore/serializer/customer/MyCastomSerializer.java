package com.netcracker.bookstore.serializer.customer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Customer;

import java.io.IOException;
import java.util.List;

public class MyCastomSerializer extends StdSerializer<Customer>{



    public MyCastomSerializer() {
        this(null);
    }

    public MyCastomSerializer(Class<Customer> t) {
        super(t);
    }

    @Override
    public void serialize(Customer customer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("LastName", customer.getLname());
        jsonGenerator.writeNumberField("Discount", customer.getDiscount());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
