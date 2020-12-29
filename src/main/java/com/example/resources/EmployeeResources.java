package com.example.resources;

import com.example.dao.EmployeeDao;
import com.example.model.BankAccountWrapper;
import com.example.model.Employee;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/employees")
@CrossOrigin
public class EmployeeResources {

    @Autowired
    private EmployeeDao service;

    @Autowired
    private RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(EmployeeResources.class);

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getFallBackEmployee",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public Employee getEmployee(@PathVariable("employeeId") int id) {
        //System.out.println("employee id =" + id);
        log.trace("employee id =" + id);

        /*
        if(id > 100){
           throw new EmployeeNotFoundException("employee not found for id = " + id);
        }
         */
        Employee employee = service.getEmployee(id);
        BankAccountWrapper accountWrapper = restTemplate.getForObject("http://employee-account-service/"+ id + "/accounts", BankAccountWrapper.class);
        employee.setAccounts(accountWrapper.getAccountList());
        return  employee;
    }

    public Employee getFallBackEmployee(@PathVariable("employeeId") int id) {
       //System.out.println("inside EmployeeResources#getFallBackEmployee");
        log.trace("inside EmployeeResources#getFallBackEmployee");
       return  new Employee("dummy");
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        //System.out.println("employee object =" + employee);
        log.trace("employee object =" + employee);
        Employee newEmployee = service.addEmployee(employee);
        return newEmployee;
    }


    @PutMapping
    public Employee updateEmployeeData(@RequestBody Employee employee) {
        Employee newEmployee = service.updateEmployee(employee);
        return newEmployee;
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public Employee deleteEmployee(@PathVariable("employeeId") int employeeId) {
        return service.deleteEmployee(employeeId);
    }

    @GetMapping
    public List<Employee> getAllEmployeeData() {
        return service.getAllEmployee();
    }
}
