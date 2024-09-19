package com.openclassrooms.mddapi.model.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entity of a user.
 */
@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String email;
    private String username;
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updateDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SUBSCRIPTION",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> subscriptions;
}
