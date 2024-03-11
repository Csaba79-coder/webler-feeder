package hu.webler.weblerfeeder.service;

import hu.webler.weblerfeeder.model.CustomerCreateModel;
import hu.webler.weblerfeeder.model.CustomerModel;
import hu.webler.weblerfeeder.model.CustomerUpdateModel;

import java.util.List;

public interface CustomerService {

    List<CustomerModel> getAllCustomers();
    CustomerModel addCustomer(CustomerCreateModel customerCreateModel);
    CustomerModel updateCustomer(CustomerUpdateModel customerUpdateModel);
    void deleteCustomer(Long id);
    CustomerModel getCustomerById(Long id);
    CustomerModel getCustomerByEmail(String email);
}
