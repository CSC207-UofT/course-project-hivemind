## Specification
Every food has a name, a quantity, and a measurement. The user specifies which foods they have, and whether they are perishable or non perishable. Perishable foods have an expiry status and date. The system keeps track of the user’s foods, and keeps track of the expiry date and status of perishable foods. Upon expiring, the system alerts the user of the expired status of the food.

Alongside foods are recipes, which specify which foods qualify as ingredients, and the measurements and quantities of these ingredients needed. The system keeps track of the user’s foods and suggests recipes based on the availability and expiry status of the ingredients. These recipes are ranked and return back to the user, who is free to select which one to use.

## Requirements: A reasonable CRC model that satisfies your specification should consist of:
- at least three entity classes,
- at least two use case classes,
- at least one controller,
- and at least a basic command line interface!

## Entity Classes:
Food (abstract):
- Perishable - tracks food with things like due date
- Non-perishable

Recipe - stores needed ingredients and instructions

## Use Case Classes:
RecipeCalculator - for finding viable recipes
Calendar - for tracking expiry dates and planning meals

## Controller Classes
RecipeController - Manages stored data and store data when needed from the data source of all recipes 

CalendarController - Manages stored data and store data when needed from the data source of Calendars and expiry dates

## User Interface
Command line input - base interaction

(optional) GUI - easier input and interaction in an app-like environment 
