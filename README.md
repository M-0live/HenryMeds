# HenryMeds Reservation API

## Description
A simple reservation API that allows providers to submit availability and clients to book these reservations.

## Table of Contents
- [Tech Stack](#tech-stack)
- [How To Run](#how-to-run)
- [API](#api)
- [Time Constraints](#time-constraints)

## Tech Stack
The application was built using Java 17 and Spring Boot, with Gradle used to handle dependencies. Java was chosen for its familiarity, and Spring Boot and Spring Data for their efficiency in setting up projects with minimal boilerplate code and extensive functionality. An embedded H2 database is used for persistence, and Lombok is added to reduce boilerplate code with getters/setters and builder patterns.

## How To Run
Ensure Java 17 is installed.

To run the Spring Boot app, follow these steps:

1. Build the project using Gradle:

2. Navigate to the directory where the JAR file was generated:

3. Run the JAR file:

Alternatively, you can use your favorite IDE to load the project.

## API
The project features 4 APIs split into two categories: providers and clients.

1. **Create Availability API**:
- Accepts a JSON payload containing provider information, date, start time, and end time.
- Creates records in 15-minute increments based on the start and end time. This approach simplifies record fetching but may create unused records.

2. **Get Available Records API**:
- Retrieves all non-confirmed records for a given provider ID.

3. **Reservation API**:
- Retrieves a record based on the record ID created from the previous API.
- Performs validations, such as 24-hour advance and not within 30 minutes of a reservation.
- Sets reservation to true and tracks reservation time.

4. **Confirm Reservation API**:
- Similar to the Reservation API but confirms the reservation by setting confirm to true.

## Time Constraints
The application was designed to be simple and completed within a 2-3 hour time frame. In an ideal setting, handling expired records, introducing event-driven or async functionality for larger datasets, and adding testing would be considered. The project prioritizes simplicity and practicality, making it easy to follow and execute.

