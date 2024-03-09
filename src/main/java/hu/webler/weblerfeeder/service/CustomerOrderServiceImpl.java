package hu.webler.weblerfeeder.service;

import hu.webler.weblerfeeder.entity.CustomerOrder;
import hu.webler.weblerfeeder.repository.CustomerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService{

    private final CustomerOrderRepository customerOrderRepository;

    @Override
    public List<CustomerOrder> getAllCustomerOrder() {
        return customerOrderRepository.findAll();
    }

    @Override
    public CustomerOrder saveCustomerOrder(CustomerOrder order) {
        return null;
    }

    @Override
    public CustomerOrder deleteCustomerOrder(Long id) {
        return null;
    }

    @Override
    public CustomerOrder findCustomerOrderById(Long id) {
        return null;
    }

    @Override
    public CustomerOrder findCustomerOrderByAddress(String address) {
        return null;
    }

    @Override
    public CustomerOrder updateCustomerOrder(long id, CustomerOrder customerOrder) {
        return null;
    }
}
