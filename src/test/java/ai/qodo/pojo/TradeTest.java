package ai.qodo.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;


public class TradeTest {


  // Create trade with all valid parameters and verify field values
  @Test
  public void test_create_trade_with_valid_parameters() {
    int id = 1;
    String symbol = "AAPL";
    String tradeType = "BUY";
    int quantity = 100;
    BigDecimal price = new BigDecimal("150.50");
    Timestamp tradeDate = new Timestamp(System.currentTimeMillis());

    Trade trade = new Trade(id, symbol, tradeType, quantity, price, tradeDate);

    assertEquals(id, trade.getId());
    assertEquals(symbol, trade.getSymbol());
    assertEquals(tradeType, trade.getTradeType());
    assertEquals(quantity, trade.getQuantity());
    assertEquals(price, trade.getPrice());
    assertEquals(tradeDate, trade.getTradeDate());
  }

  // Set null values for nullable fields
  @Test
  public void test_trade_with_null_values() {
    int id = 1;
    int quantity = 100;

    Trade trade = new Trade(id, null, null, quantity, null, null);

    assertEquals(id, trade.getId());
    assertNull(trade.getSymbol());
    assertNull(trade.getTradeType());
    assertEquals(quantity, trade.getQuantity());
    assertNull(trade.getPrice());
    assertNull(trade.getTradeDate());
  }

  // Set zero and negative values for quantity
  @Test
  public void test_trade_with_zero_and_negative_quantity() {
    Trade tradeZero =
        new Trade(1, "AAPL", "SELL", 0, new BigDecimal("100.00"), new Timestamp(System.currentTimeMillis()));
    Trade tradeNegative =
        new Trade(2, "AAPL", "SELL", -50, new BigDecimal("100.00"), new Timestamp(System.currentTimeMillis()));

    assertEquals(0, tradeZero.getQuantity());
    assertEquals(-50, tradeNegative.getQuantity());
  }
}