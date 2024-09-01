===============================
Donee Maintenance System
===============================
1. Create Donee
	- When entering the Register as a new donee choice, the user needs to input all the donee details such as donee name, address, phone number, email, donee type(Organization /  Individual), and also the organization name if applicable. 
	- After the new donee is registered, the donee details will be shown on screen, and return back to the main menu screen.
2. Remove donee
	- When entering the Remove Donee choice, the full donee list will be displayed on screen to let the user indicate which donee that want to be removed. The user need to input the correct done ID to perform the remove action. 
	- If the donee id is correct it will show the donee is being removed, if the donee id is incorrect it will show donee not found.
3. Update donee details
	- When entering Update Current Donee Details choice, it will show the full donee list and users need to input the correct donee ID that they wish to modify.
	- When the donee ID inputted is correct, it will enter a Update Donee Details Menu with the option to update name, address, phone number, email, donee type(Organization/Individual), organization name (if applicable), and also an exit option to complete the updating section.
	- The donee details will only be updated after the user chooses the Finish Updating choice.
4. Search Donee
	- When entering the Search Donee choice, users need to input the donee id that they want to search, if the donee id is found, it will display the donee details of the searched donee id.
	- It also allows the user to update these donee details if they wish, and a Update Donee Details Menu with the choice of updating name, address, phone number, email, donee type, organization name(if applicable) and also finish updating.
5. Applying Aid
	- When entering the Aid Menu, it allows the user to apply food aid, daily expenses aid, cash aid. And also the user can choose to display all the available donations that can be distributed to the donee.
	- If the user want to apply the aid, they need to select which type of aid they want to apply, then they need to input the donee ID, if the donee is found, then will proceed to the input amount they need (in RM), and the distribution details will be shown to the user.
	- If the input amount exceeds the total available amount, it will not be distributed.
6. Filter Donee
	- When selecting the Filter Donee choice, it will ask the user to enter the type of donee(Organization / Individual) to filter, the user can leave this empty if they don't want to apply the filter about donee type. And also asking the next filter, which is location, the user can input the location that they want to show (Eg : Kuala Lumpur) and it will apply the filter.
	- If both the filter is input, it will show the donee details that fulfill both of the filters.
	- If none of the filters is input, it will show all the donee details.
	- It also can work with either one of the filter is apply
7. Generate Report
	- When selecting the Generate Report choice, it will enter the Report Menu.
	- There are two types of summary report that the user can view, donee summary report and distribution summary report.
	- Donee summary report will show the total number of registered donee for each type(Organization and Individual) in a table, and also show the total of current registered donee. 
	- Distribution summary report will show two tables, the first tables is showing the donee , and total distributed amount of each type of aid that the specific donee received, and also the total distributed amount for each donee. The second tables will show the total distributed amount for each distribution type of the whole distribution list.
8. Undo Last Action
	- When the user selects the Undo Last Action choice, it will undo the last action(Add Donee ,Remove Donee,Update Donee Details).
	- If there is an action to undo, it will perform the undo and show the "Add command has been undo." if the last action is add action. It can be the add, remove or update depending on what is the last action.
	- If there is no action to undo, it will show "No actions to undo".
9. Redo Last Action
	- When the user selects the Redo Last Action choice, it will redo the last action that has been undo.
	- If there is an action to undo, it will perform the undo and show the "Add command has been redo." if the last undo action is add action. It can be the add, remove or update 	depending on what is the last undo action.
	- If there is no action to undo, it will show "No actions to redo".




Volunteer and Event Management System
Overview

The Volunteer and Event Management System is a Java-based application designed to streamline the management of volunteers and events. The system is built to handle various tasks, such as registering volunteers, assigning them to events, and maintaining a record of these activities. This program is particularly useful for organizations that rely heavily on volunteers and need an efficient way to manage them.

Key Features

Volunteer Management:
The VolunteerMaintenance class is responsible for managing all volunteer-related activities. It includes several key functionalities: 
Register New Volunteers: Collect essential details such as name, phone number, email, address, and experience level. Volunteers are assigned a unique ID and stored in VolunteerData.csv.
Delete Volunteers: Easily remove volunteers from the system by their ID. The system ensures data consistency by updating the CSV file upon deletion.
Search Volunteers by ID: Quickly retrieve and display detailed information about any volunteer using their unique ID.
Filter Volunteers by Experience: Display lists of volunteers filtered by their experience level (Experienced/Non-Experienced).
List All Volunteers: View all registered volunteers, complete with their respective details.
The VolunteerMaintenance class utilizes a custom linked list implementation (LinkedList class) to manage the volunteers. This ensures efficient insertion, deletion, and traversal of volunteer records. The class also interacts with file handling utilities to read and write CSV files, ensuring that data is persistently stored and easily retrievable.

Event Management:
The EventMaintenance class is responsible for managing events and the volunteers assigned to them. Key functionalities include:
Assign Volunteers to Events: Assign volunteers to specific events based on their ID and prevent duplicate assignments. The system records event data in Event.csv.
View Volunteers by Event: Retrieve a list of all volunteers assigned to a specific event by event name.
The EventMaintenance class also uses a linked list to manage the volunteers assigned to each event. It interacts with the VolunteerMaintenance class to ensure that only registered volunteers are assigned to events. The class provides mechanisms to handle potential errors, such as assigning volunteers who are already assigned to another event at the same time.

Reporting:

Generate Volunteer Reports: Create summary reports displaying the number of experienced and 
non-experienced volunteers within the system, providing insights into the volunteer pool.

Data Persistence:

CSV File Storage: All volunteer data and event assignments are stored in CSV files for easy access, backup, and editing. Files include VolunteerData.csv for volunteer information and Event.csv for event assignments.
Entities
Volunteer.java: Represents a volunteer, encapsulating attributes like ID, name, phone number, email, address, and experience level.
Event.java: Represents an event, storing event-specific data, including event ID, event name, and the assigned volunteers.
Data Access (DAO)
FileDao.java: This class handles reading and writing data to CSV files, ensuring data persistence.
Control
VolunteerMaintenance.java: Contains the core logic for managing volunteers and events, coordinating operations between the DAO and boundary classes.
User Interface (Boundary)
VolunteerMaintenanceUI.java: Provides a simple console interface for user interactions. Displays menus and options for managing volunteers and events.
Prerequisites
Before running the system, ensure you have the following installed:
Java Development Kit (JDK) 8 or later
Any Java-compatible IDE (e.g., Eclipse, IntelliJ IDEA)
CSV Libraries: The system leverages Java’s standard I/O libraries for reading and writing CSV files.

How to Run:
Compile and run the VolunteerMaintenance class in your Java IDE.
Follow the menu prompts to perform various operations, such as registering new volunteers, deleting existing volunteers, searching for volunteers, filtering volunteers, listing all volunteers, assigning volunteers to events, and generating summary reports.

How to Use:
Upon launching the application, you will be presented with the main menu that allows you to perform various operations. Below is a quick guide on how to interact with the system:
Main Menu Options
Register a New Volunteer: Enter the volunteer's details (name, phone, email, address, and experience level). A unique volunteer ID is generated, and the data is stored in the CSV file.
Delete a Volunteer by ID: Enter the ID of the volunteer you wish to delete. The system removes the volunteer from the in-memory list and updates the CSV file.
Search Volunteer by ID: Input the volunteer ID, and the system will return the volunteer's details if they are found in the CSV file.
Filter Volunteers by Experience: Choose whether to filter by "Experienced" or "Non-Experienced." The system will display a list of matching volunteers.
List All Volunteers: Shows a full list of all registered volunteers in the system, along with their details.
Assign Volunteer to an Event: Input the volunteer ID and event name to assign the volunteer to a specific event. The assignment is recorded in the Event.csv file.
View Volunteers by Event: Enter the event name, and the system will list all volunteers assigned to that event.
Generate Volunteer Report: Outputs a summary report showing the count of experienced and non-experienced volunteers.

Example Workflow
Scenario: Registering Volunteers and Assigning Them to Events


Step 1 : select the volunteer management
=====================================
       Volunteer Management Menu
=====================================
| 1. Register as New Volunteer       |
| 2. Remove Existing Volunteer       |
| 3. Search Volunteer                |
| 4. Filter Volunteers               |
| 5. List All Volunteers             |
| 6. Add Volunteer to Event          |
| 7. Search Volunteer from Event     |
| 8. Generate report                 |
| 0. Quit                            |
=====================================
Please enter your choice:
The user runs the application and is greeted with the volunteer management main menu.


Step 2: Register a New Volunteer
The user chooses option 1 to register a new volunteer. The system prompts the user for the volunteer's details.

=================================================================
||     Is the volunteer experienced? (Yes/No): Yes             ||
||     Enter your name: John Doe                               ||
||     Enter your phone number (10 or 11 digits): 5551234567   ||
||     Enter your email: john.doe@example.com                  ||
||     Enter your address: 123 Elm Street                      ||
||     Volunteer registered successfully!                      ||
=================================================================

The system registers the volunteer. 

The user registers a second volunteer 

=================================================================
||     Is the volunteer experienced? (Yes/No): No              ||
||     Enter your name: Jane Smith                             ||
||     Enter your phone number (10 or 11 digits): 5559876543   ||
||     Enter your email: jane.smith@example.com                ||
||     Enter your address: 456 Oak Avenue                      ||
||     Volunteer registered successfully!                      ||
=================================================================
The system validates the input and successfully registers the new volunteer.


Step 3: Remove an Existing Volunteer
The user wanted to remove a  volunteer from the file.
======================================================
||     Enter your Volunteer Id: V002                ||
||     Volunteer V002 has been deleted successfully.||
======================================================
A confirmation message Volunteer V002 has been deleted from the volunteer files.


Step 4: Search for a Volunteer
==============================================
||Enter the Volunteer ID to search: V001    ||
==============================================
The system displays the details of the volunteer with ID V001:
=====================================
           Volunteer Details
=====================================
Volunteer ID  : V001
Name          : John Doe
Phone Number  : 5551234567
Email         : john.doe@example.com
Address       : 123 Elm Street
=====================================


Step 5: Filter Volunteers
The user wants to filter volunteers by experience. They choose option 4.

-------------------------------------------------
         VOLUNTEER FILTER MENU
-------------------------------------------------
  1. Filter by Experienced Volunteers
  2. Filter by Non-Experienced Volunteers
-------------------------------------------------
Select an option (1 or 2): 1
The system displays a list of experienced volunteers.

If no found will give a message :
=====================================
||  No volunteers found for type:  ||
=====================================

If found will print the volunteer out :
=====================================
            Volunteers List
=====================================
Volunteer ID  : V001
Name          : John Doe
Phone Number  : 5551234567
Email         : john.doe@example.com
Address       : 123 Elm Street
-------------------------------------


Step 6: List All Volunteers
The user wants to view a list of all registered volunteers. They choose option 5.
=====================================
            Volunteers List
=====================================
Volunteer ID  : V001
Name          : John Doe
Phone Number  : 5551234567
Email         : john.doe@example.com
Address       : 123 Elm Street
-------------------------------------


Step 7: Add Volunteer to an Event
The user chooses option 6 to assign the registered volunteer to an event.

====================================================================
||     Enter Volunteer Id: V001                                    ||
||     Volunteer with ID V001 is already assigned to the event."   ||
=====================================================================


Step 8: Search Volunteer in an Event
The user wants to search for a volunteer within a specific event. They choose option 7.
The system confirms that the volunteer is participating in the specified event.

=====================================
           Event Participation
=====================================
Volunteer ID  : V001
Event ID      : E001
Event Name    : Community Clean-Up
=====================================


Step 9: Generate Report
The user wants to generate a summary report of all registered volunteers. They choose option 8 from the main menu.
-------------------------------------------------
         VOLUNTEER SUMMARY REPORT MENU
-------------------------------------------------
  1. Summary of Registered Volunteers
  2. View Volunteer Data File (VolunteerData.csv)
-------------------------------------------------
Select an option (1 or 2): 1

For option 1 : summary of registered volunteer it will calculate the total number of volunteers 

===========================================
       Volunteer Summary Report
==========================================|
| Volunteer Type     ||     Total Count   |
|-----------------------------------------|
| Experienced Volunteers      ||  0       |
| Non-Experienced Volunteers  ||  2       |
|-----------------------------||----------|
|Total Volunteers Registered  ||  2       |
===========================================
For option 2 : it will open the VolunteerData.csv file in the user personal computer

