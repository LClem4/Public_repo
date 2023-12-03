# Author Luke Clement
# Imports
from os.path import exists
import os
import re

# Global variables
action = "" # action
active = True # Loop the main action
entry_data = ["10:00","10","Steve"] # Time, Age, and Name
lineChange = 20 # characters per line (modified later)
action_format = r'edit\s([0-9]|[1-9][0-9]|[1-9][0-9][0-9])' # formatting for edit
data_view = "Nice" # Formatting of how the data looks in the textfile
                   # The other mode that can be used is compact
space = "" # Var for spacing

# Creates file if there isn't one
def create_file():
    with open('fun.dat', 'w') as file:
        global space
        if(data_view == "Nice"): # Nice view
            space = " "
            file.write("Time  Age Name       \n")
        else:                   # Compact/other view for data
            space = ""
        file.close()

# Checks input format for time
def format_check_time(time): 
    time_format = r'^(2[0-3]|[0-1][0-9]):([0-5][0-9])$' # Checks time input format
    time_check = re.fullmatch(time_format, time) != None
    if(time_check == False): # Checks if there is a formatting error
        return "Error: time formatting"
    else:
        entry_data[0] = time
        return "Recorded"

# Checks input format for age
def format_check_age(age): 
    age_format = r'^([0-9]|[1-9][0-9]|[1-9][0-9][0-9])' # Checks age input format
    age_check = re.fullmatch(age_format, age) != None
    while(len(age) < 3): # add spaces if < 3
        age = age+" "
    if(len(entry_data[1]) > 3): # truncate
        age = age[:3]
    if(age_check == False): # Checks if there is a formatting error
        return "Error: age formatting"
    else:
        entry_data[1] = age
        return "Recorded"

# Checks input format for name
def format_check_name(name):
    name_format = r'[a-zA-Z]+' # Checks name input format
    name_check = re.fullmatch(name_format, name) != None
    while(len(name) < 10): # add spaces if < 10
        name = name+" "
    if(len(name) > 10): # truncate
        name = name[:10]
    if(name_check == False): # Checks if there is a formatting error
        return "Error: name formatting"
    else:
        entry_data[2] = name
        return "Recorded"

# Finds location of file pointer
def file_pointer(entry):
    return (lineChange+len(space)*3)*(entry-1+len(space)) 

# Adds entry to the data
def add_entry(data,entry,timeUpdate,ageUpdate,nameUpdate):
    if(entry > 0): # for writing to an entry
        with open('fun.dat', 'r+') as file:
            entry_pointer = file_pointer(entry) # Goes to line of entry
            file.seek(entry_pointer,0) # sets pointer to line
            if(timeUpdate == True): # Write time
                file.write(data[0]+space)
            if(ageUpdate == True): # Write age
                file.seek(entry_pointer+5+len(space),0)
                file.write(data[1]+space)
            if(nameUpdate == True): # Write name
                file.seek(entry_pointer+8+len(space)*2,0)
                file.write(data[2])
            file.close()
    elif(entry == 0): # for adding new entry
        with open('fun.dat', 'a') as file:
            file.seek(0,2)
            for i in data: # Writes new entry
                file.write(i+space)
            file.write('\n')
            file.close()
    
# Retrieve Data by entry
def retrieve_data(entry):
    data = ["","",""] # Where entry is stored
    with open('fun.dat', 'r') as file:
        entry_pointer = file_pointer(entry) # Goes to line of entry
        file.seek(entry_pointer,0) # sets pointer to line
        data[0] = file.read(5+len(space)) # reads time
        data[1] = file.read(3+len(space)) # reads age
        data[2] = file.read(10) # reads name
        file.close()
    return data

# Checks if entry exists
def check_entry(entry):
    entry_pointer = file_pointer(entry) # Where the pointer would be
    filesize = os.path.getsize('fun.dat') # file size
    if(filesize > entry_pointer):
        return True
    else:
        return False

# Prints given entry
def print_entry(entry):
    check = check_entry(entry) 
    if(check == True): 
        data = retrieve_data(entry)
        print("Time: " + str(data[0])+" ")
        print("Entry: " + str(data[1])+" ")
        print("Name: " + str(data[2])+" ")

# adds new entry
def new_entry():
    timeData = ""
    ageData = ""
    nameData = ""

    while(format_check_time(timeData) != "Recorded"): # Asks for time
        timeData = input("Enter: Time\n")

    while(format_check_age(ageData) != "Recorded"): # Asks for age
        ageData = input("Enter: Age\n")

    while(format_check_name(nameData) != "Recorded"): # Asks for name
        nameData = input("Enter: Name\n")

    add_entry(entry_data,0,True,True,True)

# edits old entry
def edit_entry():
    # variables for setting up edit entry
    input_entry = 0
    timeData = ""
    ageData = ""
    nameData = ""
    timeUpdate = False
    ageUpdate = False
    nameUpdate = False

    while(check_entry(input_entry) == False or input_entry == 0): # Checks for a valid entry
        input_entry = input("Which entry will be updated?\n") # Sets input value
        try:
            input_entry = int(input_entry)
        except:
            input_entry = 0

    editing = True
    while(editing == True): # Loops until editing is finished
        edit_data = input("Choose which to edit: Time Age Name (type end if done)\n")

        if(edit_data == "Time"): # If time is editted
            while(format_check_time(timeData) != "Recorded"):
                timeData = input("Enter: Time\n")
            timeUpdate = True
        elif(edit_data == "Age"): # If age is editted
            while(format_check_age(ageData) != "Recorded"): 
                ageData = input("Enter: Age\n")
            ageUpdate = True
        elif(edit_data == "Name"): # If name is editted
            while(format_check_name(nameData) != "Recorded"):
                nameData = input("Enter: Name\n")
            nameUpdate = True
        elif(edit_data == "end"): # If end
                editing = False
        else:                       
            print("Invalid Action")
    
    add_entry(entry_data,input_entry,timeUpdate,ageUpdate,nameUpdate) # add modified entry

# Prompt for directing commands
def input_prompt():
    action = input("Action: new, edit, view entry, or finish\n") # action prompt
    global active

    if(action == "new"): # new action
        new_entry()
    elif(action == "edit"): # edit action
        edit_entry()
    elif(action == "view entry"): # view entry action
        input_entry = 0
        while(check_entry(input_entry) == False or input_entry == 0): # Checks for a valid entry
            input_entry = input("View which Entry? (intger)\n") # Sets input value
            try:
                input_entry = int(input_entry)
            except:
                input_entry = 0
        print_entry(input_entry)
    elif(action == "finish"): # finish action
        active = False

# The main function      
def Main_funct():
    Create_file_check = exists('fun.dat') # Check if create file
    if(Create_file_check == False):
        create_file()
    while(active): # loop action
        input_prompt()
    
# Main function running
Main_funct()