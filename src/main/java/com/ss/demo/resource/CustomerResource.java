package com.ss.demo.resource;

import com.ss.demo.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerResource {

    @RequestMapping("/{id}")
    @GetMapping
    Customer fetchCustomer(@PathVariable("id") String id) {
        Customer customer = new Customer();
        customer.setFirstname("Test First Name");
        customer.setLastName("Test Last Name");
        return customer;
    }

}
