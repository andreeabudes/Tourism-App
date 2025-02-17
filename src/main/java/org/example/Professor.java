package org.example;

public class Professor extends Person {
	private int experience;
	private String school;

	public Professor(String surname, String name, String school, int experience) {
		super(surname, name, "profesor");
		this.experience = experience;
		this.school = school;
	}
	public int getExperience() {
		return this.experience;
	}
	public String getSchool() {
		return this.school;
	}
	@Override
	public String toString() {
		return "surname=" + getSurname() + ", name=" + getName() + ", role=" + getRole() +
				", age=" + getAge() + ", email=" + getEmail() + ", school=" + school +
				", experience=" + experience;
	}
}
