# Project Information 
    Author: Luke Clement
    Last updated: 12/3/23

# Description
    The purpose of this project was to make a GUI for an SQL database. Each person had to design a GUI for their portion of the 
    database. My interface interacted with locations. Every location has an ID, name, size, type, and exits. A location can be added, modified, 
    or deleted. In it's current state, the GUI (Locations_UI.py) can be displayed but has not functionality unless you connect it to your own
    database. Group_Create_Tables.py can be used to generate all the necessary table for Locations_UI.py to run. Note that you need to change
    the variables in mysql.connecter throughout both files in order to properly interface with your own database.

# Run
    Create and populate Tables: python3 Group_Create_Tables.py
    Locations GUI: python3 Locations_UI.py