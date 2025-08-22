package ai.qodo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TableCreator {
    private final JdbcTemplate jdbcTemplate;

    public TableCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        createOrdersTable();
        createStockTable();
        createTradeTable();
        jdbcTemplate.execute("""
        CREATE TABLE user_profile (
                id INT PRIMARY KEY AUTO_INCREMENT,
            username VARCHAR(50) NOT NULL,
            email VARCHAR(100) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
        """);
    }

    private void createOrdersTable() {
        jdbcTemplate.execute("""
            CREATE TABLE orders (
                id INT PRIMARY KEY AUTO_INCREMENT,
                order_number VARCHAR(50) NOT NULL,
                customer_id INT NOT NULL,
                order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """);
    }

    private void createStockTable() {
        jdbcTemplate.execute("""
            CREATE TABLE stock (
                id INT PRIMARY KEY AUTO_INCREMENT,
                symbol VARCHAR(10) NOT NULL,
                company_name VARCHAR(100) NOT NULL,
                current_price DECIMAL(10,2) NOT NULL,
                quantity INT NOT NULL,
                last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """);
    }

    private void createTradeTable() {
        jdbcTemplate.execute("""
            CREATE TABLE trade (
                id INT PRIMARY KEY AUTO_INCREMENT,
                symbol VARCHAR(10) NOT NULL,
                trade_type VARCHAR(4) NOT NULL,
                quantity INT NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                trade_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """);
    }
}