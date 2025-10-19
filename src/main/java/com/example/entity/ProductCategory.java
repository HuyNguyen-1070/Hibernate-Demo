package com.example.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ProductCategory")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Giả định là ID tự tăng
    @Column(name = "ProductCategoryID")
    private int productCategoryId;

    @Column(name = "CanBeShipped")
    private boolean canBeShipped;

    // Mối quan hệ One-to-Many với Product
    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    // Mối quan hệ One-to-Many với bản dịch (không đổi)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductCategoryTranslation> translations;

    //TRANSIENT FIELD ĐỂ LƯU TÊN DANH MỤC THEO NGÔN NGỮ HIỆN TẠI
    @Transient
    private String currentLanguageName;

    // Constructors
    public ProductCategory() {}
    public ProductCategory(int productCategoryId, boolean canBeShipped) {
        this.productCategoryId = productCategoryId;
        this.canBeShipped = canBeShipped;
    }

    // Getters và Setters (Không đổi)
    public int getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(int productCategoryId) { this.productCategoryId = productCategoryId; }
    public boolean isCanBeShipped() { return canBeShipped; }
    public void setCanBeShipped(boolean canBeShipped) { this.canBeShipped = canBeShipped; }
    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }
    public Set<ProductCategoryTranslation> getTranslations() { return translations; }
    public void setTranslations(Set<ProductCategoryTranslation> translations) { this.translations = translations; }
    public String getCurrentLanguageName() { return currentLanguageName; }
    public void setCurrentLanguageName(String currentLanguageName) { this.currentLanguageName = currentLanguageName; }
    @Override
    public String toString() {
        return "ProductCategory{id=" + productCategoryId + ", canBeShipped=" + canBeShipped + '}';
    }
}