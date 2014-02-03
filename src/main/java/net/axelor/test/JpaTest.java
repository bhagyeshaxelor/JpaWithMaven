package net.axelor.test;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.dom4j.dtd.ExternalEntityDecl;

import net.axelor.domain.Department;
import net.axelor.domain.Employee;


public class JpaTest {

    private EntityManager manager;

    public JpaTest(EntityManager manager) {
        this.manager = manager;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();
        JpaTest test = new JpaTest(manager);

        EntityTransaction tx = manager.getTransaction();
        try {
            test.createEmployees(tx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.listEmployees();
        
        System.out.println(".. done");
    }

    private void createEmployees(EntityTransaction et) {
    		
    		et.begin();
    		Department department = new Department("Bhagyesh");
            manager.persist(department);
            manager.persist(new Employee("Jakab Gipsz",department));
            manager.persist(new Employee("Captain Nemo",department));
            et.commit();
    }

    private void listEmployees() {
        List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
        System.out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    
    
    
    
    
    
    
    
    

}