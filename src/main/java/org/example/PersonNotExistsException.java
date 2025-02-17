package org.example;

public class PersonNotExistsException extends Exception {
	public PersonNotExistsException() {
		super("PersonNotExistsException: Person was not found in the group.");
	}
}
