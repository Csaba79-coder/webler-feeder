package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.order.entity.Order;
import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class OrderMapper {

    public static OrderModel mapOrderEntityToOrderModel(Order order) {
        return OrderModel.
                builder().id(order.getId())
                .createdAt(order.getCreatedAt())
                .address(order.getAddress())
                .description(order.getDescription())
                .customer(order.getCustomer())
                .build()
                ;
    }

    public static Order mapOrderCreateAndUpdateModelToOrderEntity
            (OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return Order.builder()
                .address(orderCreateAndUpdateModel.getAddress())
                .description(orderCreateAndUpdateModel.getDescription())
                .customer(orderCreateAndUpdateModel.getCustomer())
                .build()
                ;
    }

    public OrderMapper() {
    }
}
