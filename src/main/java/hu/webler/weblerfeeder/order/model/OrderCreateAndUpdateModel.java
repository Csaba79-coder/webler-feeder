package hu.webler.weblerfeeder.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateAndUpdateModel {

    private Long id;
    private LocalDateTime createdAt;
    private String address;
    private String description;
}
