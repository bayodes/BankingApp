package com.revature.controllers;

import com.revature.models.LoginObject;
import com.revature.models.RegisterObject;
import com.revature.models.UpdateUserObject;
import com.revature.models.User;
import com.revature.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class UserController {

    private UserService uServ;
    private ObjectMapper oMap;
    private User u;

    public UserController(UserService uServ){
        this.uServ = uServ;
        this.oMap = new ObjectMapper();
    }

    public Handler handleRegister = (ctx) -> {
        RegisterObject ro = oMap.readValue(ctx.body(), RegisterObject.class);

        u = uServ.registerUser(ro.firstName, ro.lastName, ro.email, ro.password, ro.userType);

        ctx.req.getSession().setAttribute("id", ""+u.getUserID());
        ctx.req.getSession().setAttribute("fName", ""+u.getFirstName());
        ctx.req.getSession().setAttribute("lName", ""+u.getLastName());
        ctx.req.getSession().setAttribute("email", ""+u.getEmail());
        ctx.req.getSession().setAttribute("password", ""+u.getPassword());
        ctx.req.getSession().setAttribute("userType", ""+u.getUserType());

        ctx.result("Registered user: " + u.getEmail());
    };

    public Handler handleLogin = (ctx) -> {
        LoginObject lo = oMap.readValue(ctx.body(), LoginObject.class);

        u = uServ.loginUser(lo.email, lo.password);

        if (u != null) {
            ctx.req.getSession().setAttribute("id", ""+u.getUserID());
            ctx.req.getSession().setAttribute("fName", ""+u.getFirstName());
            ctx.req.getSession().setAttribute("lName", ""+u.getLastName());
            ctx.req.getSession().setAttribute("email", ""+u.getEmail());
            ctx.req.getSession().setAttribute("password", ""+u.getPassword());
            ctx.req.getSession().setAttribute("userType", ""+u.getUserType());
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