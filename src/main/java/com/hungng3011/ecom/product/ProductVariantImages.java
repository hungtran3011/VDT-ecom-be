package com.hungng3011.ecom.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "product_variant_images")
public class ProductVariantImages {
    @Id
    @SequenceGenerator(
            name = "product_variant_images_sequence",
            sequenceName = "product_variant_images_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private @Getter @Setter Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    @JsonBackReference
    private @Getter @Setter ProductVariant productVariant;

    // Store image URL/path
    private @Getter @Setter String imageUrl;
}
