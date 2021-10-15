# Hivemind Food Program

### How to start:
To get started, clone the repository from git and run the CommandInput.java file.  This file will prompt you for input.  You can add food and search for recipes (more feature coming soon!)

##Command list:

#### food:
Syntax:
> food add NAME QUANTITY UNIT (DAY MONTH YEAR)
> ~~~~~
> NAME: the name of the food
> QUANTITY: a double representing the amount of the food
> UNIT: a string containing the unit of the double
> DAY: optional expiry day of food
> MONTH: optional expiry month of food
> YEAR: optional expiry year of food
> 
> *NOTE* day, month and year must be either all not provided or provided

#### recipe:
Syntax:
> recipe recommend
> ~~~~~
> recommends recipes based on ingredients