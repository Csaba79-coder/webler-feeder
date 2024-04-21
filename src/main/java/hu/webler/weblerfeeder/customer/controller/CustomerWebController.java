package hu.webler.weblerfeeder.customer.controller;

import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.food.service.FoodService;
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
    private final FoodService foodService;

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("foods", foodService.getAllFoods());
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

    @GetMapping("/update-customer")
    public String updateCustomerOnWeb(@RequestParam Long customerId, Model model) {
        model.addAttribute("customers", customerService.getCustomerById(customerId));
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
        newCustomer.setAddress(Address.builder()
                .streetAndNumber(streetAndNumber)
                .city(city)
                .postalCode(postalCode)
                .build());
        customerService.addCustomer(newCustomer);
        return "redirect:/customers";
    }

    @PostMapping("/customers/customer/update")
    public String updateCustomerOnWeb(@RequestParam Long customerId, String firstName, String midName, String lastName,
                                      String streetAndNumber, String city, String postalCode, String cell, String email,
                                      LocalDate dateOfBirth, Status status) {
        CustomerUpdateModel customerUpdateModel = new CustomerUpdateModel();
        customerUpdateModel.setFirstName(firstName);
        customerUpdateModel.setMidName(midName);
        customerUpdateModel.setLastName(lastName);
        customerUpdateModel.setCell(cell);
        customerUpdateModel.setEmail(email);
        customerUpdateModel.setDateOfBirth(dateOfBirth);
        customerUpdateModel.setStatus(status);
        customerUpdateModel.setAddress(Address.builder()
                .streetAndNumber(streetAndNumber)
                .city(city)
                .postalCode(postalCode)
                .build());
        customerService.updateCustomer(customerId, customerUpdateModel);
        return "redirect:/customers";
    }

    @PostMapping("/customers/customer")
    public String deleteCustomerOnWeb(@RequestParam Long customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }
}
