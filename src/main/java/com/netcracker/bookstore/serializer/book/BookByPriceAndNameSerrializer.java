package com.netcracker.bookstore.serializer.book;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.bookstore.model.Book;


import java.io.IOException;

public class BookByPriceAndNameSerrializer extends StdSerializer<Book> {

    public BookByPriceAndNameSerrializer(){
        this(null);
    }

    public BookByPriceAndNameSerrializer(Class<Book> t) {
        super(t);
    }

    @Override
    public void serialize(Book book, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("BookName", book.getBname());
        jsonGenerator.writeNumberField("price",book.getPrice());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeRaw('\n');
    }
}
