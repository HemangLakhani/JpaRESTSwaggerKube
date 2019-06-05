package com.boot.RESTAPP.JpaREST.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.RESTAPP.JpaREST.dao.EmployeeDAO;
import com.boot.RESTAPP.JpaREST.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	// All the service methods will delegate calls to EmployeeDAO. So, we will inject the EmployeeDAO
	// using constructor injection.
	private EmployeeDAO employeeDAO;
		
	// What does this @Qualifier annotation do? Must see your notebook for this. 
	//@Qualifier("employeeDAOJpaImpl")
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO theEmplyoeeDAO) {
		employeeDAO = theEmplyoeeDAO;
	}
		
	// Here we will make use of @Transactional annotation rather than using it in DAO Implementation.
	// for all the methods.
	
	@Transactional
	@Override
	public List<Employee> findAll() {
		
		return employeeDAO.findAll();
	}
	
	@Transactional
	@Override
	public Employee findById(int theId) {
		
		return employeeDAO.findById(theId);
	}

	@Transactional
	@Override
	public void save(Employee theEmployee) {
		
		employeeDAO.save(theEmployee);
	}

	@Transactional
	@Override
	public void deleteById(int theId) {
		
		employeeDAO.deleteById(theId);
	}

}
