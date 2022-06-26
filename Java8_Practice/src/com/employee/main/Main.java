package com.employee.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.employee.entity.Employee;

public class Main {
 public static void main(String[] args) {
	List<Employee> employeesList = Arrays.asList(new Employee(12,"sarath",25000),
			new Employee(9,"vamsi",89000),
			new Employee(1,"sabari",29000),
			new Employee(19,"srinath",18000),
			new Employee(5,"monica",85000));
	
	//sorting list based on salary by using java8 streams
	List<Employee> sortedlist= employeesList.stream().sorted((a,b) -> (b.getSalary()-a.getSalary())).collect(Collectors.toList());
	System.out.println(sortedlist);
	
	//third largest salary
	System.out.println(employeesList.stream().sorted((a,b) -> (b.getSalary()-a.getSalary())).skip(2).findFirst().get());

 }
 
 
}
