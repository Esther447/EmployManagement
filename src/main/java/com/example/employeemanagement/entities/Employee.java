package com.example.employeemanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Employee entity represents an employee record in the company.")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the employee.", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the employee.", example = "Chantal")
    private String name;

    @Schema(description = "Position of the employee in the company.", example = "Developer")
    private String position;

    @Schema(description = "Department where the employee works.", example = "IT")
    private String department;

    @Schema(description = "Date when the employee was hired.", example = "2025-11-19")
    private LocalDate hireDate;
}
