package com.barlas.kafkaexample.controller;

import com.barlas.kafkaexample.event.EmployeeCreated;
import com.barlas.kafkaexample.event.EmployeeDeleted;
import com.barlas.kafkaexample.event.EmployeeUpdated;
import com.barlas.kafkaexample.model.Employee;
import com.barlas.kafkaexample.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.util.Optional;

@RestController
@Api(tags = "Employee API")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);

    private final KafkaTemplate<String, Object> template;
    private final String topicName;

    public EmployeeController(
            final KafkaTemplate<String, Object> template,
            @Value("${tpd.topic-name}") final String topicName, EmployeeRepository employeeRepository) {
        this.template = template;
        this.topicName = topicName;
        this.employeeRepository = employeeRepository;
    }


    @PostMapping("/employee")
    @Operation(summary = "Create Employee")
    @Produces({"application/json"})
    public String createEmployee(@RequestBody Employee employee) throws Exception {
        Employee savedEmployee = employeeRepository.save(employee);

        this.template.send(topicName, new EmployeeCreated("Employee created", savedEmployee.getFullName()));
        logger.info("Employee saved with name: " + employee.getFullName());

        return "Employee created!";
    }


    @GetMapping("/employee")
    @Produces({"application/json"})
    public String getEmployees() throws Exception {
        Iterable<Employee> employees = employeeRepository.findAll();

        return employees.toString();
    }


    @GetMapping("/employee/{id}")
    @Produces({"application/json"})
    public String getEmployeeById(@PathVariable long id) throws Exception {
        Optional<Employee> employees = employeeRepository.findById(id);

        return employees.toString();
    }


    @PutMapping("/employee/{id}")
    @Operation(summary = "Update Employee")
    @Produces({"application/json"})
    public String updateEmployee(@RequestBody Employee employee, @PathVariable long id) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty())
            return "Employee not found!";

        Employee employeeInDb = employeeOptional.get();

        employeeInDb.setBirthday(employee.getBirthday());
        employeeInDb.setHobbies(employee.getHobbies());

        employeeRepository.save(employeeInDb);

        this.template.send(topicName, new EmployeeUpdated("Employee updated", employeeInDb.getFullName()));

        return "Employee updated!";
    }


    @DeleteMapping("/employee/{id}")
    @Operation(summary = "Delete Employee")
    @Produces({"application/json"})
    public String deleteEmployee(@PathVariable long id) throws Exception {
        employeeRepository.deleteById(id);

        this.template.send(topicName, new EmployeeDeleted("Employee deleted with id: ", id));

        return "Employee deleted!";
    }
}
