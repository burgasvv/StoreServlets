package com.burgas.storeservlets.entity;

/**
 * Сущность, которая описывает базовую информацию во всех таблицах
 * id - идентификатор
 */
public class Entity {

    private int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
