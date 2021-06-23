# Recipe Helper
Hugh Tietze

# Description
This project for 2545 Java Programming allows a user to search
for recipes by using their entered ingredients. These are checked
for validity then put together to create an API request url.
The first call brings back 15 recipes that contain the ingredients
that the user entered. One of these can be selected and another button
pressed in order to make two more API calls that will bring back
the other ingredients and the instructions. These are all displayed
in two GUIs.

# How to use it
 -- The program is set up in the pom.xml to use **Java 12**. --

To use this, first the API key must be set as an environment variable
This API key can be received by creating an account at spoonacular.com
The environment variable is named 'Spoonacular_API_Key'
There is a limit of 150 points worth of calls. Not too bad for all the info

Next enter in an ingredient or three into the field at the top of the main GUI.
Then clicking Search will send these ingredients to the API and the GUI will
display the results.

From here, selecting one of these recipes and clicking the Get selected recipe button
will again ask the API but this time open another GUI which will display this split information

# bugs..
I'm. not sure there are any I know of right now. I fixed the ones that got mentioned in the
code review! It used to not clear the listed recipes.
I just now realized that it can open a whole lot of GUIs by selecting more recipes..
Ok

* Bug: Opening multiple secondary GUIs by clicking the Get Selected Recipe button
will cause the other GUIs to break. These GUIs become unresponsive. 

I don't know much about
dealing with second GUIs.
The fix would be to somehow check if it's running first I think? Not sure how to do this.
