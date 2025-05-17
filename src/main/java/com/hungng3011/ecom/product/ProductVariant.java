package com.hungng3011.ecom.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductVariant {
    private @Getter @Setter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter Long price;
    private @Getter @Setter String currency = "VND";
    private @Getter @Setter List<String> images;
}
