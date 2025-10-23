package ai.qodo.pojo;

import java.math.BigDecimal;

public class Stock {
    private int id;
    private String symbol;
    private String companyName;
    private BigDecimal currentPrice;
    private int quantity;

    public Stock() {
    }

    public Stock(int id, String symbol, String companyName, BigDecimal currentPrice, int quantity) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", currentPrice=" + currentPrice +
                ", quantity=" + quantity +
                '}';
    }
}