import tkinter as tk
from tkinter import ttk, messagebox
import mysql.connector
from tkinter import *

# Author: Luke Clement
# This code is designed to interact and interface with the locations stored in a prebuilt database
 
# Builds the Stored Procedure
def makeStoredProcedure():
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    statement = "CREATE PROCEDURE delete_location(IN deleted_id INT) BEGIN DELETE FROM Location WHERE Location_ID = deleted_id; END"
    db_cursor.execute(statement)
    
    conn.commit()
    conn.close()

# Gets values from input boxes
def getValues():  
    try:
        entry_exits = []
        if(entry_exits_tmp.get() != ""):
            entry_id = entry_id_tmp.get()
        else:
            entry_id = 0
        if(entry_exits_tmp.get() != ""):
            entry_name = entry_name_tmp.get()
        else:
            entry_name = "Um..."
        if(entry_exits_tmp.get() != ""):
            entry_size = entry_size_tmp.get()
        else:
            entry_size = 1
        entry_type = entry_type_tmp.get()
       
        if(entry_exits_tmp.get() != ""):
           for i in entry_exits_tmp.get().split(','):
               entry_exits.append(int(i)) 
           entry_exits = [*set(entry_exits)]
        else:
           entry_exits.append(0)
            
        return entry_id, entry_name, entry_size, entry_type, entry_id, entry_exits
    except Exception as e:
        print(e)
        message.set("Error: Something ain't right with those values...")

# Command for inserting values into location and Location_Exits
def Add():
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    entry_id, entry_name, entry_size, entry_type, entry_id, entry_exits = getValues()
    try:
        L_sql = "INSERT INTO Location (Location_ID, Location_Name,Size, Location_Type) VALUES (%s, %s, %s, %s)"
        L_val = (entry_id, entry_name, entry_size, entry_type)
        db_cursor.execute(L_sql, L_val)
        for i in entry_exits:
            E_sql = "INSERT INTO Location_Exit (Location_To_ID, Location_From_ID) VALUES (%s, %s)"
            E_val = (i, entry_id)
            db_cursor.execute(E_sql, E_val)
        message.set("Added Location "+str(entry_id)+"!")
        conn.commit()
    except Exception as e:
        print(e)
        message.set("You can't add that you silly goose")
        conn.rollback()
    update_table()
    
    conn.close()
  
# Command for deleting rows from the Location table (USES STORED PROCEDURE)
def Delete():
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    try:
        if(entry_exits_tmp.get() != ""):
            entry_id = entry_id_tmp.get()
        else:
            entry_id = 0
        if(entry_id != 0):
            L_sql = "CALL delete_location("+str(entry_id)+")"
            db_cursor.execute(L_sql)
            message.set("Deleted Location "+str(entry_id)+"!")
            conn.commit()
        else:
            message.set("How dare you try to delete location 0!")
         
    except Exception as e:
        print(e)
        message.set("You shall not delete this magnificent location!")
        conn.rollback()
    update_table()
    
    conn.close()

# Command for updating rows in Location and location_Exits  
def Update():
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    entry_id, entry_name, entry_size, entry_type, entry_id, entry_exits = getValues()
    if(entry_id != 0):
       try:
           U_sql = "UPDATE Location SET Location_Name = %s, Size = %s, Location_Type = %s WHERE Location_ID = %s"
           U_val = (entry_name, entry_size, entry_type, entry_id)
           D_sql = "DELETE FROM Location_Exit WHERE Location_From_ID = %s"
           D_val = [entry_id]
           db_cursor.execute(U_sql,U_val)
           db_cursor.execute(D_sql,D_val)
           for i in entry_exits:
                E_sql = "INSERT INTO Location_Exit (Location_To_ID, Location_From_ID) VALUES (%s, %s)"
                E_val = (i, entry_id)
                db_cursor.execute(E_sql, E_val)
           message.set("Updated Location "+str(entry_id))
           conn.commit()
       except Exception as e:
           print(e)
           message.set("Error in Updating")
           conn.rollback()
    elif(entry_id == 0):
       message.set("Can't Update Location 0!")
    else:
       message.set("That location ain't in the database!")
    update_table()
   
    conn.close()

# Function for displaying the Locations Table
def show():
    for col in cols:
        listBox.column(str(col),anchor=CENTER, stretch=NO, width=150)
        listBox.heading(col, text=col)
        listBox.grid(row=1, column=0, columnspan=2)
        listBox.place(x=10, y=200)
  
# Code for retrieving and then displaying table
def update_table():
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    db_cursor.execute("SELECT Location_ID,Location_Name,size,Location_Type,Location_To_ID FROM Location, Location_Exit WHERE Location_ID = Location_From_ID")
    L_info = db_cursor.fetchall()
    for item in listBox.get_children():
        listBox.delete(item)
    for i, (Location_ID,L_name,L_description,L_type,exit_id2) in enumerate(L_info, start=1):
        listBox.insert("", "end", values=(Location_ID,L_name,L_description,L_type,exit_id2))
    show()
    
    conn.close()

# Code for building tables (NOT NECESSARY SINCE A DIFFERENT SCRIPT CREATES ALL THE TABLES)
def TableCheck(table):
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    tableCommand = "SHOW TABLES LIKE '"+str(table)+"'"
    db_cursor.execute(tableCommand)
    result = db_cursor.fetchone()
    if result:
       print("There is a table named "+str(table))
    else:
       print("There is not a table named "+str(table))
       try:
           if(str(table) == 'Location'):
               T_sql = "CREATE TABLE Location (Location_ID INT UNSIGNED NOT NULL PRIMARY KEY, Location_Name VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Location_Name)>=3), Size FLOAT(24) CHECK(Size > 0 AND Size < 1000000) NOT NULL, Location_Type ENUM('mountain','forest', 'city') NOT NULL)" 
               db_cursor.execute(T_sql)
               print("Added "+str(table)+"!")
           if(str(table) == 'Location_Exit'):
               T_sql2 = "CREATE TABLE Location_Exit (Location_To_ID INT UNSIGNED NOT NULL, Location_From_ID INT UNSIGNED NOT NULL, FOREIGN KEY (Location_To_ID) REFERENCES Location(Location_ID) ON DELETE CASCADE, FOREIGN KEY (Location_From_ID) REFERENCES Location(Location_ID) ON DELETE CASCADE, PRIMARY KEY (Location_To_ID, Location_From_ID))"
               db_cursor.execute(T_sql2)
               print("Added "+str(table)+"!")
           conn.commit()
       except Exception as e:
           print(e)
           conn.rollback()    
    conn.close()
 
# Function for dropping a particular table      
def drop_table(table):
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    tableCommand = "DROP TABLE "+str(table)
    try:
        db_cursor.execute(tableCommand)
        conn.commit()
    except Exception as e:
        print(e)
        conn.rollback()
        
    conn.close()
   
# Command for clearing input boxes for ease of use 
def Clear():
    entry_id_tmp.set("")
    entry_name_tmp.set("")
    entry_size_tmp.set("")
    entry_exits_tmp.set("")
    message.set("01100011 01101100 01100101 01100001 01110010 01100101 01100100")

# Command for retrieving the information associated with a particular Location
def Retrieve():
    conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='database',port=3306)
    db_cursor = conn.cursor()
    
    try:
        entry_id = entry_id_tmp.get()
        R_sql = "SELECT * FROM Location WHERE Location_ID = %s"
        R_sql2 = "SELECT Location_To_ID FROM Location_Exit WHERE Location_From_ID = %s"
        R_val = [entry_id]
        db_cursor.execute(R_sql,R_val)
        entry_values = db_cursor.fetchall()
        db_cursor.execute(R_sql2,R_val)
        entry_exit_values = db_cursor.fetchall()
       
        entry_id_tmp.set(entry_values[0][0])
        entry_name_tmp.set(entry_values[0][1])
        entry_size_tmp.set(entry_values[0][2])
        entry_type_tmp.set(entry_values[0][3])
        
        newExits = str(entry_exit_values[0][0])
        for i, value in enumerate(entry_exit_values):
            if(i > 0):
                newExits = newExits + ',' + str(entry_exit_values[i][0])
        entry_exits_tmp.set(newExits)
        message.set("Ah yes, we have acquired the information you seek")
    except Exception as e:
        print(e)
        message.set("Umm... you can't retrieve that")
        
    conn.close()

# Main script function   
def main():
    global entry_id_tmp
    global entry_name_tmp
    global entry_size_tmp
    global entry_type_tmp
    global entry_exits_tmp
    global cols
    global listBox
    global message
    global root
    
    # makeStoredProcedure()
    
    # Window
    root = Tk()
    root.geometry("770x465")
    
    # Variable offsets
    startY = 10
    startX = 10
    offsetY = 35
    offsetX = 190
    widthSize = 20
    buttonColor = '#e682ff'
    
    # ROW 1
    Label(root, text="Location", height=1, width=widthSize*5-8, borderwidth=3, relief="sunken").place(x=startX, y=startY)
    
    # ROW 2 (ID)
    Label(root, text="Location ID", height=1, width=widthSize+1, borderwidth=3, relief="sunken").place(x=startX, y=startY+offsetY*1+5) 
    entry_id_tmp = IntVar(root)
    entry_id_tmp.set(0)
    Entry(root, textvariable = entry_id_tmp, width=widthSize-4, font=('Arial 15')).place(x=startX+offsetX*1, y=startY+offsetY*1)
    Button(root, text="Retrieve",  height=1, command=Retrieve, width=widthSize-1, borderwidth=3, bg = buttonColor).place(x=startX+offsetX*2, y=startY+offsetY*1)
    
    # ROW 3 (ADD)
    Button(root, text="Add",  height=1, command=Add, width=widthSize-1, borderwidth=3, bg = buttonColor).place(x=startX-1, y=startY+offsetY*2)
    Button(root, text="Update",  height=1, command=Update,width=widthSize-1, borderwidth=3, bg = buttonColor).place(x=startX+offsetX*1-1, y=startY+offsetY*2)
    Button(root, text="Delete",  height=1, command=Delete,width=widthSize-1, borderwidth=3, bg = buttonColor).place(x=startX+offsetX*2-1, y=startY+offsetY*2)
    Button(root, text="Clear",  height=1, command=Clear,width=widthSize-1, borderwidth=3, bg = buttonColor).place(x=startX+offsetX*3-1, y=startY+offsetY*2)
    
    # ROW 4 (NAME)
    labelXSize = 2
    labelYoffset = 10
    Label(root, text="Name:",  height=1, width=widthSize+labelXSize, borderwidth=2, relief="sunken").place(x=startX, y=startY+offsetY*3+labelYoffset)
    Label(root, text="Size:",  height=1, width=widthSize+labelXSize, borderwidth=2, relief="sunken").place(x=startX+offsetX*1, y=startY+offsetY*3+labelYoffset)
    Label(root, text="Type:",  height=1, width=widthSize+labelXSize, borderwidth=2, relief="sunken").place(x=startX+offsetX*2, y=startY+offsetY*3+labelYoffset)
    Label(root, text="Exits:",  height=1, width=widthSize+labelXSize, borderwidth=2, relief="sunken").place(x=startX+offsetX*3, y=startY+offsetY*3+labelYoffset)
    
    # ROW 5 (NAME ENTRY)
    L_types = ['mountain','forest', 'city']
    
    entry_name_tmp = StringVar(root)
    entry_name_tmp.set("DefaultLandia")
    entry_size_tmp = DoubleVar(root)
    entry_size_tmp.set(3.14159)
    entry_type_tmp = StringVar(root)
    entry_type_tmp.set(L_types[0])
    entry_exits_tmp = StringVar(root)
    entry_exits_tmp.set("0")
    
    Entry(root, textvariable = entry_name_tmp, width=widthSize-4, font=('Arial 15')).place(x=startX, y=startY+offsetY*4)
    Entry(root, textvariable = entry_size_tmp, width=widthSize-4, font=('Arial 15')).place(x=startX+offsetX*1, y=startY+offsetY*4)
    OptionMenu(root, entry_type_tmp, *L_types).place(x=startX+offsetX*2+45, y=startY+offsetY*4)
    Entry(root, textvariable = entry_exits_tmp, width=widthSize-4, font=('Arial 15')).place(x=startX+offsetX*3, y=startY+offsetY*4)
    
    # ROW 6 (TABLE)
   
    cols = ('Location ID', 'Name', 'Size', 'Type', 'Exits')
    listBox = ttk.Treeview(root, columns=cols, show='headings')
    
    # ROW 7 (MESSAGE)
    
    message = StringVar(root)
    message.set("Welcome to the most awesomest GUI for a database!")
    Label(root, textvariable = message, width=widthSize*3+10, font=('Arial 15')).place(x=startX, y=startY+offsetY*12)
    
    #update_table()
    root.mainloop()
   
main()