package com.revature.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Transaction;
import com.revature.service.TransactionService;
import io.javalin.Javalin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class TransactionController implements Controller {
    private final TransactionService transactionService;

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
                }else{
                    ctx.json("User Not Found");
                    ctx.status(404);
                }
            } catch (Exception e){
                ctx.json(e.getMessage());
                ctx.status(400);
            }

        });

//        app.get("/transactions", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//            ctx.json(transactionService.getAllTransactions());
//            ctx.status(200);
//        });
//
//
//        app.get("/transaction/requesterId", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsByRequesterId("1"));
//            ctx.status(200);
//        });
//
//
//        app.get("/transaction/senderId", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsbySenderId("1"));
//            ctx.status(200);
//        });
//
//
//        app.get("/transaction/receivingId", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsByReceivingId("1"));
//            ctx.status(200);
//        });
//
//
//        app.get("/transaction/statusId", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsByStatusName("PENDING"));
//            ctx.status(200);
//        });
//
//        app.get("/transaction/descriptionId", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsByDescription("Payment"));
//            ctx.status(200);
//        });

//        app.get("/Transaction/approved", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsByApproved("1"));
//            ctx.status(200);
//        });


    }
}


