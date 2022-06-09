package com.netcracker.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.netcracker.bookstore.exeption.AmountExeption;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.response.DeleteResponse;

import com.netcracker.bookstore.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    @Autowired
    public PurchaseController( PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @GetMapping
    public List<Purchase> listofpurchase(){
        return purchaseService.findall();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Purchase> findlistofpurchasebyid (@PathVariable(value = "id") int id){
        return new ResponseEntity<>(purchaseService.findbyid(id), HttpStatus.OK);
    }

    @PostMapping
    public Purchase createnewpurchase(@RequestBody String purchase) throws AmountExeption, UpdateExeption, JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Purchase deserialisedpurchase=objectMapper.readValue(purchase, Purchase.class);
        return purchaseService.save(deserialisedpurchase);
    }

    @PatchMapping
    public Purchase updatepurchase(@RequestBody String purchase) throws UpdateExeption, JsonProcessingException {
        return purchaseService.update(purchase);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deletebyid(@PathVariable(value = "id") int id){
        Purchase purchase= purchaseService.findbyid(id);
        purchaseService.delete(purchase);
        return ResponseEntity.ok(new DeleteResponse("purchase with id "+id+" was deleted"));
    }
    @PutMapping
    public Purchase fullupdate(@RequestBody String purchase) throws  UpdateExeption, JsonProcessingException {
        return purchaseService.update(purchase);

    }
    @GetMapping("/mounth")
    public Set<String> mounth(){
        return purchaseService.allmounth();
    }


    @GetMapping("/format/{whatformatis}")
    public String purchesformat(@PathVariable(value = "whatformatis") int numder) throws JsonProcessingException {
       return purchaseService.findallpercheseformat(numder);
    }

    @GetMapping("/findbycondition")
    public String purchasebwithcondition(@RequestParam(value="condition") double price) throws JsonProcessingException {
        return purchaseService.findbycondition(price);
    }

    @GetMapping("/withowenarial")
    public  String purcheswithowenarial(@RequestParam(value="cid") int cid,@RequestParam(value="userdate") String userdate) throws JsonProcessingException {
        return purchaseService.purchasewithowenarial(cid,userdate);
    }

    @GetMapping("/stores")
    public String purchesbystore(@RequestParam(value="badarial") String badarial,@RequestParam(value="one") double one,@RequestParam(value="two") double two) throws JsonProcessingException {
        return purchaseService.purchasewithoutbadarial(badarial,one,two);
    }

    @GetMapping("/bookswithcondition")
    public String purchasebookswithcondition(@RequestParam(value="bookamount") int bam) throws JsonProcessingException {
        return purchaseService.purchasebookswithcondition(bam);
    }
}
