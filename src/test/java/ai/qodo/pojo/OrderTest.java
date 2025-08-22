package ai.qodo.pojo;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void testOrderConstructor() {
        // Arrange
        int id = 1;
        int userId = 2;
        String orderNumber = "abc-324";
        LocalDateTime createdAt = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(createdAt);
        // Act
        Order order = new Order(id, orderNumber, userId, timestamp);

        // Assert
        assertEquals(id, order.getId());
        assertEquals(userId, order.getCustomerId());
        assertEquals(orderNumber, order.getOrderNumber());
        assertEquals(timestamp, order.getOrderDate());
    }

}
