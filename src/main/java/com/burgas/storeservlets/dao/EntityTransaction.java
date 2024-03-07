package com.burgas.storeservlets.dao;

import com.burgas.storeservlets.entity.Entity;
import com.burgas.storeservlets.manager.DbManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static java.util.Arrays.*;

public class EntityTransaction {

    private Connection connection;

    @SafeVarargs
    public final void initTransaction(Dao<? extends Entity>... daos) {

        try {
            connection = Objects.requireNonNull(
                    DbManager.createConnection()
            );
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        stream(daos).forEach(
                dao -> dao.setConnection(connection)
        );
    }

    @SafeVarargs
    public final void init(Dao<? extends Entity>... daos) {

        connection = Objects.requireNonNull(
                DbManager.createConnection()
        );

        stream(daos).forEach(
                dao -> dao.setConnection(connection)
        );
    }

    public void commit() {
        try {
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void endTransaction() {

        try {
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollBack() {

        try {
            connection.rollback();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
