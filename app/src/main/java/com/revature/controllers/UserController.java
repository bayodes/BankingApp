package com.revature.controllers;

import com.revature.models.*;
import com.revature.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class UserController {

    private UserService uServ;
    private ObjectMapper oMap;
    private User u;
    private Account a;

    public UserController(UserService uServ){
        this.uServ = uServ;
        this.oMap = new ObjectMapper();
    }

    public UserController(Account a) {
        this.a = a;
    }

    public Handler handleRegister = (ctx) -> {
        RegisterObject ro = oMap.readValue(ctx.body(), RegisterObject.class);
        String m = "manager";
        String user = "user";

        u = uServ.registerUser(ro.firstName, ro.lastName, ro.email, ro.password, ro.userType);

        ctx.req.getSession().setAttribute("id", ""+u.getUserID());
        ctx.req.getSession().setAttribute("fName", ""+u.getFirstName());
        ctx.req.getSession().setAttribute("lName", ""+u.getLastName());
        ctx.req.getSession().setAttribute("email", ""+u.getEmail());
        ctx.req.getSession().setAttribute("password", ""+u.getPassword());
        ctx.req.getSession().setAttribute("userType", ""+u.getUserType());
        ctx.req.getSession().setAttribute("m", ""+m);
        ctx.req.getSession().setAttribute("u", ""+user);

        ctx.result("Registered user: " + u.getEmail());
    };

    public Handler handleLogin = (ctx) -> {
        LoginObject lo = oMap.readValue(ctx.body(), LoginObject.class);
        int userID = Integer.parseInt((String) ctx.req.getSession().getAttribute("id"));

        u = uServ.loginUser(lo.email, lo.password);
        ctx.req.getSession().setAttribute("id", ""+u.getUserID());
        if (u != null && u.getUserType().equalsIgnoreCase("user")) {

            //u.getStackOfAccounts().push(a);
            ctx.result(oMap.writeValueAsString(u));
        } else if(u != null && u.getUserType().equalsIgnoreCase("manager")) {
            u.getStackOfAccounts().push(a);
            ctx.result(oMap.writeValueAsString(u));
        } else {
            ctx.status(403);
            ctx.result("The username or password was incorrect");
        }
    };

    public Handler handleUpdateUser = (ctx) -> {
        UpdateUserObject uo = oMap.readValue(ctx.body(), UpdateUserObject.class);

        if (ctx.req.getSession().getAttribute("id") != null) {
            u = uServ.updateUser(uo.firstName, uo.lastName, uo.password, u);
            ctx.result(u.getEmail() + " has updated their information");
        } else if (ctx.req.getSession().getAttribute("id") == null) {
            ctx.result("Login before updating your information");
        } else {
            ctx.result("User not found in the database");
        }
    };

    public Handler handleDeleteUser = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));

        User user = new User();
        user.setUserID(id);

        if (ctx.req.getSession().getAttribute("id") != null) {
            uServ.deleteUser(user);
        } else if (ctx.req.getSession().getAttribute("id") == null) {
            ctx.result("Login before deleting a user");
        } else {
            ctx.result("User not found in the database");
        }
    };

    public Handler handleLogout = (ctx) -> {
        ctx.result(u.getEmail() + " logged out successfully");

        uServ.logOut(u.getEmail());
    };
}