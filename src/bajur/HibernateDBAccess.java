package bajur;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import bajur.entity.Employee;

public class HibernateDBAccess {
	
	private static SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(Employee.class)
			.buildSessionFactory();
	
	private static Session session;
	
	private static void addEmployee(String firstName, String lastName, String company) {
		Employee employee = new Employee(firstName, lastName, company);
		
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		session.save(employee);
		
		session.getTransaction().commit();
	}
	
	private static void displayEmployees() {
		List<Employee> employees;
		
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		employees = session.createQuery("from Employee").getResultList();
		
		for(Employee iter:employees) {
			System.out.println(iter);
		}
		
		session.getTransaction().commit();
	}

	public static void main(String[] args) {		
		try {
			addEmployee("Andrzej","Strzelba","Dev");
			addEmployee("Maciej","Malinowski","Dev");
			addEmployee("Jan","Kowalski","CEO");
			
			displayEmployees();
						
		}
		finally {
			factory.close();
		}

	}

}
