package com.crmapp.controller;

import com.crmapp.entity.Customer;
import com.crmapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @PostMapping
    public Customer add(@RequestBody Customer customer) {
        System.out.println("Customer: " + customer.getName());
        return customerRepo.save(customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerRepo.deleteById(id);
    }
}
