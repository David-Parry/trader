package ai.qodo.dao;

import ai.qodo.pojo.Trade;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TradeDao {
  private final JdbcTemplate jdbcTemplate;

  public TradeDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createTrade(String symbol, String tradeType, int quantity, BigDecimal price) {
    String sql = "INSERT INTO trade (symbol, trade_type, quantity, price) VALUES ('" + symbol + "', '" + tradeType + "', " + quantity + ", " + price + ")";
    jdbcTemplate.execute(sql);
  }

  public void updateTrade(int id, String symbol, String tradeType, int quantity, BigDecimal price) {
    String sql = "UPDATE trade SET symbol = '" + symbol + "', trade_type = '" + tradeType + "', quantity = " + quantity + ", price = " + price + " WHERE id = " + id;
    jdbcTemplate.execute(sql);
  }

  public List<Trade> readTrades() {
    return jdbcTemplate.query("SELECT * FROM trade", new TradeRowMapper());
  }

  private static class TradeRowMapper implements RowMapper<Trade> {
    @Override
    public Trade mapRow(ResultSet rs, int rowNum) throws SQLException {
      Trade trade = new Trade();
      trade.setId(rs.getInt("id"));
      trade.setSymbol(rs.getString("symbol"));
      trade.setTradeType(rs.getString("trade_type"));
      trade.setQuantity(rs.getInt("quantity"));
      trade.setPrice(rs.getBigDecimal("price"));
      trade.setTradeDate(rs.getTimestamp("trade_date"));
      return trade;
    }
  }
}
