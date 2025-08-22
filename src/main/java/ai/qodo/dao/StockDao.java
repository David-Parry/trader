package ai.qodo.dao;

import ai.qodo.pojo.Stock;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StockDao {
  private final JdbcTemplate jdbcTemplate;

  public StockDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createStock(String symbol, String companyName, BigDecimal currentPrice, int quantity) {
    String sql = "INSERT INTO stock (symbol, company_name, current_price, quantity) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, symbol, companyName, currentPrice, quantity);
  }

  public void updateStock(int id, String symbol, String companyName, BigDecimal currentPrice, int quantity) {
    String sql = "UPDATE stock SET symbol = ?, company_name = ?, current_price = ?, quantity = ? WHERE id = ?";
    jdbcTemplate.update(sql, symbol, companyName, currentPrice, quantity, id);
  }

  public List<Stock> readStocks() {
    return jdbcTemplate.query("SELECT * FROM stock", new StockRowMapper());
  }

  private static class StockRowMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
      Stock stock = new Stock();
      stock.setId(rs.getInt("id"));
      stock.setSymbol(rs.getString("symbol"));
      stock.setCompanyName(rs.getString("company_name"));
      stock.setCurrentPrice(rs.getBigDecimal("current_price"));
      stock.setQuantity(rs.getInt("quantity"));
      return stock;
    }
  }
}
