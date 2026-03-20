package com.ecom.catalog.service;

import org.springframework.stereotype.Service;

@Service
public class PricingService {
    public Double discountPrice(Double price, Integer discount) {
        if (price == null) return 0d;
        if (discount == null) return price;
        return price - (price * (discount / 100.0));
    }
}
