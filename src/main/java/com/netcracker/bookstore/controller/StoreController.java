package com.netcracker.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Store;
import com.netcracker.bookstore.response.DeleteResponse;
import com.netcracker.bookstore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }


    @GetMapping
    public List<Store> listofstores(){
        return storeService.findall();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Store> findstorebyid (@PathVariable(value = "id") int id){
        return new ResponseEntity<>(storeService.findbyid(id), HttpStatus.OK);
    }

    @PostMapping
    public Store createnewstore(@RequestBody Store store){
        return storeService.save(store);
    }

    @PatchMapping
    public Store updatestore(@RequestBody Store store) throws UpdateExeption {
        return storeService.update(store);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deletebyid(@PathVariable(value = "id") int id){
        Store store= storeService.findbyid(id);
        storeService.delete(store);
        return ResponseEntity.ok(new DeleteResponse("store with id "+id+" was deleted"));
    }
    @PutMapping
    public Store fullupdate(@RequestBody Store newstore)  {
        return storeService.save(newstore);

    }
    @GetMapping("/logoinarial")
    public String logoimarial(@RequestParam(value = "arials") String... arials) throws JsonProcessingException {
        return storeService.logobyarial(arials);
    }
}
