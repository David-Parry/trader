package ai.qodo;

import ai.qodo.dao.*;
import ai.qodo.pojo.Order;
import ai.qodo.pojo.Stock;
import ai.qodo.pojo.Trade;
import ai.qodo.pojo.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class DataAccessApplication {

    @Autowired
    private TableCreator tableCreator;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private TradeDao tradeDao;

    @Autowired
    private UserProfileDao userProfileDao;

    public static void main(String[] args) {
        SpringApplication.run(DataAccessApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> init() {
        return event -> {
            // Initialize tables
            tableCreator.init();

            userProfileDao.createUserProfile("John", "d@eami.com");
            List<UserProfile> profileDaoList = userProfileDao.readUserProfiles();
            System.out.println(profileDaoList.get(0));
            userProfileDao.updateUserProfile(profileDaoList.get(0).getId(), "Found", "jacko@micked.com");
            profileDaoList = userProfileDao.readUserProfiles();

            UserProfile profileOne = profileDaoList.get(0);

            // Test Orders
            orderDao.createOrder("ORD001", 1);
            List<Order> orders = orderDao.readOrders();
            System.out.println("Created Order: " + orders.get(0));
            orderDao.updateOrder(orders.get(0).getId(), "ORD001-UPDATE", 1);
            orders = orderDao.readOrders();
            Order theOrder = orders.get(0);


            // Test Stocks
            stockDao.createStock("AAPL", "Apple Inc", new BigDecimal("150.00"), 100);
            List<Stock> stocks = stockDao.readStocks();
            System.out.println("Created Stock: " + stocks.get(0));
            stockDao.updateStock(stocks.get(0).getId(), "AAPL", "Apple Inc", new BigDecimal("155.00"), 120);
            stocks = stockDao.readStocks();
            System.out.println("Updated Stock: " + stocks.get(0));

            // Test Trades
            tradeDao.createTrade("AAPL", "BUY", 50, new BigDecimal("150.00"));
            List<Trade> trades = tradeDao.readTrades();
            System.out.println("Created Trade: " + trades.get(0));
            tradeDao.updateTrade(trades.get(0).getId(), "AAPL", "SELL", 25, new BigDecimal("155.00"));
            trades = tradeDao.readTrades();
            System.out.println("Updated Trade: " + trades.get(0));


        };
    }
}
