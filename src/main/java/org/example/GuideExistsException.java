package org.example;

public class GuideExistsException extends Exception {
	public GuideExistsException() {
		super("GuideExistsException: Guide already exists.");
	}
}
