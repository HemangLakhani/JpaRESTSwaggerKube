package com.boot.RESTAPP.JpaREST.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.RESTAPP.JpaREST.entity.Employee;
import com.boot.RESTAPP.JpaREST.exception.ResourceNotFoundException;
import com.boot.RESTAPP.JpaREST.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//declaring rest controller
@RestController

//Creating a base request mapping
@RequestMapping("/api")

//using swagger annotations to make swagger documentation.
@Api(value = "Employee Management System", description = "This API is implementing CRUD operations on Employee database.")
public class EmployeeRestController {
	
	/*
	private EmployeeDAO employeeDAO;
	// For the first time we will create a quick and dirty solution by injecting employee DAO directly
	// using constructor injection
	@Autowired
	public EmployeeRESTController(EmployeeDAO theEmployeeDAO) {
		employeeDAO = theEmployeeDAO;
	}
	*/
	
	/*Once we have developed our EmployeeService. We don't want to use EmployeeDAO object. As our
	 * service is ultimately calling DAO methods. We can use it.
	 * Rather than directly using DAO we can use services. 
	 * This is what we have defined in our Architecture.
	 */
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	//defining ApiOperations and responses expected from this endpoint.
	@ApiOperation(value = "Returns a list of employees.", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A JSON array of employees details."),
			@ApiResponse(code = 404, message = "The page you are looking for is not found. Please check the url."),
			@ApiResponse(code = 500, message = "There might be some problem with the server. Please make sure you\r\n" + 
					"            have connected to google cloud. Also check the connection manually.")
	})
	
	// expose /employees end point and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	@ApiOperation(value = "Get the employee with the specified id back from the database.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A JSON format employee object."),
			@ApiResponse(code = 400, message = "The specified employee ID is not valid (not a number)."),
			@ApiResponse(code = 404, message = "The specified employee ID was not found. Please enter existing id.")
	})
	
	/*add mapping for GET /employees/{employeeID}
	 *@PathVariable is used to get values from user/client.. @PathVariable binds user supplied value
	 *to method parameter. Here in method parameter we are taking "int theId" which means 
	 *we are expecting "int" value from user end.
	 *MUST REMEMBER THAT MAPPING "/{theId}" AND @PathVariable int "theId" BOTH NAME MUST BE SAME OTHERWISE IT WILL THROW INTERNAL
	 *SERVER ERROR SAYING THAT "MISSING PathVariable: theId."
	 */
	 
	@GetMapping("/employees/{Id}")
	public ResponseEntity<Employee> getEmployee(
			@ApiParam(value = "Employee id",required = true)@PathVariable int Id)
		throws ResourceNotFoundException{
		
		Employee theEmployee = employeeService.findById(Id);
		if(theEmployee == null) {
			throw new ResourceNotFoundException("Employee not found for this Id::" + Id);
		}
		
		return ResponseEntity.ok().body(theEmployee);
	}
	
	
	@ApiOperation(value = "A new employee object will be created.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "A newly created employee object."),
			@ApiResponse(code = 500, message = "Unexpected error occured at server side so your object is not\r\n" + 
					"            inserted and not retrieved either.")
	})
	
	//add mapping for POST /employee - add a new employee
	@PostMapping("/employees")
	public Employee addEmployee(
			@ApiParam(value = "Employee object", required = true)@RequestBody Employee theEmployee) {
		
		// If REST client passes "ID" in the request body, we will set it to "0"
		// this will force to add a new employee rather then updating..
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	@ApiOperation(value = "An existing record of an employee will be updated.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "An updated record of employee."),
			@ApiResponse(code = 404, message = "The record of an employee you provided does not exists in the\r\n" + 
					"            system. Please check ID of an employee.")
	})
	
	//add mapping PUT /employees - update the existing employee
	@PutMapping("/employees")
	public Employee updateEmployee(
			@ApiParam(value = "Updated employee object", required = true)@Valid @RequestBody Employee theEmployee) {
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	
	@ApiOperation(value = "An existing record with provided employee ID will be deleted.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The employee record is deleted successfully."),
			@ApiResponse(code = 400, message = "The specified employee ID is not valid (not a number)."),
			@ApiResponse(code = 404, message = "The specified employee ID was not found. Please enter existing id.")
	})

	//add mapping for DELETE /employees/{employeeId} - delete the employee
	
	@DeleteMapping("/employees/{Id}")
	public String deleteById(
			@ApiParam(value = "Employee id to delete.", required = true)@PathVariable int Id)
		throws ResourceNotFoundException{
		
		Employee theEmployee = employeeService.findById(Id);
		
		if(theEmployee == null) {
			throw new ResourceNotFoundException("Employee not found for this Id::" + Id);
		}
		
		employeeService.deleteById(Id);
		
		return "Deleted employee id :-" + Id;
	}
	
}
