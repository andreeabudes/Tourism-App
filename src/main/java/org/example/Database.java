package org.example;

import java.util.HashSet;
import java.util.Set;

public class Database {
	private static Database database;
	private Set<Museum> museums = new HashSet<>();
	private Set<Group> groups = new HashSet<>();

	// folosesc singleton
	private Database() {}
	public static Database getDatabase() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}
	public static Set<Museum> getMuseums() {
		return getDatabase().museums;
	}
	public static Set<Group> getGroups() {
		return getDatabase().groups;
	}
	public void addMuseum(Museum museum) {
		getDatabase().museums.add(museum);
	}
	public void addMuseums(Set<Museum> museums) {
		getDatabase().museums.addAll(museums);
	}
	public void addGroup(Group group) {
		getDatabase().groups.add(group);
	}
	public void addGroups(Set<Group> groups) {
		getDatabase().groups.addAll(groups);
	}
	// gasesc un grup dupa muzeu
	public Group getGroupByMuseumCode(Integer museumCode) {
		for (Group group : groups) {
			if (group.getMuseumCode().equals(museumCode)) {
				return group;
			}
		}
		return null;
	}
}
