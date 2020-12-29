package com.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.example.dao.EmployeeDao;
import com.example.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService implements EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    public EmployeeService() {
    }

    @Override
    public Employee addEmployee(Employee employee) {
        String query = "insert into employee values(?,?,?)";
        jdbcTemplateObject.update(query, employee.getId(), employee.getName(), employee.getAddress());
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        String query = "update employee set employee_name=?, employee_address=? where employee_id=?";
        jdbcTemplateObject.update(query, employee.getName(), employee.getAddress(), employee.getId());
        return employee;
    }


    @Override
    public Employee getEmployee(int id) {
        String query = "select * from  employee where employee_id = ?";
        return jdbcTemplateObject.queryForObject(query, new Object[]{id}, new EmployeeRowMapper());
    }

    private class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultset, int arg1) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultset.getInt(1));
            employee.setAddress(resultset.getString(2));
            employee.setName(resultset.getString(3));
            return employee;
        }

    }

    @Override
    public Employee deleteEmployee(int employeeId) {
        Employee employee = getEmployee(employeeId);
        String query = "delete from employee where employee_id = ?";
        jdbcTemplateObject.update(query, employeeId);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        String query = "select * from employee";
        return jdbcTemplateObject.query(query, new EmployeeRowMapper());

    }

}
