package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.example.PathTypes.getPathType;

public class Main {
    static Database database = Database.getDatabase();

    public static void main(String[] args) {
        if (args.length == 2) {
            PathTypes pathType = getPathType(args[0]);
            String filePath = args[1];

            switch (pathType) {
                case MUSEUMS:
                    processMuseums(filePath);
                    break;

                case GROUPS:
                    processGroups(filePath);
                    break;

					default:
            }
        } else if (args.length == 4) {
            PathTypes pathType = PathTypes.valueOf(args[0]);
            String museumPath = args[1];
            String groupPath = args[2];

            processMuseums(museumPath);
            processGroups(groupPath);
        }
    }

    private static void processMuseums(String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + ".out");
            PrintStream printStream = new PrintStream(fileOutputStream);

            Scanner scanner = new Scanner(new File(filePath + ".in"));
            String params = scanner.nextLine();
            String line = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] tokens = line.split("\\|");
                if (tokens.length <= 19) {
                    printStream.println("Exception: Data is broken. ## (" + line + ")");
                    continue;
                }
                if(tokens[0].equals("ADD MUSEUM")) {
                    try {
                        String county = tokens[3];
                        String sirutaCodeStr = tokens[7];
                        String cleanLatitude = tokens[18].replaceAll("[,]", "").replaceFirst("(\\d{2})(\\d+)", "$1.$2");
                        String cleanLongitude = tokens[19].replaceAll("[,]", "").replaceFirst("(\\d{2})(\\d+)", "$1.$2");

						// parsez coordonatele
                        int latitude = (int) Math.round(Double.parseDouble(cleanLatitude));
                        int longitude = (int) Math.round(Double.parseDouble(cleanLongitude));
                        Integer sirutaCode = Integer.parseInt(sirutaCodeStr);

                        // creez locatia si muzeul
                        Location location = new Location.LocationBuilder(county, sirutaCode)
                                .locality(tokens[4])
                                .adminUnit(tokens[5])
                                .address(tokens[6])
                                .latitude(latitude)
                                .longitude(longitude)
                                .build();

                        Museum museum = new Museum.MuseumBuilder(tokens[2], Long.parseLong(tokens[1]), Long.parseLong(tokens[14]), location)
                                .setFoundingYear(tokens[10].isEmpty() ? null : Integer.parseInt(tokens[10]))
                                .setPhoneNumber(tokens[7])
                                .setFax(tokens[8])
                                .setEmail(tokens[11])
                                .setUrl(tokens[12])
                                .setProfile(tokens[15])
                                .build();

                        database.addMuseum(museum);
                        printStream.println(museum.getCode() + ": " + museum.getName());

                    } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
                        printStream.println("Exception: Data is broken. ## (" + line + ")");
                        e.printStackTrace();
                    }
                }
            }
            scanner.close();
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	private static void processGroups(String filePath) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath + ".out");
			PrintStream printStream = new PrintStream(fileOutputStream);
			Scanner scanner = new Scanner(new File(filePath + ".in"));

			ArrayList<Group> groups = new ArrayList<>();
			String line = scanner.nextLine();

			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				String[] tokens = line.split("\\|");

				if (tokens.length < 11) {
					printStream.println("Exception: Data is broken. ## (" + line + ")");
					continue;
				}

				String command = tokens[0];
				String surname = tokens[1];
				String name = tokens[2];
				String title = tokens[3];
				int age = Integer.parseInt(tokens[4]);
				String email = tokens[5].isEmpty() ? "null" : tokens[5];
				String school = tokens[6];
				int studyYearOrExperience = Integer.parseInt(tokens[7]);
				String role = tokens[8];
				Integer museumCode = Integer.parseInt(tokens[9]);
				String timetable = tokens[10];

				// caut grupul corespunzator codului muzeului si orei
				Group group = null;
				for (Group g : groups) {
					if (g.getMuseumCode().equals(museumCode) && g.getTimetable().equals(timetable)) {
						group = g;
						break;
					}
				}

				if (command.equals("ADD GUIDE")) {
					try {
						if (!title.equals("profesor")) {
							throw new Exception("GuideTypeException: Guide must be a professor.");
						}

						Professor guide = new Professor(surname, name, school, studyYearOrExperience);
						guide.setAge(age);
						guide.setEmail(email);

						// daca nu exista grupul, il creez si adaug ghidul
						if (group == null) {
							group = new Group(guide, museumCode, timetable);
							groups.add(group);
							printStream.println(museumCode + " ## " + timetable + " ## new guide: surname=" + surname +
									", name=" + name + ", role=ghid, age=" + age + ", email=" + email +
									", school=" + school + ", experience=" + studyYearOrExperience);
						} else {
							// verific daca ghidul exista deja
							if (group.getGuide() != null) {
								throw new Exception("GuideExistsException: Guide already exists.");
							} else {
								group.addGuide(guide);
								printStream.println(museumCode + " ## " + timetable + " ## new guide: surname=" + surname +
										", name=" + name + ", role=ghid, age=" + age + ", email=" + email +
										", school=" + school + ", experience=" + studyYearOrExperience);
							}
						}

					} catch (Exception e) {
						if (e.getMessage().contains("GuideExistsException")) {
							Professor existingGuide = group.getGuide();
							printStream.println(museumCode + " ## " + timetable + " ## GuideExistsException: Guide already exists. ## (new guide: surname=" +
									existingGuide.getSurname() + ", name=" + existingGuide.getName() + ", role=ghid, age=" +
									existingGuide.getAge() + ", email=" + existingGuide.getEmail() + ", school=" +
									existingGuide.getSchool() + ", experience=" + existingGuide.getExperience() + ")");
						} else if (e.getMessage().contains("GuideTypeException")) {
							printStream.println(museumCode + " ## " + timetable + " ## GuideTypeException: Guide must be a professor. ## (new guide: surname=" +
									surname + ", name=" + name + ", role=" + role + ", age=" + age + ", email=" + email +
									", school=" + school + ", studyYear=" + studyYearOrExperience + ")");
						} else {
							printStream.println(museumCode + " ## " + timetable + " ## " + e.getMessage());
						}
					}

				} else if (command.equals("REMOVE GUIDE")) {
					if (group == null || group.getGuide() == null) {
						printStream.println(museumCode + " ## " + timetable + " ## GroupNotExistsException: No guide exists. ## (removed guide: surname=" +
								surname + ", name=" + name + ")");
						continue;
					}
					try {
						group.removeGuide();
						printStream.println(museumCode + " ## " + timetable + " ## removed guide: surname=" + surname +
								", name=" + name + ", role=ghid, age=" + age + ", email=" + email +
								", school=" + school + ", experience=" + studyYearOrExperience);
					} catch (Exception e) {
						printStream.println(museumCode + " ## " + timetable + " ## " + e.getMessage() +
								" ## (removed guide: surname=" + surname + ", name=" + name + ")");
					}

				}  else if (command.equals("FIND GUIDE")) {
					try {
						Person personToFind = new Professor(surname, name, school, studyYearOrExperience);
						if (group != null) {
							group.findGuide(personToFind);
						} else {
							printStream.println(museumCode + " ## " + timetable + " ## GroupNotExistsException: Group does not exist. ## (find guide: surname=" +
									surname + ", name=" + name + ")");
						}
					} catch (Exception e) {
						printStream.println(museumCode + " ## " + timetable + " ## " + e.getMessage() +
								" ## (find guide: surname=" + surname + ", name=" + name + ")");
				}

			}
				else if (command.equals("ADD MEMBER")) {
					// daca grupul nu exista
					if (group == null) {
						String additionalInfo = "";
						// pt student
						if (title.equals("student")) {
							additionalInfo = ", studyYear=" + studyYearOrExperience;
						}
						// pt profersor
						else if (title.equals("profesor")) {
							additionalInfo = ", experience=" + studyYearOrExperience;
						}

						printStream.println(museumCode + " ## " + timetable + " ## " + "GroupNotExistsException: Group does not exist. ## (" +
								"new member: " + "surname=" + surname +", name=" + name + ", role=" + role + ", age=" +
								age + ", email=" + email + ", school=" + school + additionalInfo + ")");
						continue;
					}

					try {
						PersonFactory personFactory = new PersonFactory();
						Person member = personFactory.createPerson(surname, name, title, school, age, email, studyYearOrExperience);
						group.addMember(member);

						String extraInfo = title.equals("profesor") ?
								", experience=" + studyYearOrExperience :
								", studyYear=" + studyYearOrExperience;

						printStream.println(museumCode + " ## " + timetable + " ## new member: surname=" + surname +
								", name=" + name + ", role=" + role + ", age=" + age + ", email=" + email +
								", school=" + school + extraInfo);

					} catch (Exception e) {
						String additionalInfo = "";
						// pt student
						if (title.equals("student")) {
							additionalInfo = ", studyYear=" + studyYearOrExperience;
						}
						// pt profersor
						else if (title.equals("profesor")) {
							additionalInfo = ", experience=" + studyYearOrExperience;
						}
						printStream.println(museumCode + " ## " + timetable + " ## " + e.getMessage() +
								" ## (new member: surname=" + surname + ", name=" + name + ", role=" + role + ", age=" + age +
								", email=" + email + ", school=" + school + additionalInfo + ")");
					}

				} else if (command.equals("REMOVE MEMBER")) {
					if (group == null) {
						printStream.println(museumCode + " ## " + timetable + " ## " + "GroupNotExistsException: Group does not exist. ## (" +
								"removed member: " + "surname=" + surname +", name=" + name + ", role=" + role + ", age=" +
								age + ", email=" + email + ", school=" + school + ", experience=" + studyYearOrExperience + ")");
						continue;
					}

					try {
						Person member = null;
						for (Person p : group.getMembers()) {
							if (p.getSurname().equals(surname) && p.getName().equals(name)) {
								member = p;
								break;
							}
						}

						if (member == null) {
							throw new Exception("PersonNotExistsException: Person was not found in the group.");
						}

						group.removeMember(member);

						String extraInfo = (member instanceof Professor) ?
								", experience=" + ((Professor) member).getExperience() :
								", studyYear=" + (member instanceof Student ? ((Student) member).getStudyYear() : "");

						printStream.println(museumCode + " ## " + timetable + " ## removed member: surname=" + surname +
								", name=" + name + ", role=" + role + ", age=" + age + ", email=" + email +
								", school=" + school + extraInfo);

					} catch (Exception e) {
						printStream.println(museumCode + " ## " + timetable + " ## " + "PersonNotExistsException: Person was not found in the group. ## (" +
								"surname=" + surname +", name=" + name + ", role=" + role + ", age=" + age + ", email=" + email +
								", school=" + school + ", experience=" + studyYearOrExperience + ")");
					}

				} else if (command.equals("FIND MEMBER")) {
					try {
						Person personToFind = new Person(surname, name, role);

						if (group != null) {
							group.findMember(personToFind);
						} else {
							printStream.println(museumCode + " ## " + timetable + " ## GroupNotExistsException: Group does not exist. ## (find member: surname=" +
									surname + ", name=" + name + ")");
						}
					} catch (Exception e) {
						printStream.println(museumCode + " ## " + timetable + " ## " + e.getMessage() +
								" ## (find member: surname=" + surname + ", name=" + name + ")");
					}
				}
			}
			scanner.close();
			printStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
