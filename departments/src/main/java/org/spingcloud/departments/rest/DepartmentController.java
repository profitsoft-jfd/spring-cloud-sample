package org.spingcloud.departments.rest;


import org.spingcloud.departments.model.Department;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

  @GetMapping(value = "/{departmentId}")
  public Department getDepartment(@PathVariable String departmentId) {
    return new Department(departmentId, "name" + departmentId);
  }

}
