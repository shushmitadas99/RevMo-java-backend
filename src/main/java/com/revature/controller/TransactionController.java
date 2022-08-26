package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Account;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.service.AccountService;
import com.revature.service.TransactionService;
import com.revature.service.UserService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TransactionController implements Controller {
    private final TransactionService transactionService;
    private UserService userService;
    private AccountService accountService;

    public TransactionController() {
        userService = new UserService();
        accountService = new AccountService();
        transactionService = new TransactionService();
    }


    @Override
    @SuppressWarnings("unchecked")
    public void mapEndpoints(Javalin app) {
        app.post("trx/accounts", ctx ->{
//            try{
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                String role = (String) session.getAttribute("userRole");
                String email = (String) session.getAttribute("email");
                User myUser = userService.getUserByEmail(email);
                    System.out.println(myUser);

                int userId = myUser.getUserId();
                if (Objects.equals(role, "2")) {
                    ObjectMapper om = new ObjectMapper();
                    Map<String, String> newTransaction = om.readValue(ctx.body(), Map.class);
                    System.out.println(newTransaction);
                    ctx.json(transactionService.transferBetweenAccounts(newTransaction, userId));
                    ctx.status(201);
                    }
//            }
//            catch (InvalidParameterException e) {
//                        ctx.json(e.getMessages());
//                        ctx.status(400);
//                    }
                }
        );

        app.post("/trx-send", ctx -> {
            try {
                Transaction tr = ctx.bodyAsClass(Transaction.class);
                int id = tr.getRequesterId();
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                Integer uId = (Integer) session.getAttribute("userId");
                String role = (String) session.getAttribute("userRole");
                if (id == uId || Objects.equals(role, "2")) {
                    ObjectMapper om = new ObjectMapper();
                    Map<String, String> newTransaction = om.readValue(ctx.body(), Map.class);
                    try {
                        ctx.json(transactionService.moveAmountBetweenAccounts(newTransaction));
                        ctx.status(201);
                    } catch (InvalidParameterException e) {
                        ctx.json(e.getMessages());
                        ctx.status(400);
                    }
                } else {
                    ctx.json("User Not Found");
                    ctx.status(404);
                }
            } catch (Exception e) {
                ctx.json(e.getMessage());
                ctx.status(400);
            }

        });

        app.post("/trx-req", ctx -> {
            try {
                Transaction tr = ctx.bodyAsClass(Transaction.class);
                int id = tr.getRequesterId();
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                Integer uId = (Integer) session.getAttribute("userId");
                String role = (String) session.getAttribute("userRole");
                if (id == uId || Objects.equals(role, "2")) {
                    ObjectMapper om = new ObjectMapper();
                    Map<String, String> newTransaction = om.readValue(ctx.body(), Map.class);
                    try {
                        ctx.json(transactionService.requestAmount(newTransaction));
                        ctx.status(201);
                    } catch (InvalidParameterException e) {
                        ctx.json(e.getMessages());
                        ctx.status(400);
                    }
                } else {
                    ctx.json("User Not Found");
                    ctx.status(404);
                }
            } catch (Exception e) {
                ctx.json(e.getMessage());
                ctx.status(400);
            }

        });

        app.put("/trx-req", ctx -> {
//            endpoint expects JSON { "statusId": "{2 for approved, 3 for denied}", "transactionId": "{trxId}"}
            try {
                Transaction tr = ctx.bodyAsClass(Transaction.class);
                int transactionId = tr.getTransactionId();
                String email = userService.getRequesteeByTransactionId(transactionId);
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                String emailSignedInUser = (String) session.getAttribute("email");
                String role = (String) session.getAttribute("userRole");

                if (Objects.equals(email, emailSignedInUser) || Objects.equals(role, "2")) {
                    ObjectMapper om = new ObjectMapper();
                    Map<String, String> newTransaction = om.readValue(ctx.body(), Map.class);
                    try {
                        ctx.json(transactionService.handleRequestAmount(newTransaction));
                        ctx.status(201);
                    } catch (InvalidParameterException e) {
                        ctx.json(e.getMessages());
                        ctx.status(400);
                    }
                } else {
                    ctx.json("User Not Found");
                    ctx.status(404);
                }
            } catch (Exception e) {
                ctx.json(e.getMessage());
                ctx.status(400);
            }

        });

        app.get("/trx", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactions());
                ctx.status(200);
            }
        });
        app.get("/trx/account/{aid}", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            int aid = Integer.parseInt(ctx.pathParam("aid"));
            ctx.json(transactionService.getAllTransactions(aid));
            ctx.status(200);

        });

        app.get("/trx/{requesterId}/requester", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            String requesterId = ctx.pathParam("requesterId");
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsByRequesterId(requesterId));
                ctx.status(200);
            }
        });

        app.get("/trx/{senderId}/sender", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            String senderId = ctx.pathParam("senderId");
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsBySenderId(senderId));
                ctx.status(200);
            }
        });
        

        app.get("/trx/{receivingId}/receiver", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String emailSignedInUser = (String) session.getAttribute("email");
            String role = (String) session.getAttribute("userRole");
            String receivingId = ctx.pathParam("receivingId");
            List<String> emails = userService.getReceiverByTransactionId(Integer.parseInt(receivingId));
            if (emails.contains(emailSignedInUser)|| role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsByReceivingId(receivingId));
                ctx.status(200);
            }
        });

        app.get("/trx/status/{status-name}/{aId}", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            String statusName = ctx.pathParam("status-name").toUpperCase();
            int aId = Integer.parseInt(ctx.pathParam("aId"));
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsByStatusName(statusName, aId));
                ctx.status(200);
            }
        });

        app.get("/trx/{descriptionId}/description", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            String description = ctx.pathParam("descriptionId");
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsByDescription(description));
                ctx.status(200);
            }
        });

        app.get("/trx/income-by-account/{aId}/{month}/{year}", ctx -> {
            try {
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                String emailSignedInUser = (String) session.getAttribute("email");
                String role = (String) session.getAttribute("userRole");
                int aId = Integer.parseInt(ctx.pathParam("aId"));
                int month = Integer.parseInt(ctx.pathParam("month"));
                int year = Integer.parseInt(ctx.pathParam("year"));
                List<Account> accounts = accountService.getAccountsByEmail(emailSignedInUser);
                List<Integer> accountIds = new ArrayList<Integer>();
                for (Account a : accounts)
                    accountIds.add(a.getAccountId());
                if (accountIds.contains(aId) || Objects.equals(role, "2")) {
                    ctx.json(transactionService.trackAccountIncome(aId, month, year));
                    ctx.status(201);
                } else {
                    ctx.json("Access Denied");
                    ctx.status(401);
                }
            } catch (Exception e) {
                ctx.json(e.getMessage());
                ctx.status(400);
            }
        });


        app.get("/trx/income-by-user/{uId}/{month}/{year}", ctx -> {
            try {
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                String emailSignedInUser = (String) session.getAttribute("email");
                String role = (String) session.getAttribute("userRole");
                int aId = Integer.parseInt(ctx.pathParam("aId"));
                int month = Integer.parseInt(ctx.pathParam("month"));
                int year = Integer.parseInt(ctx.pathParam("year"));
                List<Account> accounts = accountService.getAccountsByEmail(emailSignedInUser);
                List<Integer> accountIds = new ArrayList<Integer>();
                for (Account a : accounts)
                    accountIds.add(a.getAccountId());
                if (accountIds.contains(aId) || Objects.equals(role, "2")) {
                    ctx.json(transactionService.trackAccountIncome(aId, month, year));
                    ctx.status(201);
                } else {
                    ctx.json("Access Denied");
                    ctx.status(401);
                }
            } catch (Exception e) {
                ctx.json(e.getMessage());
                ctx.status(400);
            }
        });
//        app.post("/trx/request", ctx -> {
//           HttpServletRequest req = ctx.req;
//           HttpSession session = req.getSession();
//           Integer uId = (Integer) session.getAttribute("userId");
//           ObjectMapper om = new ObjectMapper();
//           Map<String, String> trx = om.readValue(ctx.body(), Map.class);
//           ctx.json(transactionService.sendMoneyRequest(trx, uId));
//           ctx.status(200);
//        });
    }

}


