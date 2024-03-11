package hu.webler.weblerfeeder.controller;

import hu.webler.weblerfeeder.model.CustomerCreateModel;
import hu.webler.weblerfeeder.model.CustomerModel;
import hu.webler.weblerfeeder.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerModel>> listAllCustomers() {
        return ResponseEntity.status(200).body(customerServiceImpl.getAllCustomers()) ;
    }

    @GetMapping("/customers/email/{email}")
    public ResponseEntity<CustomerModel> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.status(200).body(customerServiceImpl.getCustomerByEmail(email));
    }

    @GetMapping("/customers/id/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(customerServiceImpl.getCustomerById(id));
    }

    @PostMapping("/add-customer")
    public ResponseEntity<CustomerModel> addCustomer(@RequestBody CustomerCreateModel customerCreateModel) {
        return ResponseEntity.status(200).body(customerServiceImpl.addCustomer(customerCreateModel));
    }

    @PatchMapping("/update-customer")
    public ResponseEntity<CustomerModel> updateCustomer(@RequestBody CustomerUpdateModel customerUpdateModel) {
        return ResponseEntity.status(200).body(customerServiceImpl.updateCustomer(customerUpdateModel));
    }

    @PostMapping("/delete-customer/{id}")
    public ResponseEntity<CustomerModel> deleteCustomer(@PathVariable Long id) {
        customerServiceImpl.deleteCustomer(id);
        return ResponseEntity.status(204).build();
    }
}
