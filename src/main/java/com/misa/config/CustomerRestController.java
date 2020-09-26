package com.misa.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misa.entity.Customer;
import com.misa.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	@Autowired
	private CustomerService cs;
	
	@GetMapping("/customers")
	public List<Customer> listCustomers() {
		return cs.getCustomers();
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer customer = cs.getCustomer(customerId);
		if(customer == null) {
			throw new CustomerNotFound("Customer " + customerId + " not found");
		}
		
		return customer;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		// because saveOrUpdate method is used in hibernate DAO
		// this way, id is set to null
		customer.setId(0); 
		cs.saveCustomer(customer);
		return customer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		cs.saveCustomer(customer);
		return customer;
	}

	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		Customer customer = cs.getCustomer(customerId);
		if(customer == null) {
			throw new CustomerNotFound("Customer " + customerId + " not found");
		}
		cs.deleteCustomer(customerId);
		return "Deleted " + customerId;
	}
}
