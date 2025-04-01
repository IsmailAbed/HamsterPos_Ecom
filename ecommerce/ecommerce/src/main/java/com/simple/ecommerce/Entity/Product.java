package com.simple.ecommerce.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Entity // marks the class as JPA entity  (maps to a db table)
@Getter
@Setter
@Table(name = "products") // table name
public class Product {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generate id
    private Long id;

    @Column(nullable = false) // should not be null
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne // Many products belong to one category
    @JoinColumn(name = "category_id", nullable = false) // Foreign key to category table
    private Category category;


    @Column(name = "sort_order")
    private Integer sortOrder;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
