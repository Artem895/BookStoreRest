package com.netcracker.bookstore.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.response.DeleteResponse;
import com.netcracker.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public List<Customer> listofcustomers(){
        return customerService.findall();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findcustomerbyid (@PathVariable(value = "id") int id){
        System.out.println(id);
        return new ResponseEntity<>(customerService.findbyid(id), HttpStatus.OK);
    }

    @PostMapping
    public Customer createnewcustomer(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    @PatchMapping
    public Customer updatecustomer(@RequestBody Customer customer) throws UpdateExeption {
        return customerService.update(customer);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deletebyid(@PathVariable(value = "id") int id){
        Customer customer= customerService.findbyid(id);
        customerService.delete(customer);
        return ResponseEntity.ok(new DeleteResponse("customer with id "+id+" was deleted"));
    }
    @PutMapping
    public Customer fullupdate(@RequestBody Customer newcustomer)  {
        return customerService.save(newcustomer);

    }
    @GetMapping("/arials")
    public List<String> allarials() {
        return customerService.allarials();
    }

    @GetMapping( "/findcustomerbyarial" )
    public String findcustomerbyarial(@RequestParam(value = "arial") String arial ) throws JsonProcessingException {
        System.out.println(arial);
        return customerService.allcustomersfromthisarial(arial);
    }

}
