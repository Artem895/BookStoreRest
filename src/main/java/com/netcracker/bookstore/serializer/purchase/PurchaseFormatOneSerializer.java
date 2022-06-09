package com.netcracker.bookstore.serializer.purchase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Purchase;

import java.io.IOException;


public class PurchaseFormatOneSerializer extends StdSerializer<Purchase> {

    public PurchaseFormatOneSerializer(){
        this(null);
    }

    public PurchaseFormatOneSerializer(Class<Purchase> t) {
        super(t);
    }

    @Override
    public void serialize(Purchase purchase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("PurchaseId",purchase.getPid());
        jsonGenerator.writeStringField("CustomerLastName",purchase.getCustomer().getLname());
        jsonGenerator.writeStringField("StoreName",purchase.getStore().getLogo());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
