package com.simple.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_table")  // Rename to something like "order_table"
public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne // many order to one user
        @JoinColumn(name = "user_id", nullable = false) // foreign key column for the user relationship
        private User user;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        //one order can have many items
        // Cascade ALL: saving/deleting an order also saves/deletes its items

        @JsonManagedReference // (prevents infinite loop)
        private List<OrderItem> items;

        private Double totalAmount;
        private String status = "PENDING"; // Default status

        // Getters and setters..


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
