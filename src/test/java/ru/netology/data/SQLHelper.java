package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import lombok.SneakyThrows;
import lombok.val;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void clearPaymentTable() {
        val deletePaymentEntity = "DELETE FROM payment_entity ";
        try (val conn = getConn()) {
            runner.update(conn, deletePaymentEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        val statusSQL = "SELECT status FROM payment_entity LIMIT 1";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    private static String getStatus(String query) {
        val runner = new QueryRunner();
        try (val conn = getConn()) {
            String status = runner.query(conn, query, new ScalarHandler<String>());
            return status;
        }
    }
}