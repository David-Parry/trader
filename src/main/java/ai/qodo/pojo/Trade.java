package ai.qodo.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Trade {
    private int id;
    private String symbol;
    private String tradeType;
    private int quantity;
    private BigDecimal price;
    private Timestamp tradeDate;

    public Trade() {
    }

    public Trade(int id, String symbol, String tradeType, int quantity, BigDecimal price, Timestamp tradeDate) {
        this.id = id;
        this.symbol = symbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.tradeDate = tradeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", tradeDate=" + tradeDate +
                '}';
    }
}