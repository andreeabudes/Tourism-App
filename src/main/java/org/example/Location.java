package org.example;

public class Location {
	private String county;
	private String sirutaCode;

	// campuri optionale
	private String locality;
	private String adminUnit;
	private String address;
	private Integer latitude;
	private Integer longitude;

	public Location (LocationBuilder builder) {
		this.county = builder.county;
		this.sirutaCode = builder.sirutaCode;
		this.locality = builder.locality;
		this.adminUnit = builder.adminUnit;
		this.address = builder.address;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
	}

	public static class LocationBuilder {
		private String county;
		private String sirutaCode;
		private String locality;
		private String adminUnit;
		private String address;
		private Integer latitude;
		private Integer longitude;

		public LocationBuilder(String county, Integer sirutaCode) {
			this.county = county;
			this.sirutaCode = String.valueOf(sirutaCode);
		}
		public LocationBuilder locality(String locality) {
			this.locality = locality;
			return this;
		}
		public LocationBuilder adminUnit(String adminUnit) {
			this.adminUnit = adminUnit;
			return this;
		}
		public LocationBuilder address(String address) {
			this.address = address;
			return this;
		}
		public LocationBuilder latitude(Integer latitude) {
			this.latitude = latitude;
			return this;
		}
		public LocationBuilder longitude(Integer longitude) {
			this.longitude = longitude;
			return this;
		}
		public Location build() {
			return new Location(this);
		}
	}
}
