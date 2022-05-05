package com.revature.controllers;

import com.revature.exceptions.NegativeNumberInputException;
import com.revature.models.Account;
import com.revature.models.TransferObject;
import com.revature.models.UpdateAccountObject;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class AccountController {

    private AccountService aServ;
    private ObjectMapper oMap;
    private User u;
    private Account a;

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
            String userType = (String) ctx.req.getSession().getAttribute("userType");
            String m = (String) ctx.req.getSession().getAttribute("m");
            String user = (String) ctx.req.getSession().getAttribute("u");

            if (userType.equalsIgnoreCase("user") || user.equalsIgnoreCase("user")) {
                u = new User(userID, firstName, lastName, email, password, userType);

                u = aServ.addAccount(u);

                ctx.result("Waiting to see if your account is approved");
            } else {
                ctx.result("Managers can't create accounts");
            }
        }
    };

    public Handler handleDeposit = (ctx) -> {
        UpdateAccountObject ua = oMap.readValue(ctx.body(), UpdateAccountObject.class);

        if (ua.balance < 0) {
            ctx.result("You cannot deposit a negative number");
            throw new NegativeNumberInputException();
        } else {
            double newBalance = a.getBalance() + ua.balance;

            if (ctx.req.getSession().getAttribute("id") != null) {
                aServ.deposit(a, newBalance);
                ctx.result("$" + ua.balance + " was added to your account. Your new balance" +
                        " is $" + a.getBalance());
            } else if (ctx.req.getSession().getAttribute("id") == null) {
                ctx.status(401);
                ctx.result("You must login to withdraw from an account");
            }
        }
    };

    public Handler handleWithdraw = (ctx) -> {
        UpdateAccountObject ua = oMap.readValue(ctx.body(), UpdateAccountObject.class);

        if (ua.balance < 0) {
            ctx.result("You cannot withdraw a negative number");
            throw new NegativeNumberInputException();
        } else {
            double newBalance = a.getBalance() - ua.balance;

            if (ctx.req.getSession().getAttribute("id") != null) {
                aServ.withdraw(a, newBalance);
                ctx.result("$" + ua.balance + " was withdrawn from your account. Your new balance" +
                        " is $" + a.getBalance());
            } else if (ctx.req.getSession().getAttribute("id") == null) {
                ctx.status(401);
                ctx.result("You must login to withdraw from an account");
            }
        }
    };

    public Handler viewAllAccounts = (ctx) -> {
        if (u.getStackOfAccounts() == null) {
            ctx.result("No accounts to view yet");
        } else {
            ctx.result(oMap.writeValueAsString(u.getStackOfAccounts().peek()));
        }
    };

    public Handler approveAccount = (ctx) -> {
        a = aServ.approveAccount(u);

        UserController c = new UserController(a);
        u.getStackOfAccounts().push(a);

        if (u.getEmail().equalsIgnoreCase("rick.sanchez@mail.com")) {
            ctx.result(oMap.writeValueAsString("morty.smith@mail.com account was approved"));
        } else {
            ctx.result(oMap.writeValueAsString(u.getEmail() + " account was approved"));
        }
    };

    public Handler denyAccount = (ctx) -> {
        ctx.result(oMap.writeValueAsString(u.getEmail() + " account was denied"));
    };

    public Handler closeAccount = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));

        Account a = new Account();
        a.setAccountID(id);

        if (ctx.req.getSession().getAttribute("id") != null) {
            aServ.closeAccount(a);
        } else if (ctx.req.getSession().getAttribute("id") == null) {
            ctx.result("Login before deleting an account");
        } else {
            ctx.result("Account not found in the database");
        }
    };

    public Handler handleTransfer = (ctx) -> {
        TransferObject ta = oMap.readValue(ctx.body(), TransferObject.class);
        double[] balances;

        balances = aServ.transferMoney(ta.accountIdFrom, ta.accountIdTo, ta.amount);

        if (ctx.req.getSession().getAttribute("id") != null) {
            ctx.result("$" + ta.amount + " was transferred from account with id: " + ta.accountIdFrom +
                    ", to account with id " + ta.accountIdTo + "\nAccount one original balance: " + balances[0] +
                    ", Account one new balance: " + balances[2] + "\nAccount two original balance: " + balances[1] +
                    ", Account two new balance: " + balances[3]);
            //aServ.inputTransferTable(balances, ta.amount);
        } else if (ctx.req.getSession().getAttribute("id") == null) {
            ctx.status(401);
            ctx.result("You must login to withdraw from an account");
        }
    };
}
