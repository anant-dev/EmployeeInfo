/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.beans.Employee;
import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anants
 */
// use rest controller and security in the application
// also add a service layer
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    JdbcTemplate template;

    public EmployeeDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveEmp(Employee emp) {
        String sql = "INSERT into employee_details(emp_id,name,salary,designation,domain) "
                + "values(?,?,?,?,?)";
        return template.update(sql, new Object[]{emp.getEmp_id(), emp.getName(), emp.getSalary(), emp.getDesignation(), emp.getDomain()});
    }

    @Override
    public int updateEmp(Employee emp) {
        String sql = "update employee_details set name = ? ,salary = ? ,designation = ? ,domain = ?  where id= ?";
        return template.update(sql, new Object[]{emp.getName(), emp.getSalary(), emp.getDesignation(), emp.getDomain(), emp.getId()});
    }

    @Override
    public int deleteEmp(int emp_id) {
        String sql = "delete from employee_details where id= ?";
        return template.update(sql, new Object[]{emp_id});
    }

    @Override
    public Employee getEmpById(int id) {
        String sql = "select * from employee_details where id=?";
        return template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Employee>(Employee.class));
    }

    @Override
    public List<Employee> getEmployees() {
        String sql = "select * from employee_details";
        List<Employee> emp = template.query(sql, new EmployeeMapper());
        return emp;
    }

    private static final class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(java.sql.ResultSet rs, int i) throws SQLException {
            Employee e = new Employee();
            e.setId(rs.getInt("id"));
            e.setEmp_id(rs.getInt("emp_id"));
            e.setName(rs.getString("name"));
            e.setSalary(rs.getFloat("salary"));
            e.setDesignation(rs.getString("designation"));
            e.setDomain(rs.getString("domain"));
            return e;
        }
    }

}
