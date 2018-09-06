package com.zen.lab.services.program.model;

public enum Provider {
    BETRADAR(20),
    BETGENIUS(70),
    TIPICO(10);


    private final int id;

    Provider(int id) {
        this.id = id;
    }
}
