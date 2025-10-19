package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "ProductCategoryTranslation")
public class ProductCategoryTranslation {
    @EmbeddedId
    private ProductCategoryId id; // Khóa tổng hợp

    @Column(name = "CategoryName")
    private String categoryName;

    // Mối quan hệ Many-to-One với ProductCategory
    @ManyToOne
    @MapsId("productCategoryId") // Ánh xạ trường productCategoryId trong ProductCategoryId
    @JoinColumn(name = "ProductCategoryID")
    private ProductCategory category;

    // Mối quan hệ Many-to-One với Language
    @ManyToOne
    @MapsId("languageId") // Ánh xạ trường languageId trong ProductCategoryId
    @JoinColumn(name = "LanguageID")
    private Language language;

    // Constructors
    public ProductCategoryTranslation() {}

    // Getters và Setters
    public ProductCategoryId getId() { return id; }
    public void setId(ProductCategoryId id) { this.id = id; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }
    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    @Override
    public String toString() {
        return "CategoryTranslation{id=" + id + ", name='" + categoryName + '\'' + '}';
    }
}