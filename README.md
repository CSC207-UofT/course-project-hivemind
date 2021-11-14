# Hivemind Food Program

### How to start:
To get started, clone the repository from git and run the CommandInput.java file.  This file will prompt you for input.  You can add food and search for recipes (more feature coming soon!)

## Command list:

#### food:
This is for commands relating to food

Syntax:
> food add NAME QUANTITY UNIT (YEAR MONTH DAY)
> ~~~~~
> NAME: the name of the food
> QUANTITY: a double representing the amount of the food
> UNIT: a string containing the unit of the double
> YEAR: optional double expiry year of food
> MONTH: optional double expiry month of food
> DAY: optional double expiry day of food
>
> *NOTE* day, month and year must be either all not provided or provided
>food delete
> ~~~~~
> *NOTE* deletes food from the system

> ~~~~~
> food check 
> ~~~~
> Checks for expired foods in inventory. 

#### recipe:
This is for commands relating to recipe

Syntax:
> recipe search
> ~~~~~
> *NOTE* recommends recipes based on ingredients

#### program:
This is for commands relating to the program in general

Syntax:
> program exit
> ~~~~~
