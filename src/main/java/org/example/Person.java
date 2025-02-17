package org.example;

public class Person {
	private String surname;
	private String name;
	private String role;
	private int age;
	private String email;

	public Person(String surname, String name, String role) {
		this.surname = surname;
		this.name = name;
		this.role = role;
	}
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString() {
		return "surname=" + surname + ", name=" + name + ", role=" + role +
				", age=" + age + ", email=" + email;
	}
}
