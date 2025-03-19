package com.flowerstore.flower_shop.model;

import jdk.jfr.Category;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Product { //intreb daca mai adaug atribute sau fac clasa care mosteneste product?
    private Long id;
    private String name;
    private double price;
    private String description;
    private String image;
   // private Category category; //relatie cu modelul Category
   private String category; //pt moment
    private int stock;
}
