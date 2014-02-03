package net.axelor.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.axelor.domain.Department;
import net.axelor.domain.Employee;


public class JpaTestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1031422249396784970L;

	private static final String PERSISTENCE_UNIT_NAME = "todos";
	private EntityManager manager;

    public JpaTestServlet(EntityManager manager) {
        this.manager = manager;
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
	        EntityManager manager = factory.createEntityManager();
	        JpaTestServlet test = new JpaTestServlet(manager);

	        EntityTransaction tx = manager.getTransaction();
	        tx.begin();
	        try {
	            test.createEmployees();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        tx.commit();

	        test.listEmployees();

	        System.out.println(".. done");
	}
	
	 private void createEmployees() {
	        int numOfEmployees = manager.createNativeQuery("Select a From Employee a", Employee.class).getResultList().size();
	        if (numOfEmployees == 0) {
	            Department department = new Department("java");
	           
	            manager.persist(new Employee("Jakab Gipsz",department));
	            manager.persist(new Employee("Captain Nemo",department));

	        }
	    }

	    private void listEmployees() {
	        List<Employee> resultList = manager.createNativeQuery("Select a From Employee a", Employee.class).getResultList();
	        System.out.println("num of employess:" + resultList.size());
	        for (Employee next : resultList) {
	            System.out.println("next employee: " + next);
	        }
	    }
	
}
