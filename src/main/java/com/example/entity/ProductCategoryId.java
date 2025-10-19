package com.example.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductCategoryId implements Serializable {
    private int productCategoryId;
    private String languageId;

    // Constructors
    public ProductCategoryId() {}
    public ProductCategoryId(int productCategoryId, String languageId) {
        this.productCategoryId = productCategoryId;
        this.languageId = languageId;
    }

    // Getters và Setters
    public int getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(int productCategoryId) { this.productCategoryId = productCategoryId; }
    public String getLanguageId() { return languageId; }
    public void setLanguageId(String languageId) { this.languageId = languageId; }

    // Cần phải ghi đè equals() và hashCode() cho khóa tổng hợp
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return productCategoryId == that.productCategoryId && Objects.equals(languageId, that.languageId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(productCategoryId, languageId);
    }
}