package com.example.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductTranslationId implements Serializable {
    private int productId;
    private String languageId;

    // Constructors
    public ProductTranslationId() {}
    public ProductTranslationId(int productId, String languageId) {
        this.productId = productId;
        this.languageId = languageId;
    }

    // Getters và Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getLanguageId() { return languageId; }
    public void setLanguageId(String languageId) { this.languageId = languageId; }

    // Cần phải ghi đè equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTranslationId that = (ProductTranslationId) o;
        return productId == that.productId && Objects.equals(languageId, that.languageId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(productId, languageId);
    }
}