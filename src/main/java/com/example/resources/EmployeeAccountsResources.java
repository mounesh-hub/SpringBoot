package com.example.resources;

import com.example.dao.EmployeeDao;
import com.example.model.BankAccount;
import com.example.model.BankAccountWrapper;
import com.example.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/employee-details/{employeeId}/accounts")
@CrossOrigin
public class EmployeeAccountsResources {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeDao dao;

    @Value("${employee-account-service}")
    private String accountServiceUrl;

    Logger log = LoggerFactory.getLogger(EmployeeAccountsResources.class);

    @PostMapping
    public ResponseEntity<Employee> addAccount(@RequestBody BankAccount account, @PathVariable("employeeId") int ownerId){
        log.trace("employee id in EmployeeAccountsResources#addAccount :"+ownerId);
        BankAccount newAccount = restTemplate.postForObject(accountServiceUrl+ ownerId + "/accounts", account, BankAccount.class);
        Employee owner = dao.getEmployee(ownerId);
        owner.setAccounts(Collections.singletonList(newAccount));
        return new ResponseEntity<>(owner, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Employee> updateAccount(@RequestBody BankAccount account, @PathVariable("employeeId") int ownerId){
        log.trace("employee id in EmployeeAccountsResources#updateAccount :"+ownerId);
         restTemplate.put(accountServiceUrl+ ownerId + "/accounts", account, new Object());
        Employee owner = dao.getEmployee(ownerId);
        owner.setAccounts(Collections.singletonList(account));
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Employee> deleteAccount(@PathVariable("employeeId") int ownerId, @PathVariable("accountId") int accountId){
        log.trace("employee id in EmployeeAccountsResources#deleteAccount :"+ownerId +"and "+accountId);
        BankAccount account = restTemplate.getForObject(accountServiceUrl+ ownerId + "/accounts/"+accountId, BankAccount.class);
        restTemplate.delete(accountServiceUrl+ ownerId + "/accounts/"+accountId);
        Employee owner = dao.getEmployee(ownerId);
        owner.setAccounts(Collections.singletonList(account));
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Employee> getAllAccounts(@PathVariable("employeeId") int ownerId){
        log.trace("employee id in EmployeeAccountsResources#getAllAccounts :"+ownerId);
        String url = accountServiceUrl+ ownerId + "/accounts";
        log.trace("employee id in EmployeeAccountsResources#getAllAccounts url:"+ url);
        BankAccountWrapper accounts = restTemplate.getForObject(url, BankAccountWrapper.class);
        Employee owner = dao.getEmployee(ownerId);
        owner.setAccounts(accounts.getAccountList());
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }
}
