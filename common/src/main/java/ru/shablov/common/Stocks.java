package ru.shablov.common;

import java.util.Objects;

public class Stocks {
    public final Stock stock;
    public int amount = 0;

    public Stocks(Stock stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stocks stocks = (Stocks) o;
        return Objects.equals(stock, stocks.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock);
    }

    @Override
    public String toString() {
        return "Stocks{" + "stock=" + stock + ", amount=" + amount + '}';
    }
}