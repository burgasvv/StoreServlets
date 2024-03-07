package com.burgas.storeservlets.transaction;

import com.burgas.storeservlets.dao.Dao;
import com.burgas.storeservlets.entity.Entity;
import com.burgas.storeservlets.exception.TransactionException;
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
            throw new TransactionException("Can't init transaction", e.getCause());
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
            throw new TransactionException("Can't commit changes", e.getCause());
        }
    }

    public void endTransaction() {

        try {
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new TransactionException("Can't end transaction", e.getCause());
        }
    }

    public void rollBack() {

        try {
            connection.rollback();

        } catch (SQLException e) {
            throw new TransactionException("Can't rollback changes", e.getCause());
        }
    }
}
