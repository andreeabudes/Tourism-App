This project involves the development of the back-end of a tourism application. It allows users to create a database of museums and manage tourist groups, while also implementing various 
design patterns such as Singleton, Builder, and Observer.

Features:
  
  Museum Management: Add and manage museums, including their details (name, location, contact information, etc.).
  
  Group Management: Create and manage tourist groups with members, guides, and schedules.
  
  Exception Handling: Various exceptions like GroupNotExistsException, GuideExistsException, etc., are implemented to handle edge cases.

Classes Implemented:

  Database: Singleton pattern for managing museum and group data.
  
  Museum: Uses Builder and Observer patterns for creating and notifying museums about events.

  Group: Handles group membership, adding/removing guides, and receiving notifications.
  
  Person/Student/Professor: Define the people involved in the system (tourist guides, group members).
  
  Location: Location details for museums, implemented with the Builder pattern.
  
  Exceptions: Handling errors with custom exceptions.
  
  PathTypes: Enum for categorizing types of data (groups, museums, listeners).
