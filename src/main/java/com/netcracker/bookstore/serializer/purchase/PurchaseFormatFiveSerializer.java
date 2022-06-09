package com.netcracker.bookstore.serializer.purchase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Purchase;

import java.io.IOException;

public class PurchaseFormatFiveSerializer extends StdSerializer<Purchase> {

    public PurchaseFormatFiveSerializer(){
        this(null);
    }

    public PurchaseFormatFiveSerializer(Class<Purchase> t) {
        super(t);
    }

    @Override
    public void serialize(Purchase purchase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("StoreName",purchase.getStore().getLogo());
        jsonGenerator.writeStringField("StoreArial",purchase.getStore().getSarial());
        jsonGenerator.writeStringField("Customer",purchase.getCustomer().getLname());
        jsonGenerator.writeNumberField("Discount",purchase.getCustomer().getDiscount());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
