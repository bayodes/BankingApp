package com.revature.controllers;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class AccountController {

    private AccountService aServ;
    private ObjectMapper oMap;
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userType;

    private User updatedUser;

    public AccountController() {}

    public AccountController(AccountService aServ) {
        this.aServ = aServ;
        this.oMap = new ObjectMapper();
    }

    public Handler handleCreateAccount = (ctx) -> {
        boolean hasAccount = true;

        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to create an account");
        } else {
            userID = Integer.parseInt((String) ctx.req.getSession().getAttribute("id"));
            firstName = (String) ctx.req.getSession().getAttribute("fName");
            lastName = (String) ctx.req.getSession().getAttribute("lName");
            email = (String) ctx.req.getSession().getAttribute("email");
            password = (String) ctx.req.getSession().getAttribute("password");
            userType = (String) ctx.req.getSession().getAttribute("userType");

            if(userType.equalsIgnoreCase("manager")) {
                ctx.result("Managers can't create accounts");
            } else if (userType.equalsIgnoreCase("user")) {
                User u = new User(userID, firstName, lastName, email, password, userType);

                updatedUser = aServ.addAccount(u);

                ctx.result("Waiting to see if your account is approved");
//                ctx.result(email + " added a new bank account");
//                ctx.req.getSession().setAttribute("hasAccount", ""+hasAccount);
            }
        }
    };

    public Handler handleDeposit = (ctx) -> {
        Account a = oMap.readValue(ctx.body(), Account.class);

        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to deposit into an account");
        } else if (ctx.req.getSession().getAttribute("hasAccount") == null) {
            ctx.result("You must create an account before making a deposit");
        } else if (ctx.req.getSession().getAttribute("hasAccount") != null) {
            aServ.deposit(a);
            ctx.result(a.getBalance() + " added");
        }
    };

    public Handler handleWithdraw = (ctx) -> {
        Account a = oMap.readValue(ctx.body(), Account.class);

        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to withdraw into an account");
        } else if (ctx.req.getSession().getAttribute("hasAccount") == null) {
            ctx.result("You must create an account before making a withdraw");
        } else if (ctx.req.getSession().getAttribute("hasAccount") != null) {
            aServ.withdraw(a);
            ctx.result(a.getBalance() + " added");
        }
    };

    public Handler viewAllAccounts = (ctx) -> {

        System.out.println(updatedUser.getListOfAccounts().peek());

        ctx.result(oMap.writeValueAsString(updatedUser.getListOfAccounts().peek()));
    };

    public Handler approveAccount = (ctx) -> {
        aServ.approveAccount(updatedUser);
    };

    public Handler denyAccount = (ctx) -> {
        ctx.result(oMap.writeValueAsString(updatedUser.getListOfAccounts().peek()));
        ctx.result("Account was denied");
    };
}
