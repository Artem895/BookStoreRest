package com.netcracker.bookstore.serializer.purchase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Purchase;

import java.io.IOException;

public class PurchaseFormatTwoSerializer  extends StdSerializer<Purchase> {

    public PurchaseFormatTwoSerializer(){
        this(null);
    }

    public PurchaseFormatTwoSerializer(Class<Purchase> t) {
        super(t);
    }

    @Override
    public void serialize(Purchase purchase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("PurchaseId",purchase.getPid());
        jsonGenerator.writeStringField("PurchaseDate",String.valueOf(purchase.getPdate()));
        jsonGenerator.writeStringField("CustomerLastName",purchase.getCustomer().getLname());
        jsonGenerator.writeNumberField("CustomerDiscount",purchase.getCustomer().getDiscount());
        jsonGenerator.writeStringField("BookName",purchase.getBook().getBname());
        jsonGenerator.writeNumberField("Amount",purchase.getAmount());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
