package hu.webler.weblerfeeder.service;

import hu.webler.weblerfeeder.entity.CustomerOrder;

import java.util.List;


public interface CustomerOrderService {

    List<CustomerOrder> getAllCustomerOrder();
    CustomerOrder saveCustomerOrder(CustomerOrder order);

    CustomerOrder deleteCustomerOrder(Long id);

    CustomerOrder findCustomerOrderById(Long id);

    CustomerOrder findCustomerOrderByAddress(String address);
}
