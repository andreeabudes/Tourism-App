package org.example;

import java.util.ArrayList;

public class Museum implements Subject {
	private String name;
	private long code;
	private long supervisorCode;
	private Location location;

	// parametri optionali
	private Person manager;
	private Integer foundingYear;
	private String phoneNumber;
	private String fax;
	private String email;
	private String url;
	private String profile;

	private ArrayList<Observer> observers = new ArrayList<>();

	public Museum(MuseumBuilder builder) {
		this.name = builder.name;
		this.code = builder.code;
		this.supervisorCode = builder.supervisorCode;
		this.location = builder.location;
		this.manager = builder.manager;
		this.foundingYear = builder.foundingYear;
		this.phoneNumber = builder.phoneNumber;
		this.fax = builder.fax;
		this.email = builder.email;
		this.url = builder.url;
		this.profile = builder.profile;
		this.observers = new ArrayList<>();
	}

	// observer pattern
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	public void notifyObservers(String eventMessge) {
		for (Observer observer : observers) {
			observer.update(eventMessge);
		}
	}
	public String getName() {
		return this.name;
	}
	public long getCode() {
		return this.code;
	}
	public long getSupervisorCode() {
		return this.supervisorCode;
	}
	public Location getLocation() {
		return this.location;
	}
	public Person getManager() {
		return this.manager;
	}
	public Integer getFoundingYear() {
		return this.foundingYear;
	}

	public ArrayList<Observer> getObservers() {
		return this.observers;
	}

	public void addEvent(String organizerMessage) {
		String eventMessage = "To: " + this.manager.getEmail() +
				" ## Message: " + this.name + " (" + this.code + ") " + organizerMessage;
		notifyObservers(eventMessage);
	}

	public static class MuseumBuilder {
		private String name;
		private long code;
		private long supervisorCode;
		private Location location;
		private Person manager;
		private Integer foundingYear;
		private String phoneNumber;
		private String fax;
		private String email;
		private String url;
		private String profile;

		public MuseumBuilder(String name, long code, long supervisorCode, Location location) {
			this.name = name;
			this.code = code;
			this.supervisorCode = supervisorCode;
			this.location = location;
		}
		public MuseumBuilder setManager(Person manager) {
			this.manager = manager;
			return this;
		}
		public MuseumBuilder setFoundingYear(Integer foundingYear) {
			this.foundingYear = foundingYear;
			return this;
		}
		public MuseumBuilder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		public MuseumBuilder setFax(String fax) {
			this.fax = fax;
			return this;
		}
		public MuseumBuilder setEmail(String email) {
			this.email = email;
			return this;
		}
		public MuseumBuilder setUrl(String url) {
			this.url = url;
			return this;
		}
		public MuseumBuilder setProfile(String profile) {
			this.profile = profile;
			return this;
		}
		public Museum build() {
			return new Museum(this);
		}
	}
}
