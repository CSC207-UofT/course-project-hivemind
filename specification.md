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
Recipe Calculator - for finding viable recipes

Calender Manager - for tracking expiry dates and planning meals

## Controller Classes
AppController - Controls both recipe calculator and calender manager

## User Interface
Command line input - base interaction
(optional) GUI - easier input and interaction in an app-like environment 
