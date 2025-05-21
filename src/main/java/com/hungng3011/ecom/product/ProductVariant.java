package com.hungng3011.ecom.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Entity
@Table
public class ProductVariant {
    @Id
    @SequenceGenerator(
            name = "product_variant_sequence",
            sequenceName = "product_variant_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE) // Hide setter for id
    @Getter // Hide getter for id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    public void setProduct(Product product) {
        this.product = product;
    }

    private @Getter @Setter String name;
    private @Getter @Setter Long price;
    private @Getter @Setter String currency = "VND";

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private @Getter @Setter List<ProductVariantImages> images;

}

