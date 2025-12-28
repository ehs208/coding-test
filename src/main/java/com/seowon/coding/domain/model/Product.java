package com.seowon.coding.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    private String description;
    
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    private int stockQuantity;
    
    private String category;
    
    // Business logic
    public boolean isInStock() {
        return stockQuantity > 0;
    }
    
    public void decreaseStock(int quantity) {
        if (quantity > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        stockQuantity -= quantity;
    }
    
    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        stockQuantity += quantity;
    }

    public void updatePrice(double percentage, boolean includeTax) {
        double base = this.getPrice() == null ? 0.0 : this.getPrice().doubleValue();
                    double changed = base + (base * (percentage / 100.0)); // 부동소수점 오류 가능
                    if (includeTax) {
                        changed = changed * 1.1; // 하드코딩 VAT 10%, 지역/카테고리별 규칙 미반영
                    }
                    // 임의 반올림: 일관되지 않은 스케일/반올림 모드
                    BigDecimal newPrice = BigDecimal.valueOf(changed).setScale(2, RoundingMode.HALF_UP);
                    this.setPrice(newPrice);
    }

}
