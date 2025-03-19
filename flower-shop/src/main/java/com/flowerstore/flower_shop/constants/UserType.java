package com.flowerstore.flower_shop.constants;

import java.util.List;

public enum UserType {
    CLIENT,
    ADMIN,
    LIVRATOR;

    private static final List<UserType> VALUES = List.of(values());  //converteste array-ul intr o lista imutabila care contina toate valorile enum-ului
    //o facem private static final(imutabila) pt ca lista nu trebuie sa fie modificata
}