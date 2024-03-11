package com.burgas.storeservlets.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Утилитарная сущность-одиночка, которая устанавливает соединение с базой данных
 * PROPERTIES - объект, с помощью которого считываются параметры подключения к базе данных
 * URL - строка, которая хранит в себе адрес базы данных
 */
public class DbManager {

    private static final Properties PROPERTIES = new Properties();
    private static final String URL;

    static {
        try (InputStream resourceAsStream = DbManager.class
                .getClassLoader().getResourceAsStream("db.properties")){
            PROPERTIES.load(resourceAsStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        URL = PROPERTIES.getProperty("url");
    }


    private DbManager() {
    }

    /**
     * Получение драйвера и создание соединения с базой данных
     * @return подключение к базе данных
     */
    public static Connection createConnection() {
        try {
            Class.forName(PROPERTIES.getProperty("driver"));
            return DriverManager.getConnection(URL, PROPERTIES);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
