package com.netcracker.bookstore.serializer.purchase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Purchase;

import java.io.IOException;

public class PurchaseFormatThreeSerializer extends StdSerializer<Purchase> {


    public PurchaseFormatThreeSerializer(){
        this(null);
    }

    public PurchaseFormatThreeSerializer(Class<Purchase> t) {
        super(t);
    }

    @Override
    public void serialize(Purchase purchase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("PurchaseNumber",purchase.getNumber());
        jsonGenerator.writeStringField("PurchaseDate",String.valueOf(purchase.getPdate()));
        jsonGenerator.writeStringField("CustomerLastName",purchase.getCustomer().getLname());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
