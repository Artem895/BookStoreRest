package com.netcracker.bookstore.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.response.DeleteResponse;
import com.netcracker.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BooksController {
    private final BookService bookService;
    @Autowired
    public BooksController( BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    @ResponseBody
    public List<Book> listofbooks()  {
        return bookService.findall();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> findbookbyid (@PathVariable(value = "id") int id)  {
        return new ResponseEntity<>(bookService.findbyid(id), HttpStatus.OK);
    }

    @PostMapping
    public Book createnewbook(@RequestBody String book) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Book deserialisedbook=objectMapper.readValue(book,Book.class);
        return bookService.save(deserialisedbook);
    }

    @PatchMapping
    public Book updatebook(@RequestBody String book) throws UpdateExeption, JsonProcessingException {
        System.out.println(book);
        return bookService.update(book);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deletebyid(@PathVariable(value = "id") int id)  {
        Book book= bookService.findbyid(id);
        bookService.delete(book);
        return ResponseEntity.ok(new DeleteResponse("book with id"+id+" was deleted"));
    }
    @PutMapping
    public Book fullupdate(@RequestBody String book) throws JsonProcessingException, UpdateExeption {
        return bookService.update(book);

    }
    @GetMapping("/unique")
    public Map<String,Double> findalluniquebook(){
        return bookService.findunique();
    }


    @GetMapping("/selectbystringandprice")
    public String  boksbypriceandsubstring(@RequestParam(value="substring") String substring,@RequestParam(value = "priceupper") String price) throws JsonProcessingException {
        return bookService.findbysubstringandprice(substring,Double.parseDouble(price));
    }
}
