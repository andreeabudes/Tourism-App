package org.example;

import java.util.ArrayList;

public class Group implements Observer {
	private ArrayList<Person> members;
	private Professor guide;
	private Integer museumCode;
	private String timetable;

	public Group(Professor guide, Integer museumCode, String timetable) {
		this.members = new ArrayList<>();
		this.guide = guide;
		this.museumCode = museumCode;
		this.timetable = timetable;
	}
	public ArrayList<Person> getMembers() {
		return this.members;
	}
	public Professor getGuide() {
		return this.guide;
	}
	public Integer getMuseumCode() {
		return this.museumCode;
	}
	public String getTimetable() {
		return this.timetable;
	}
	public void setMembers(ArrayList<Person> members) {
		this.members = members;
	}
	public void setGuide(Professor guide){
		this.guide = guide;
	}
	public void setMuseumCode(Integer museumCode){
		this.museumCode = museumCode;
	}
	public void setTimetable(String timetable){
		this.timetable = timetable;
	}

	public void addMember(Person member) throws Exception {
		if (members.size() >= 10) {
			throw new Exception("GroupThresholdException: Group cannot have more than 10 members.");
		}
		members.add(member);
		System.out.println(museumCode + " ## " + timetable + " ## new member: " + member);
	}

	public void removeMember(Person member) throws Exception {
		if (!members.contains(member)) {
			throw new Exception("PersonNotExistsException: Person was not found in the group.");
		}
		members.remove(member);
		System.out.println(museumCode + " ## " + timetable + " ## removed member: " + member);
	}

	public void findMember(Person member) throws Exception {
		if (members.contains(member)) {
			System.out.println(museumCode + " ## " + timetable + " ## member found: " + member);
		} else {
			System.out.println(museumCode + " ## " + timetable + " ## member not exists: " + member);
		}
	}

	public void addGuide(Professor guide) throws Exception {
		if (this.guide != null) {
			throw new Exception("GuideExistsException: Guide already exists.");
		}
		if (!(guide instanceof Professor)) {
			throw new Exception("GuideTypeException: Guide must be a professor.");
		}
		this.guide = (Professor) guide;
		System.out.println(museumCode + " ## " + timetable + " ## new guide: " + guide);
	}

	public void findGuide(Person guide) throws Exception {
		if (!(guide instanceof Professor)) {
			throw new Exception("GuideTypeException: Guide must be a professor.");
		}
		if (this.guide == null) {
			throw new Exception("GroupNotExistsException: Group does not exist.");
		}
		if (this.guide.equals(guide)) {
			System.out.println(museumCode + " ## " + timetable + " ## guide found: " + guide);
		} else {
			System.out.println(museumCode + " ## " + timetable + " ## guide not exists: " + guide);
		}
	}

	public void removeGuide() throws Exception {
		if (this.guide == null) {
			throw new Exception("GroupNotExistsException: No guide exists.");
		}
		System.out.println(museumCode + " ## " + timetable + " ## removed guide: " + guide);
		this.guide = null;
	}

	public void update(String eventMessage) {
		if (guide != null) {
			System.out.println("Guide: " + guide.getName() + " " + eventMessage);
		}
	}
}
