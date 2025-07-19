package org.arpha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.arpha.dto.product.Dimension;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private long quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private ProductType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    // --- New & Updated Fields ---
    @Column(name = "min_player_number")
    private Integer minPlayerNumber;

    @Column(name = "max_player_number")
    private Integer maxPlayerNumber;

    @Column(name = "min_play_time")
    private Integer minPlayTime;

    @Column(name = "max_play_time")
    private Integer maxPlayTime;

    @Column(name = "min_age")
    private Integer minAge;

    private String language;
    private String publisher;
    private String author;

    @Column(name = "bgg_rating")
    private Double bggRating;

    private Double complexity;

    @Column(columnDefinition = "TEXT")
    private String components;

    private String rulesLink;
    @Embedded
    private Dimension dimension;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_mechanics", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "mechanic")
    private Set<String> mechanics = new HashSet<>();

    @CreatedBy
    @Column(nullable = false, name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(nullable = false, name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime updatedAt;

    private Double averageRating = 0.0;

    private Integer reviewCount = 0;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "product_genre", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ToString.Exclude
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "product_addons", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "addon_id"))
    @ToString.Exclude
    private Set<Product> addons = new HashSet<>();

    public void addQuantity(int quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be less than zero!");
        }
        this.quantity+=quantity;
    }
}
