package org.example;

public class GroupNotExistsException extends Exception {
	public GroupNotExistsException() {
		super("GroupNotExistsException: Group does not exist.");
	}
}
