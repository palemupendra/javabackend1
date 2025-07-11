package com.crmapp.entity;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String company;

    
    // Getters and setters (required for @RequestBody to bind properly)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

    // Getters and setters

// {
//   "customer": {
//     "name": "Alice Johnson",
//     "email": "alice@example.com",
//     "phone": "9876543210",
//     "company": "TechCorp Inc."
//   },
//   "status": "New",
//   "notes": "Interested in premium plan"
// }
