package hu.webler.weblerfeeder.customer.controller;

import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.value.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class CustomerWebController {

    private final CustomerService customerService;

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping("/customers")
    public String renderAllCustomersOnWeb(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/customers/search")
    public String renderCustomersByEmailOnWeb(@RequestParam String email, Model model) {
        if (email != null && !email.isEmpty()) {
            model.addAttribute("customers", customerService.getCustomerByEmail(email));
        }
        return "customers";
    }

    @GetMapping("/add-customer")
    public String addCustomerOnWeb() {
        return "add-customer";
    }

    @GetMapping("/update-customer/{id}")
    public String updateCustomerOnWeb(@PathVariable Long id) {
        return "update-customer";
    }

    @PostMapping("/customers/customer/add")
    public String addCustomerOnWeb(@RequestParam String firstName, String midName, String lastName, String streetAndNumber,
                                   String city, String postalCode, String cell, String email, LocalDate dateOfBirth) {
        CustomerCreateModel newCustomer = new CustomerCreateModel();
        newCustomer.setFirstName(firstName);
        newCustomer.setMidName(midName);
        newCustomer.setLastName(lastName);
        newCustomer.setCell(cell);
        newCustomer.setEmail(email);
        newCustomer.setDateOfBirth(dateOfBirth);
        newCustomer.getAddress().setStreetAndNumber(streetAndNumber);
        newCustomer.getAddress().setCity(city);
        newCustomer.getAddress().setPostalCode(postalCode);
        customerService.addCustomer(newCustomer);
        return "redirect:/customers";
    }

    @PostMapping("/customers/customer/update/")
    public String updateCustomerOnWeb(@RequestParam Long customerId, String firstName, String midName, String lastName,
                                      String streetAndNumber, String city, String postalCode, String cell, String email,
                                      LocalDate dateOfBirth, Status status) {
        CustomerUpdateModel updateModelCustomer = new CustomerUpdateModel();
        updateModelCustomer.setFirstName(firstName);
        updateModelCustomer.setMidName(midName);
        updateModelCustomer.setLastName(lastName);
        updateModelCustomer.setCell(cell);
        updateModelCustomer.setEmail(email);
        updateModelCustomer.setDateOfBirth(dateOfBirth);
        updateModelCustomer.setStatus(status);
        updateModelCustomer.getAddress().setStreetAndNumber(streetAndNumber);
        updateModelCustomer.getAddress().setCity(city);
        updateModelCustomer.getAddress().setPostalCode(postalCode);
        customerService.updateCustomer(customerId, updateModelCustomer);
        return "redirect:/customers";
    }

    @PostMapping("/customers/customer/")
    public String deleteCustomerOnWeb(@RequestParam Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
        }
}
