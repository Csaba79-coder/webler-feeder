package hu.webler.weblerfeeder.customer.controller;

import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.customer.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.webler.weblerfeeder.util.CustomerMapper.mapCustomerEntityToCustomerModel;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerModel>> listAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers()) ;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerModel> addCustomer(@RequestBody CustomerCreateModel customerCreateModel) {
        return ResponseEntity.status(200).body(customerService.addCustomer(customerCreateModel));
    }

    @GetMapping("/customers/customer/email/{email}")
    public ResponseEntity<CustomerModel> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.status(200).body(customerService.getCustomerByEmail(email));
    }

    @GetMapping("/customers/customer/id/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(mapCustomerEntityToCustomerModel(customerService.getCustomerById(id)));
    }

    @PatchMapping("/customers/customer/{id}")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateModel customerUpdateModel) {
        return ResponseEntity.status(200).body(customerService.updateCustomer(id, customerUpdateModel));
    }

    @DeleteMapping("/customers/customer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(204).build();
    }
}
