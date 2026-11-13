
package com.example.employee.controller;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.employee.dto.EmployeeRequestDTO;
import com.example.employee.dto.EmployeeResponseDTO;
import com.example.employee.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private EmployeeResponseDTO getEmployee() {
        return new EmployeeResponseDTO(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                70000.0
        );
    }

    // 1. CREATE EMPLOYEE
    @Test
    void createEmployeeTest() throws Exception {

        when(employeeService.saveEmployee(any(EmployeeRequestDTO.class)))
                .thenReturn(getEmployee());

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Rahul",
                        "email": "rahul@gmail.com",
                        "department": "IT",
                        "salary": 70000
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.email").value("rahul@gmail.com"));

        verify(employeeService, times(1))
                .saveEmployee(any(EmployeeRequestDTO.class));
    }

    // 2. GET ALL EMPLOYEES
    @Test
    void getAllEmployeesTest() throws Exception {

        Page<EmployeeResponseDTO> page =
                new PageImpl<>(List.of(getEmployee()));

        when(employeeService.getAllEmployees(0, 10, "id", "asc"))
                .thenReturn(page);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Rahul"));
    }

    // 3. GET EMPLOYEE BY ID
    @Test
    void getEmployeeByIdTest() throws Exception {

        when(employeeService.getEmployeeById(1L))
                .thenReturn(getEmployee());

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"));
    }

    // 4. SEARCH BY DEPARTMENT
    @Test
    void getEmployeesByDepartmentTest() throws Exception {

        when(employeeService.getEmployeesByDepartment("IT"))
                .thenReturn(List.of(getEmployee()));

        mockMvc.perform(get("/employees/department/IT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].department").value("IT"));
    }

    // 5. SEARCH BY SALARY
    @Test
    void getEmployeesBySalaryTest() throws Exception {

        when(employeeService.getEmployeesBySalaryGreaterThan(50000.0))
                .thenReturn(List.of(getEmployee()));

        mockMvc.perform(get("/employees/salary/50000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].salary").value(70000.0));
    }

    // 6. SEARCH BY NAME
    @Test
    void searchEmployeesByNameTest() throws Exception {

        when(employeeService.searchEmployeesByName("Rahul"))
                .thenReturn(List.of(getEmployee()));

        mockMvc.perform(get("/employees/search")
                .param("name", "Rahul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rahul"));
    }

    // 7. UPDATE EMPLOYEE
    @Test
    void updateEmployeeTest() throws Exception {

        when(employeeService.updateEmployee(
                eq(1L), any(EmployeeRequestDTO.class)))
                .thenReturn(getEmployee());

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Rahul",
                        "email": "rahul@gmail.com",
                        "department": "IT",
                        "salary": 70000
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rahul"));
    }

    // 8. DELETE EMPLOYEE
    @Test
    void deleteEmployeeTest() throws Exception {

        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1))
                .deleteEmployee(1L);
    }

    // 9. VALIDATION TEST
    @Test
    void createEmployeeWithInvalidEmailTest() throws Exception {

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Rahul",
                        "email": "invalid-email",
                        "department": "IT",
                        "salary": 70000
                    }
                    """))
                .andExpect(status().isBadRequest());

        verify(employeeService, never())
                .saveEmployee(any(EmployeeRequestDTO.class));
    }
}
