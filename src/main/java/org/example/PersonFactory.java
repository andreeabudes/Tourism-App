package org.example;

public class PersonFactory {
	public Person createPerson(String surname, String name, String role, String school, int age, String email, int extraInfo) {
		Person person;
		if (role.equals("student")) {
			person = new Student(surname, name, school, extraInfo);
		} else if (role.equals("profesor")) {
			person = new Professor(surname, name, school, extraInfo);
		} else {
			throw new IllegalArgumentException("Invalid role");
		}

		person.setAge(age);
		person.setEmail(email);
		return person;
	}
}
