package hu.webler.weblerfeeder.controller;

import hu.webler.weblerfeeder.entity.Customer;
import hu.webler.weblerfeeder.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> listAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/add-customer")
    public void addCustomer(@RequestBody Customer newCustomer) {
        customerService.saveCustomer(newCustomer);
    }

    @PostMapping("/delete-customer/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
