package org.arpha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "player_number", nullable = false)
    private int playerNumber;
    @Column(name = "play_time", nullable = false)
    private int playTime;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "rules_link")
    private String rulesLink;
    @Embedded
    private Dimension dimension;
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

    public void addQuantity(int quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be less than zero!");
        }
        this.quantity+=quantity;
    }
}
