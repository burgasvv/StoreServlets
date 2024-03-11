package com.burgas.storeservlets.dao;

import com.burgas.storeservlets.entity.Entity;
import com.burgas.storeservlets.manager.DbManager;

import java.sql.Connection;
import java.util.List;

public abstract class Dao<T extends Entity> {

    private Connection connection = DbManager.createConnection();

    /**
     * Получить объект из таблицы
     * @param id идентификатор
     * @return оьъект, расширяющий класс Entity
     */
    public abstract T get(int id);

    /**
     * Получить все объекты из таблицы
     * @return список объектов, расширяющих класс Entity
     */
    public abstract List<T> getAll();

    /**
     * Добавить в таблицу значения
     * @param t объект класса, расширяющий класс Entity
     */
    public abstract void insert(T t);

    /**
     * Обновляет значения таблицы
     * @param t объект класса, расширяющий класс Entity
     * @param id идентификатор
     */
    public abstract void update(T t, int id);

    /**
     * Удаляет значения их таблицы
     * @param id идентификатор
     */
    public abstract void delete(int id);

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
