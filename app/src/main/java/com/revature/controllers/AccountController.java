package com.revature.controllers;

import com.revature.models.Account;
import com.revature.models.CreateAccountObject;
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

        if(ctx.req.getSession().getAttribute("id") != null) {
            int accountID = Integer.parseInt((String) ctx.req.getSession().getAttribute("id"));

//            String firstName = (String) ctx.req.getSession().getAttribute("fName");
//            String lastName = (String) ctx.req.getSession().getAttribute("lName");
//            String email = (String) ctx.req.getSession().getAttribute("email");
//            String password = (String) ctx.req.getSession().getAttribute("password");
//
//            System.out.println(firstName);
//            System.out.println(lastName);
//            System.out.println(email);
//            System.out.println(password);

            User u = new User();
            u.setUserID(accountID);

            System.out.println(u.getUserID());
            System.out.println(u);

            aServ.addAccount(u);
            ctx.status(201);
            ctx.result(u.getEmail() + " added a new bank account");
        } else {
            ctx.status(401);
            ctx.result("You must login to create an account");
        }
    };
}
