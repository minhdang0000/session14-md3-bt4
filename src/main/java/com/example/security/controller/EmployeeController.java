package com.example.security.controller;

import com.example.security.dto.EmployeeCreateDTO;
import com.example.security.entity.Employee;
import com.example.security.repository.EmployeeRepository;
import com.example.security.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "Nguyễn Công Hưởng", 5000.0),
                new Employee(2L, "Phạm Tuấn Bình", 5000.0),
                new Employee(3L, "Nguyễn Văn A", 5000.0)
        );
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@ModelAttribute EmployeeCreateDTO employeeDTO) {
        try {
            String avatarUrl = cloudinaryService.uploadImage(employeeDTO.getAvatarFile());

            Employee employee = new Employee();
            employee.setFullName(employeeDTO.getFullName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setAvatarUrl(avatarUrl);

            Employee savedEmployee = employeeRepository.save(employee);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
