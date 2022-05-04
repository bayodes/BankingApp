package com.revature.controllers;

import com.revature.models.Account;
import com.revature.models.UpdateAccountObject;
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
    private Account mainAccount;

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
                mainAccount = (Account) updatedUser.getStackOfAccounts().pop();

                updatedUser.getStackOfAccounts().push(mainAccount);

                ctx.result("Waiting to see if your account is approved");
            }
        }
    };

    public Handler handleDeposit = (ctx) -> {
        UpdateAccountObject ua = oMap.readValue(ctx.body(), UpdateAccountObject.class);

        mainAccount = aServ.deposit(mainAccount, ua.balance);
        ctx.result(ua.balance + " was added to your account");

//        if(ctx.req.getSession().getAttribute("id") == null) {
//            ctx.status(401);
//            ctx.result("You must login to deposit into an account");
//        } else if (ctx.req.getSession().getAttribute("hasAccount") != null) {
//            aServ.deposit(updatedUser, a);
//            ctx.result(a.getBalance() + " added");
//        } else if (ctx.req.getSession().getAttribute("hasAccount") == null) {
//            ctx.result("You must create an account before making a deposit");
//        }
    };

    public Handler handleWithdraw = (ctx) -> {
        UpdateAccountObject ua = oMap.readValue(ctx.body(), UpdateAccountObject.class);

        if(ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to withdraw into an account");
        } else if (ctx.req.getSession().getAttribute("hasAccount") == null) {
            ctx.result("You must create an account before making a withdraw");
        } else if (ctx.req.getSession().getAttribute("hasAccount") != null) {
            mainAccount = aServ.withdraw(mainAccount, ua.balance);
            ctx.result(ua.balance + " was withdrawn from your account");
        }
    };

    public Handler viewAllAccounts = (ctx) -> {

        ctx.result(oMap.writeValueAsString(updatedUser.getStackOfAccounts().peek()));
    };

    public Handler approveAccount = (ctx) -> {
        aServ.approveAccount(updatedUser);

        ctx.result(oMap.writeValueAsString(updatedUser.getEmail() + " account was approved"));
    };

    public Handler denyAccount = (ctx) -> {
        ctx.result(oMap.writeValueAsString(updatedUser.getEmail() + " account was denied"));
    };

    public Handler closeAccount = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Account a = new Account();
        a.setAccountID(id);
        boolean deleted = aServ.closeAccount(a);

        if (deleted == true) {
            ctx.result("Deleted user");
        } else {
            ctx.result("Account with id: " + a.getAccountID()  + " does not exist in the database");
        }
    };
}
