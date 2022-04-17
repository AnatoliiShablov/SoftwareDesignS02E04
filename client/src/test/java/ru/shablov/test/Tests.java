package ru.shablov.test;

import org.junit.*;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import ru.shablov.client.AdminController;
import ru.shablov.client.Controller;

public class Tests {

    @ClassRule
    public static GenericContainer<?> simpleWebServer;

    @Before
    public void createConnection() {
        simpleWebServer = new FixedHostPortGenericContainer("server:1.0-SNAPSHOT").withFixedExposedPort(8083, 8083).withExposedPorts(8083);
        simpleWebServer.start();
    }

    public void initStocks() {
        Assert.assertEquals("Stocks{stock=Stock{id=1, companyName='First', price=0}, amount=0}", AdminController.createStocks("First"));
        Assert.assertEquals("Stocks{stock=Stock{id=2, companyName='First', price=0}, amount=0}", AdminController.createStocks("First"));
        Assert.assertEquals("Stocks{stock=Stock{id=3, companyName='Second', price=0}, amount=0}", AdminController.createStocks("Second"));

        Assert.assertEquals("Ok", AdminController.setStocksPrice(1, 100));
        Assert.assertEquals("Ok", AdminController.setStocksPrice(2, 200));
        Assert.assertEquals("Ok", AdminController.setStocksPrice(3, 300));

        Assert.assertEquals("Ok", AdminController.addStocks(1, 1));
        Assert.assertEquals("Ok", AdminController.addStocks(2, 2));


    }

    @After
    public void destroyConnection() {
        simpleWebServer.stop();
        simpleWebServer = null;
    }


    @Test
    public void fewUsersCreation() {
        Assert.assertEquals("User{id=1, money=0, stocks={}}", Controller.addUser());
        Assert.assertEquals("User{id=2, money=0, stocks={}}", Controller.addUser());
        Assert.assertEquals("User{id=3, money=0, stocks={}}", Controller.addUser());
    }

    @Test
    public void userMoneyAdd() {
        Controller.addUser();
        Controller.addUser();
        Controller.addMoney(1, 100);
        Controller.addMoney(1, 50);
        Controller.addMoney(2, 200);
        Assert.assertEquals("User{id=1, money=150, stocks={}}", Controller.getUserInfo(1));
        Assert.assertEquals("User{id=2, money=200, stocks={}}", Controller.getUserInfo(2));
        Assert.assertEquals("No such user", Controller.getUserInfo(3));
    }

    @Test
    public void buyWithoutMoney() {
        initStocks();
        Controller.addUser();
        Assert.assertEquals("Unable to buy such amount", Controller.buyStocks(1, 1, 1));
    }

    @Test
    public void buyWithoutStock() {
        initStocks();
        Controller.addUser();
        Assert.assertEquals("No such stock", Controller.buyStocks(1, 10, 1));
    }

    @Test
    public void buyWithMoney() {
        initStocks();
        Controller.addUser();
        Controller.addMoney(1, 500);
        Assert.assertEquals("Ok", Controller.buyStocks(1, 1, 1));
        Assert.assertEquals("Ok", Controller.buyStocks(1, 2, 1));
        Assert.assertEquals("User{id=1, money=200, stocks={1=Stocks{stock=Stock{id=1, companyName='First', price=100}, amount=1}, 2=Stocks{stock=Stock{id=2, companyName='First', price=200}, amount=1}}}", Controller.getUserInfo(1));

    }

    @Test
    public void getMoneyAfterChangingPrice() {
        initStocks();
        Controller.addUser();
        Controller.addMoney(1, 500);
        Controller.buyStocks(1, 1, 1);
        Controller.buyStocks(1, 2, 1);
        Assert.assertEquals("500", Controller.getFullMoney(1));

        AdminController.setStocksPrice(2, 1000);
        Assert.assertEquals("1300", Controller.getFullMoney(1));
    }

    @Test
    public void sellTest() {
        initStocks();
        Controller.addUser();
        Controller.addMoney(1, 500);
        Controller.buyStocks(1, 1, 1);
        Controller.buyStocks(1, 2, 1);
        Assert.assertEquals("500", Controller.getFullMoney(1));

        AdminController.setStocksPrice(2, 1000);

        Assert.assertEquals("User{id=1, money=200, stocks={1=Stocks{stock=Stock{id=1, companyName='First', price=100}, amount=1}, 2=Stocks{stock=Stock{id=2, companyName='First', price=1000}, amount=1}}}", Controller.getUserInfo(1));
        Assert.assertEquals("Ok", Controller.sellStocks(1, 2, 1));
        Assert.assertEquals("User{id=1, money=1200, stocks={1=Stocks{stock=Stock{id=1, companyName='First', price=100}, amount=1}}}", Controller.getUserInfo(1));
    }
}