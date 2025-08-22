package ai.qodo.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;


public class StockTest {


  // Create Stock object with valid constructor parameters
  @Test
  public void test_create_stock_with_valid_parameters() {
    Stock stock = new Stock(1, "AAPL", "Apple Inc", new BigDecimal("150.50"), 100);

    assertEquals(1, stock.getId());
    assertEquals("AAPL", stock.getSymbol());
    assertEquals("Apple Inc", stock.getCompanyName());
    assertEquals(new BigDecimal("150.50"), stock.getCurrentPrice());
    assertEquals(100, stock.getQuantity());
  }

  // Set negative id value
  @Test
  public void test_set_negative_id() {
    Stock stock = new Stock();
    stock.setId(-1);

    assertEquals(-1, stock.getId());
  }

  // Set null symbol string
  @Test
  public void test_set_null_symbol() {
    Stock stock = new Stock();
    stock.setSymbol(null);

    assertNull(stock.getSymbol());
  }
}