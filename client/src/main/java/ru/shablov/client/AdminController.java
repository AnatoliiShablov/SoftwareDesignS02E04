package ru.shablov.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ru.shablov.client.Requester.request;

@RestController
public class AdminController {
    @RequestMapping("/create-stocks")
    static public String createStocks(@RequestParam String companyName) {
        return request("create-stocks?companyName=" + companyName);
    }

    @RequestMapping("/add-stocks")
    static public String addStocks(int stockId, int amount) {
        return request("add-stocks?stockId=" + stockId + "&amount=" + amount);
    }

    @RequestMapping("/set-stocks-price")
    static public String setStocksPrice(int stockId, int price) {
        return request("set-stocks-price?stockId=" + stockId + "&price=" + price);
    }
}
