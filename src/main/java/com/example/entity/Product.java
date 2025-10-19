package com.example.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "ProductID")
    private int productId;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Weight")
    private double weight;

    // ĐÃ SỬA: Tên biến từ 'category' thành 'productCategory'
    @ManyToOne
    @JoinColumn(name = "ProductCategoryID", nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductTranslation> translations;

    // Constructors
    public Product() {}
    public Product(int productId, BigDecimal price, double weight) {
        this.productId = productId;
        this.price = price;
        this.weight = weight;
    }

    // Getters và Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    // ĐÃ SỬA: Getters/Setters cho productCategory
    public ProductCategory getProductCategory() { return productCategory; }
    public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }

    public Set<ProductTranslation> getTranslations() { return translations; }
    public void setTranslations(Set<ProductTranslation> translations) { this.translations = translations; }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", price=" + price + ", weight=" + weight + '}';
    }
}