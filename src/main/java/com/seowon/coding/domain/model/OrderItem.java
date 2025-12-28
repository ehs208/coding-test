package com.seowon.coding.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal price; // Price at the time of order

    // Business logic
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static void createNewOrderItem(Order order, Product product,
            int qty) {

        checkQuantity(qty);

        OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(qty)
                .price(product.getPrice())
                .build();
        order.getItems().add(item);
        product.decreaseStock(qty);

    }

    private static void checkQuantity(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException(
                    "quantity must be positive: " + qty);
        }
    }
}
