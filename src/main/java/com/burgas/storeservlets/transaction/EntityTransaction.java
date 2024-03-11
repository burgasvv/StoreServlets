package com.burgas.storeservlets.transaction;

import com.burgas.storeservlets.dao.Dao;
import com.burgas.storeservlets.entity.Entity;
import com.burgas.storeservlets.exception.TransactionException;
import com.burgas.storeservlets.manager.DbManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static java.util.Arrays.*;

/**
 * Сущность, которая описывает транзакционные методы для паттерна Dao,
 *      позволяющие управлять логикой сохранения и фиксации изменений в базе данных
 * connection - объект соединения
 */
public class EntityTransaction {

    private Connection connection;

    /**
     * Установка соединения со всеми объектами паттерна и отключение авто-фиксации изменений
     * @param daos объекты паттерна Dao
     */
    @SafeVarargs
    public final void initTransaction(Dao<? extends Entity>... daos) {

        try {
            connection = Objects.requireNonNull(
                    DbManager.createConnection()
            );
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            throw new TransactionException("Can't init transaction", e.getCause());
        }

        stream(daos).forEach(
                dao -> dao.setConnection(connection)
        );
    }

    /**
     * Установка соединения со всеми объектами паттерна
     * @param daos объекты паттерна Dao
     */
    @SafeVarargs
    public final void init(Dao<? extends Entity>... daos) {

        connection = Objects.requireNonNull(
                DbManager.createConnection()
        );

        stream(daos).forEach(
                dao -> dao.setConnection(connection)
        );
    }

    /**
     * Фиксация с момента последних изменений
     */
    public void commit() {
        try {
            connection.commit();

        } catch (SQLException e) {
            throw new TransactionException("Can't commit changes", e.getCause());
        }
    }

    /**
     * Включение авто-фиксации изменений
     */
    public void endTransaction() {

        try {
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new TransactionException("Can't end transaction", e.getCause());
        }
    }

    /**
     * Откат изменений, может потребоваться для обеспечении безопасности данных
     */
    public void rollBack() {

        try {
            connection.rollback();

        } catch (SQLException e) {
            throw new TransactionException("Can't rollback changes", e.getCause());
        }
    }
}
