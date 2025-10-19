package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "Language")
public class Language {
    @Id
    @Column(name = "LanguageID")
    private String languageId;

    @Column(name = "Language")
    private String language;

    // Constructors
    public Language() {}
    public Language(String languageId, String language) {
        this.languageId = languageId;
        this.language = language;
    }

    // Getters và Setters
    public String getLanguageId() { return languageId; }
    public void setLanguageId(String languageId) { this.languageId = languageId; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    @Override
    public String toString() {
        return "Language{" + "id='" + languageId + '\'' + ", name='" + language + '\'' + '}';
    }
}