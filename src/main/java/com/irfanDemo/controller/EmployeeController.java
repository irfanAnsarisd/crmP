package com.irfanDemo.controller;

import com.irfanDemo.entity.Employee;
import com.irfanDemo.payload.EmployeeDto;
import com.irfanDemo.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;


@RestController
@RequestMapping("/api/v6/Employee")
public class EmployeeController {

    private EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;

    }

    @PostMapping("/add")
    public ResponseEntity<?> createEmp(
           @Valid @RequestBody EmployeeDto dto,
           BindingResult result
            ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        EmployeeDto empDto = employeeService.addEmployee(dto);
        return new ResponseEntity<>(empDto, HttpStatus.CREATED);
    }



    @DeleteMapping
    public ResponseEntity<String> deleteEmp(
            @RequestParam Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmploye(
            @RequestParam Long id,
            @RequestBody EmployeeDto dto
            ){
        EmployeeDto employeeDto = employeeService.updateEmployee(id, dto);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

//    http://localhost:8080/api/v6/Employee?pageSize=3&pageNo=1&sortBy=emailId&sortDir=desc
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmp(
            @RequestParam(name="pageSize",required = false,defaultValue = "5") int pageSize,
            @RequestParam(name="pageNo",required = false,defaultValue = "0") int pageNo,
            @RequestParam(name="sortBy",required = false,defaultValue = "name") String sortBy,
            @RequestParam(name="",required = false,defaultValue = "asc") String sortDir
    ){
        List<EmployeeDto> empDto = employeeService.getEmployee(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(empDto, HttpStatus.OK);
    }

    @GetMapping("/employeeId/{empId}")
    public ResponseEntity<EmployeeDto> getEmpById(
            @PathVariable Long empId
    ){
        EmployeeDto empById = employeeService.getEmpById(empId);
        return new ResponseEntity<>(empById, HttpStatus.OK);
    }

    @PostMapping("/addListOfEmp")
    public ResponseEntity<List<?>> addMultipleEmployee(
            @Valid @RequestBody List<EmployeeDto> dtos,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList(), HttpStatus.BAD_REQUEST);
        }
        List<EmployeeDto> empDtos = employeeService.addMultipleEmployee(dtos);
        return new ResponseEntity<>(empDtos, HttpStatus.CREATED);
    }

}

//1.spring  validation  dependency in pom.xml
//2.spring  validation annotated  in dto class
// 2. @ valid annotation
//3. BindingResult in parameter it
//4. if  hasError
