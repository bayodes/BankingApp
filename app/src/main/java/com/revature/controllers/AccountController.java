package com.revature.controllers;

import com.revature.models.Account;
import com.revature.models.DepositObject;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class AccountController {

    private AccountService aServ;
    private ObjectMapper oMap;

    public AccountController() {}

    public AccountController(AccountService aServ) {
        this.aServ = aServ;
        this.oMap = new ObjectMapper();
    }

    public Handler handleCreateAccount = (ctx) -> {
        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to create an account");
        } else {
            int userID = Integer.parseInt((String) ctx.req.getSession().getAttribute("id"));
            String firstName = (String) ctx.req.getSession().getAttribute("fName");
            String lastName = (String) ctx.req.getSession().getAttribute("lName");
            String email = (String) ctx.req.getSession().getAttribute("email");
            String password = (String) ctx.req.getSession().getAttribute("password");

            User u = new User(userID, firstName, lastName, email, password);

            aServ.addAccount(u);

            ctx.status(401);
            ctx.result(email + " added a new bank account");
        }
    };

    public Handler handleDeposit = (ctx) -> {
        Account a = oMap.readValue(ctx.body(), Account.class);

        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to deposit into an account");
        } else if (a == null) {
            // doesn't work - you don't really need to create an account before making deposit
            ctx.result("You must create an account before making a deposit");
        } else {
            aServ.deposit(a);
            ctx.result(a.getBalance() + " added");
        }
    };

    public Handler handleWithdraw = (ctx) -> {
        Account a = oMap.readValue(ctx.body(), Account.class);

        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to withdraw from your account");
        } else if (a == null) {
            // doesn't work - you don't really need to create an account before making deposit
            ctx.result("You must create an account before making a deposit");
        } else {
            aServ.withdraw(a);
            ctx.result(a.getBalance() + " added");
        }
    };
}
