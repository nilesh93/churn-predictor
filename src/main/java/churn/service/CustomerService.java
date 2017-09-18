package churn.service;

import churn.db.DataMiningManager;
import churn.model.Customer;
import com.google.gson.Gson;

import java.sql.SQLException;

public class CustomerService {
    public Customer customer;

    public Customer create(String body) throws SQLException {
        System.out.println("[Log][Info][CreateCustomer]" + body);
        Customer cus = new Gson().fromJson(body,Customer.class);
        System.out.println("[Log][success][CreateCustomer]" + cus);
        return this.getPrediction(cus);
    }

    public Customer getPrediction(Customer cus) throws SQLException {
        DataMiningManager.insertNewData(cus);
        DataMiningManager.getLastInsertedCustomer(cus);
        DataMiningManager.getPredictionResult(cus);

        System.out.println("[Log][success][getPrediction]" + cus);
        return cus;
    }
}
