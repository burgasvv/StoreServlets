package com.burgas.storeservlets;

import service.StoreService;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        StoreService service = new StoreService();
        out.println(service.getInfoByOrderNumber("A50"));
    }
}
