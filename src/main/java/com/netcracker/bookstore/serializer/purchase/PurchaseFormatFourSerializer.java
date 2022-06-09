package com.netcracker.bookstore.serializer.purchase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Purchase;

import java.io.IOException;

public class PurchaseFormatFourSerializer extends StdSerializer<Purchase> {

    public PurchaseFormatFourSerializer(){
        this(null);
    }

    public PurchaseFormatFourSerializer(Class<Purchase> t) {
        super(t);
    }

    @Override
    public void serialize(Purchase purchase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("CustomerLastName",purchase.getCustomer().getLname());
        jsonGenerator.writeStringField("CustomerArial",purchase.getCustomer().getArial());
        jsonGenerator.writeStringField("StoreArial",purchase.getStore().getSarial());
        jsonGenerator.writeStringField("PurchaseDate",String.valueOf(purchase.getPdate()));
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
