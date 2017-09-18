package churn;

import churn.db.DBConnection;
import churn.service.CustomerService;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.post;

public class App {
    public static void main(String... args) {

        Connection db =  DBConnection.getConnection();
        CustomerService cus = new CustomerService();
        Gson gson = new Gson();
        // Book Services
        get("/", (req, res) ->new ModelAndView(new HashMap<>(), "index"), new JadeTemplateEngine());

        post("/save", (req,res)->{
            try {
                return gson.toJson(cus.create(req.body()));
            }catch(Exception e){
                res.status(400);
                System.out.println("[Log][Error][CreateCustomer] "+ e);
                return  gson.toJson(e);
            }
        });
    }
}