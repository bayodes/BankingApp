/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.revature;

import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.dao.AccountDaoJDBC;
import com.revature.dao.IAccountDao;
import com.revature.dao.IUserDao;
import com.revature.dao.UserDaoJDBC;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Driver {

    public static void main(String[] args) {

        IUserDao uDao = new UserDaoJDBC();
        UserService uServ = new UserService(uDao);
        UserController uCon = new UserController(uServ);

        IAccountDao aDao = new AccountDaoJDBC();
        AccountService aServ = new AccountService(aDao);
        AccountController aCon = new AccountController(aServ);

        Javalin server = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        });

        server.routes(()-> {
            path("users", () -> {
                post("/register", uCon.handleRegister);
                post("/login", uCon.handleLogin);
                put("/", uCon.handleUpdateUser);
                delete("/{id}", uCon.handleDeleteUser);
            });

            path("accounts", () -> {
                post("/create", aCon.handleCreateAccount);
                put("/deposit", aCon.handleDeposit);
                put("/withdraw", aCon.handleWithdraw);
//                delete("/{id}", uCon.handleDeleteUser);
            });

            path("manager", () -> {
                get("/view", aCon.viewAllAccounts);
                post("/approve", aCon.approveAccount);
                put("/deny", aCon.denyAccount);
//                delete("/{id}", uCon.handleDeleteUser);
            });
        });

        server.start(8000);
    }
}
