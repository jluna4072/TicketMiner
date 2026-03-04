package manager;

import java.util.ArrayList;
import java.util.List;
import model.Customer;

/**
 * Manages all customers in the system.
 * Stores Customer objects that match the Customer CSV structure.
 */
public class CustomerManager {

    private List<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();

        // placeholder sample customer so the program compiles and runs
        customers.add(new Customer(
                "417",
                "Adam",
                "Bass",
                "adambass",
                "Fun!23",
                "Customer",
                1159697.17,
                false,
                0
        ));
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
