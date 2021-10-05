## Requirements: A reasonable CRC model that satisfies your specification should consist of:
- at least three entity classes,
- at least two use case classes,
- at least one controller,
- and at least a basic command line interface.

## Entity Classes:
Food (abstract):
- Perishable - tracks food with things like due date
- Non-perishable

Recipe - stores needed ingredients and instructions

## Use Case Classes:
RecipeCalculator - for finding viable recipes

CalenderManager - for tracking expiry dates and planning meals

## Controller Classes
RecipeController - Manages stored data and store data when needed from the data source of all recipes 

CalendarController - Manages stored data and store data when needed from the data source of Calendars and expiry dates

## User Interface
Command line input - base interaction

(optional) GUI - easier input and interaction in an app-like environment 
