package org.arpha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.product.Dimension;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Embedded
    @Column(nullable = false)
    private Dimension dimension;
    @OneToMany(mappedBy = "type")
    private List<Product> products = new ArrayList<>();
}
