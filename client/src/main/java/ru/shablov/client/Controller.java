package ru.shablov.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.shablov.client.Requester.request;

@RestController
public class Controller {
    @RequestMapping("/create-user")
    static public String addUser() {
        return request("create-user");
    }

    @RequestMapping("/get-user-info")
    static public String getUserInfo(int id) {
        return request("get-user-info?id=" + id);
    }

    @RequestMapping("/add-money")
    static public String addMoney(int id, int amount) {
        return request("add-money?id=" + id + "&amount=" + amount);
    }

    @RequestMapping("/get-full-money")
    static public String getFullMoney(int id) {
        return request("get-full-money?id=" + id);
    }

    @RequestMapping("/show-stock")
    static public String showStock() {
        return request("show-stock");
    }

    @RequestMapping("/buy")
    static public String buyStocks(int id, int stockId, int amount) {
        return request("buy?id=" + id + "&stockId=" + stockId + "&amount=" + amount);
    }

    @RequestMapping("/sell")
    static public String sellStocks(int id, int stockId, int amount) {
        return request("sell?id=" + id + "&stockId=" + stockId + "&amount=" + amount);
    }

}