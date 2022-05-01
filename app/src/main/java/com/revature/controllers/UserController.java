package com.revature.controllers;

import com.revature.models.LoginObject;
import com.revature.models.RegisterObject;
import com.revature.models.User;
import com.revature.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class UserController {

    private UserService uServ;
    private ObjectMapper oMap;

    public UserController(UserService uServ){
        this.uServ = uServ;
        this.oMap = new ObjectMapper();
    }

    public Handler handleRegister = (ctx) -> {
        RegisterObject ro = oMap.readValue(ctx.body(), RegisterObject.class);

        System.out.println(ro);

        uServ.registerUser(ro.firstName, ro.lastName, ro.email, ro.password);
        ctx.status(201);
        ctx.result("Created user: " + ro.firstName + " " + ro.lastName);
    };

    public Handler handleLogin = (ctx) -> {
        LoginObject lo = oMap.readValue(ctx.body(), LoginObject.class);

        User u = uServ.loginUser(lo.email, lo.password);

        System.out.println(lo);

        if (u != null) {
            ctx.req.getSession().setAttribute("id", u.getUserID());
//            ctx.req.getSession().setAttribute("fName", u.getFirstName());
//            ctx.req.getSession().setAttribute("lName", u.getLastName());
//            ctx.req.getSession().setAttribute("email", u.getEmail());
//            ctx.req.getSession().setAttribute("password", u.getPassword());
            ctx.result(oMap.writeValueAsString(u));
        } else {
            ctx.status(403);
            ctx.result("The username or password was incorrect");
        }
    };

    public Handler handleUpdateUser = (ctx) -> {
        User u = oMap.readValue(ctx.body(), User.class);

        if (uServ.updateUser(u) == null) {
            ctx.result("User does not exist in the database");
        } else {
            ctx.result("Updated the following user: " + oMap.writeValueAsString(u));
        }
    };

    public Handler handleDeleteUser = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User u = new User();
        u.setUserID(id);

        if (uServ.deleteUser(u) == true) {
            ctx.result("Deleted user");
        } else {
            ctx.result("User with id: " + u.getUserID() + " does not exist in the database");
        }
    };
}