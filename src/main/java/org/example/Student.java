package org.example;

public class Student extends Person {
	private String school;
	private int studyYear;

	public Student(String surname, String name, String school, int studyYear) {
		super(surname, name, "student");
		this.school = school;
		this.studyYear = studyYear;
	}
	public String getSchool() {
		return this.school;
	}
	public int getStudyYear() {
		return this.studyYear;
	}
	public String toString() {
		return "surname=" + getSurname() + ", name=" + getName() + ", role=" + getRole() +
				", age=" + getAge() + ", email=" + getEmail() + ", school=" + school +
				", studyYear=" + studyYear;
	}
}
