package org.test.teamproject_back.dto.request;

import lombok.Data;
import org.test.teamproject_back.entity.*;

import java.util.List;

@Data
public class ReqOrderDto {
    private String customerId;
    private String fullName;
    private Long totalAmount;
    private List<OrderItem> products;
    private String payMethod;
    private String paymentId;
    private int orderItemCount;

    public Order toOrder() {
        return Order.builder()
                .userId(Long.parseLong(customerId))
                .totalAmount(totalAmount)
                .build();
    }

    public Payment toPayment(Long orderId) {
        return Payment.builder()
                .orderId(orderId)
                .paymentMethod(payMethod)
                .paymentStatus("completed")
                .paymentNum(paymentId)
                .amount(totalAmount)
                .build();
    }
}