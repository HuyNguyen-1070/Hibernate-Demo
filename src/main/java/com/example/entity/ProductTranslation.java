package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "ProductTranslation")
public class ProductTranslation {
    @EmbeddedId
    private ProductTranslationId id; // Khóa tổng hợp

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "ProductDescription")
    private String productDescription;

    // Mối quan hệ Many-to-One với Product
    @ManyToOne
    @MapsId("productId") // Ánh xạ trường productId trong ProductTranslationId
    @JoinColumn(name = "ProductID")
    private Product product;

    // Mối quan hệ Many-to-One với Language
    @ManyToOne
    @MapsId("languageId") // Ánh xạ trường languageId trong ProductTranslationId
    @JoinColumn(name = "LanguageID")
    private Language language;

    // Constructors
    public ProductTranslation() {}

    // Getters và Setters
    public ProductTranslationId getId() { return id; }
    public void setId(ProductTranslationId id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    @Override
    public String toString() {
        return "ProductTranslation{id=" + id + ", name='" + productName + '\'' + '}';
    }
}