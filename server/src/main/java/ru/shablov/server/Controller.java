package ru.shablov.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shablov.common.Stock;
import ru.shablov.common.Stocks;
import ru.shablov.common.User;

import java.util.SortedMap;
import java.util.TreeMap;


@RestController
public class Controller {
    private final SortedMap<Integer, User> users = new TreeMap<>();
    private final SortedMap<Integer, Stocks> stocks = new TreeMap<>();
    private int nextUserId = 1;
    private int nextStockId = 1;

    @RequestMapping("/create-user")
    public String addUser() {
        users.put(nextUserId, new User(nextUserId));
        return users.get(nextUserId++).toString();
    }

    @RequestMapping("/get-user-info")
    public String getUserInfo(int id) {
        if (users.containsKey(id)) {
            return users.get(id).toString();
        } else {
            return "No such user";
        }
    }

    @RequestMapping("/add-money")
    public String addMoney(int id, int amount) {
        if (!users.containsKey(id)) {
            return "No such user";
        }
        users.get(id).money += amount;
        return "Ok";
    }

    @RequestMapping("/get-full-money")
    public String getFullMoney(int id) {
        if (!users.containsKey(id)) {
            return "No such user";
        }
        return Integer.toString(users.get(id).money + users.get(id).stocks.values().stream().mapToInt(s -> s.stock.price * s.amount).sum());
    }

    @RequestMapping("/show-stock")
    public String showStock() {
        return stocks.toString();
    }

    @RequestMapping("/buy")
    public String buyStocks(int id, int stockId, int amount) {
        if (!users.containsKey(id)) {
            return "No such user";
        }

        if (!stocks.containsKey(stockId)) {
            return "No such stock";
        }

        if (amount <= 0 || stocks.get(stockId).amount < amount || users.get(id).money < amount * stocks.get(stockId).stock.price) {
            return "Unable to buy such amount";
        }
        users.get(id).stocks.putIfAbsent(stockId, new Stocks(stocks.get(stockId).stock));

        users.get(id).money -= amount * stocks.get(stockId).stock.price;
        users.get(id).stocks.get(stockId).amount += amount;

        return "Ok";
    }

    @RequestMapping("/sell")
    public String sellStocks(int id, int stockId, int amount) {
        if (!users.containsKey(id) || !users.get(id).stocks.containsKey(stockId)) {
            return "Unable to sell stocks (no such user or stock)";
        }

        if (amount <= 0 || users.get(id).stocks.get(stockId).amount < amount) {
            return "Unable to sell such amount";
        }

        users.get(id).money += amount * stocks.get(stockId).stock.price;

        if (users.get(id).stocks.get(stockId).amount == amount) {
            users.get(id).stocks.remove(stockId);
        } else {
            users.get(id).stocks.get(stockId).amount -= amount;
        }

        return "Ok";
    }

    @RequestMapping("/create-stocks")
    public String createStocks(@RequestParam String companyName) {
        stocks.put(nextStockId, new Stocks(new Stock(nextStockId, companyName)));

        return stocks.get(nextStockId++).toString();
    }

    @RequestMapping("/add-stocks")
    public String addStocks(int stockId, int amount) {
        if (!stocks.containsKey(stockId)) {
            return "No such stock";
        }

        stocks.get(stockId).amount += amount;

        return "Ok";
    }

    @RequestMapping("/set-stocks-price")
    public String setStocksPrice(int stockId, int price) {
        if (!stocks.containsKey(stockId)) {
            return "No such stock";
        }

        stocks.get(stockId).stock.price = price;

        return "Ok";
    }
}