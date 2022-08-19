package com.revature.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.service.TransactionService;
import com.revature.service.UserService;
import io.javalin.Javalin;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

public class TransactionController implements Controller {
    private final TransactionService transactionService;
    private UserService userService;

    public TransactionController() {

        transactionService = new TransactionService();
    }


    @Override
    @SuppressWarnings("unchecked")
    public void mapEndpoints(Javalin app) {
        app.post("/trx/accounts", ctx -> {
            try {
                Transaction tr = ctx.bodyAsClass(Transaction.class);
                int id = tr.getRequesterId();
                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                Integer uId = (Integer) session.getAttribute("userId");

                if(id == uId){
                    ObjectMapper om = new ObjectMapper();
                    Map<String, String> newTransaction = om.readValue(ctx.body(), Map.class);
                    try {
                        ctx.json(transactionService.addTransactionById(newTransaction));
                        ctx.status(201);
                    } catch (InvalidParameterException e) {
                        ctx.json(e.getMessages());
                        ctx.status(400);
                    }
                } else{
                    ctx.json("User Not Found");
                    ctx.status(404);
                }
            } catch (Exception e){
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
            String role = (String) session.getAttribute("userRole");
            String receivingId = ctx.pathParam("receivingId");
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsByReceivingId(receivingId));
                ctx.status(200);
            }
        });

        app.get("/trx/{status-name}/status-name", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            String statusName = ctx.pathParam("status-name").toUpperCase();
            if (role.equals("2")) {
                ctx.json(transactionService.getAllTransactionsByStatusName(statusName));
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


