package com.example.dao;

import java.util.List;

import com.example.model.Employee;

public interface EmployeeDao{

    public Employee addEmployee(Employee employee);
    public Employee updateEmployee(Employee employee);
    public Employee getEmployee(int id);
    public Employee deleteEmployee(int employeeId);
    public List<Employee> getAllEmployee();

}
