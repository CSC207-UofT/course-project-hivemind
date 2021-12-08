# CSC207 Team Hivemind Project: Sous-Chef
#### By: Carmel Tung, Kai Wen Tan, Maggie Chen, Mark Oh, Nathan Wootton and Yvonne Chen 
### Overview:
The Sous-Chef app is a kitchen helper app that can keep track of foods, expiry dates, and recommend you recipes based on the foods you have.  We currently have two versions of application, one is an interactive console that can run on your computer, and the other is an Android App, supported by most Android phones.  The interactive command line is located on the ['main'](https://github.com/CSC207-UofT/course-project-hivemind/tree/main) branch, while the app is located on the ['android'](https://github.com/CSC207-UofT/course-project-hivemind/tree/android) branch.  Below is a guide to get you started on either version.

## Command Line Guide:

When the command line starts up, you will be met with several options for commands

> > food add
> ~~~~~
> Opens a dialogue to create a food
> Name: the name of the food
> Quantity: a double representing the amount of the food
> Unit: a string containing the unit of the double
> Expiry Date: a string formatted as "YYYY/MM/DD" (Optional)

> > food delete
> ~~~~~
> Opens a dialogue to delete
> Name: the name of the food
> Number: in the case of multiple foods with the same name, choose which to delete

> > food check
> ~~~~~
> Prints out every expired food saved in the program

> > food get
> ~~~~~
> Prints out every food saved in the program

> > recipe search
> ~~~~~
> Recommends recipes based on ingredients

> > recipe add
> ~~~~~
> Opens a dialog to add a recipe
> Name: the name of the recipe
> Ingredients: a string containing triplets of foods that are required in the recipe, in the form of "Ingredient1 Quantity1 Unit1 Ingredient2 Quantity2 Unit2...."
> Instructions: a string containing the instructions of the program

> > program help
> ~~~~~
> Prints out a help message containing all commands

> > program exit
> ~~~~~
> Exits the program

## Android App Guide:

When the Android App starts up, you will be met with a list of your foods.  At the bottom, there is a navigation bar between Foods, Recipes and Settings.
#### Foods:
In the foods menu, you can see a scrollable list of all the foods you have entered into the program.  Clicking on a food will bring up a menu to delete it.  To add a new food, simply tap the plus button in the bottom right and fill out the dialog to add the food.
#### Recipes:
In the recipes menu, you can see a scrollable list of all the recipes in the app, similar to the food menu.  To expand a recipe, simply tap on the name of it and a popup will come up with information about the recipe, and its instructions.  To add a recipe, click on the plus button in the bottom right corner and fill out the dialog to add the recipe.
#### Settings:
In the settings menu, you can change the font size, as well as the number of recipes recommended by adjusting the sliders as you see fit.

#### Trouble Fixing:
If a box appears saying 'System UI isn't working', this is an issue with your emulator and press 'wait'.
If the app is crashing, uninstall and freshly install the app (there was likely an error with installation or app storage).
