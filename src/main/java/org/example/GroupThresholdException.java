package org.example;

public class GroupThresholdException extends Exception {
	public GroupThresholdException() {
		super("GroupThresholdException: Group cannot have more than 10 members.");
	}
}
