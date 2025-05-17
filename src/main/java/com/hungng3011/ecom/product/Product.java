package com.hungng3011.ecom.product;
import lombok.*;

import java.util.List;

@ToString
public class Product {
    private @Getter @Setter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String description;
    private @Getter @Setter List<ProductVariant> variants;
}
