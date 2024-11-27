package com.irfanDemo.service;

import com.irfanDemo.entity.Employee;
import com.irfanDemo.exception.ResourceNotFound;
import com.irfanDemo.payload.EmployeeDto;
import com.irfanDemo.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {

        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        Employee save = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(save);
        return employeeDto;
    }



    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {

        Employee employee = mapToEntity(dto);
        employee.setId(id);
        Employee save = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(save);
        return  employeeDto;
    }

    public List<EmployeeDto> getEmployee(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();


        Pageable page = PageRequest.of(pageNo,pageSize, sort);
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee> employees = all.getContent();
        List<EmployeeDto> collect = employees.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
        return collect;
    }

    public EmployeeDto getEmpById(Long empId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(
                () -> new ResourceNotFound("Employee not found with id: " + empId)
        );

        EmployeeDto dto = mapToDto(employee);
        return dto;
    }

    public Employee mapToEntity(EmployeeDto dto){
        Employee emp = modelMapper.map(dto, Employee.class);
        return emp;
    }

    public EmployeeDto mapToDto(Employee emp){
        EmployeeDto dto = modelMapper.map(emp, EmployeeDto.class);
        return dto;
    }

    public List<EmployeeDto> addMultipleEmployee(@Valid List<EmployeeDto> dtos) {
        List<Employee> employees = dtos.stream().map(this::mapToEntity).collect(Collectors.toList());
        List<Employee> saveAll = employeeRepository.saveAll(employees);
        List<EmployeeDto> collect = saveAll.stream().map(this::mapToDto).collect(Collectors.toList());
        return collect;
    }
}
