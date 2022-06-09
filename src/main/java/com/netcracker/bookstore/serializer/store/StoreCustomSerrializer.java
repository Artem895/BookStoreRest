package com.netcracker.bookstore.serializer.store;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.model.Store;

import java.io.IOException;

public class StoreCustomSerrializer extends StdSerializer<Store> {


    public StoreCustomSerrializer(){
        this(null);
    }

    public StoreCustomSerrializer(Class<Store> t) {
        super(t);
    }

    @Override
    public void serialize(Store store, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("Store name",store.getLogo());
        jsonGenerator.writeEndObject();
    }
}
