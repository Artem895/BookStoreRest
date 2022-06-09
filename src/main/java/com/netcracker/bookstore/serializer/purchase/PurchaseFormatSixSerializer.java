package com.netcracker.bookstore.serializer.purchase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Purchase;

import java.io.IOException;

public class PurchaseFormatSixSerializer extends StdSerializer<Purchase> {

    public PurchaseFormatSixSerializer(){
        this(null);
    }

    public PurchaseFormatSixSerializer(Class<Purchase> t) {
        super(t);
    }

    @Override
    public void serialize(Purchase purchase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("PurchaseId",purchase.getPid());
        jsonGenerator.writeStringField("BookName",purchase.getBook().getBname());
        jsonGenerator.writeStringField("StoreArial",purchase.getStore().getSarial());
        jsonGenerator.writeStringField("BookArial",purchase.getBook().getWarehouse().getSarial());
        jsonGenerator.writeNumberField("Anount",purchase.getBook().getAmount());
        jsonGenerator.writeNumberField("Price",purchase.getBook().getPrice());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
