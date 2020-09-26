package com.misa.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.misa.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionF;

	@Override
	public List<Customer> getCustomers() {
		Session session = sessionF.getCurrentSession();
		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
		List<Customer> list = query.getResultList();
		
		return list;
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionF.getCurrentSession();
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Session session = sessionF.getCurrentSession();
		Customer customer = session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		Session session = sessionF.getCurrentSession();
		Query query = session.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);
		query.executeUpdate();
	}
	
}
