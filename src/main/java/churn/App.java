package churn;

import churn.db.DBConnection;
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

        // Book Services
        get("/", (req, res) ->new ModelAndView(new HashMap<>(), "index"), new JadeTemplateEngine());

    }
}