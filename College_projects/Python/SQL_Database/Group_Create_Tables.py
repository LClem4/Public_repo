import mysql.connector

conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='databasae',port=3306)
db_cursor = conn.cursor() 

# Author: Luke Clement
# This code is for building and populating all the tables for the previously designed database

# Function for dropping a table
def drop_table(table):
     tableCommand = "SET FOREIGN_KEY_CHECKS = 0; DROP TABLE "+str(table)+"; SET FOREIGN_KEY_CHECKS = 1;"
     try:
         db_cursor.execute(tableCommand)
         conn.commit()
     except Exception as e:
         print(e)
         conn.rollback() 

# Function for dropping all tables         
def drop_all(): 
     db_cursor.execute("Show tables")
     myresult = db_cursor.fetchall()
 
     tableCommand1 = "SET FOREIGN_KEY_CHECKS = 0"
     tableCommand2 = "DROP TABLE "+myresult[0][0]
     for i in range(1,len(myresult)):
         tableCommand2 = tableCommand2 + ', ' + myresult[i][0]
     #tableCommand2 = "DROP TABLE Creature_Area, Creature_Ability, Creature_To_Player_Attitude, Creature_To_Creature_Attitude, Creature, Generic_Item, Weapon, Armor, Container, Item, Ability, Player_Character, Location_Exit, Location, Player, Moderator, Manager"
     tableCommand3 = "SET FOREIGN_KEY_CHECKS = 1"   
     try:
         db_cursor.execute(tableCommand1)
         db_cursor.execute(tableCommand2)
         db_cursor.execute(tableCommand3)
         conn.commit()
     except Exception as e:
         print(e)
         conn.rollback() 

# Prints all table names in the database
def printAllTablesNames(): 
     db_cursor.execute("Show tables")
     myresult = db_cursor.fetchall()
     for x in myresult:
         print(x[0])    
   
# Prints all rows in the table             
def printAllTableRows():   
     def printTable(table):
         tableCommand = "SELECT * FROM "+table 
         db_cursor.execute(tableCommand)
         myresult = db_cursor.fetchall()
         print(table)
         for x in myresult:
             print(x)  
         
     tables = ["Creature_Area", "Creature_Ability", "Creature_To_Player_Attitude", "Creature_To_Creature_Attitude", "Creature", "Generic_Item", "Weapon", "Armor", "Container", "Item", "Ability", "Player_Character", "Location_Exit", "Location", "Player", "Moderator", "Manager" ]
     
     for i in tables:
         printTable(i)
 
# Function for creates all tables in the database           
def create_tables():    
     Manager_sql = "CREATE TABLE Manager (Login VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Login)>=10), Password VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Password)>=10), Email VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Email)>=10), PRIMARY KEY (Login))"
     Moderator_sql = "CREATE TABLE Moderator (Login VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Login)>=10), Password VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Password)>=10), Email VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Email)>=10), PRIMARY KEY (Login))"
     Player_sql = "CREATE TABLE Player (Login VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Login)>=10), Password VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Password)>=10), Email VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Email)>=10), PRIMARY KEY (Login))"
     Location_sql = "CREATE TABLE Location (Location_ID INT UNSIGNED NOT NULL PRIMARY KEY, Location_Name VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Location_Name)>=3), Size FLOAT(24) CHECK(Size > 0 AND Size < 1000000) NOT NULL, Location_Type ENUM('mountain','forest', 'city') NOT NULL)" 
     Exits_sql = "CREATE TABLE Location_Exit (Location_To_ID INT UNSIGNED NOT NULL, Location_From_ID INT UNSIGNED NOT NULL, FOREIGN KEY (Location_To_ID) REFERENCES Location(Location_ID) ON DELETE RESTRICT, FOREIGN KEY (Location_From_ID) REFERENCES Location(Location_ID) ON DELETE CASCADE, PRIMARY KEY (Location_To_ID, Location_From_ID))"
     Player_Character_sql = "CREATE TABLE Player_Character (Character_Name VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Character_Name)>=3), Max_Hp INT NOT NULL CHECK(Max_Hp > 0 AND Max_Hp <= 100), Current_Hp INT NOT NULL CHECK(Current_Hp >= 0 AND Current_Hp <= 100),	 Player_Login VARCHAR(64) CHECK(CHAR_LENGTH(Player_Login)>=10), Location_ID INT UNSIGNED NOT NULL, Stamina INT NOT NULL CHECK(Stamina > 0 AND Stamina <= 100), Strength INT NOT NULL CHECK(Strength > 0 AND Strength <= 100), PRIMARY KEY (Character_Name), FOREIGN KEY (Player_Login) REFERENCES Player(Login), FOREIGN KEY (Location_ID) REFERENCES Location(Location_ID))"
     Ability_sql = "CREATE TABLE Ability (Ability_ID INT UNSIGNED NOT NULL, Stat_Affected ENUM('Current_Hp', 'Strength', 'Stamina') NOT NULL, Effect INT NOT NULL CHECK(Effect >= -100 AND Effect <= 100), Cast_Time INT NOT NULL CHECK(Cast_Time > 0 AND Cast_Time < 60000), Rate_Of_Occurence INT CHECK(Rate_Of_Occurence > 0 AND Rate_Of_Occurence < 1000), Primary KEY (Ability_ID))"
     Item_sql = "CREATE TABLE Item (Item_ID INT UNSIGNED NOT NULL, Location_ID INT UNSIGNED NOT NULL, Mass FLOAT(24) NOT NULL CHECK(Mass > 0 AND Mass < 1000), Volume FLOAT(24) NOT NULL CHECK(Volume > 0 AND Volume < 50), Worn_By_Character_Name VARCHAR(64) CHECK(CHAR_LENGTH(Worn_By_Character_Name)>=3), In_Possesion_Of_Character_Name VARCHAR(64) CHECK(CHAR_LENGTH(In_Possesion_Of_Character_Name)>=3), In_Container_ID INT UNSIGNED, PRIMARY KEY (Item_ID), FOREIGN KEY (In_Possesion_Of_Character_Name) REFERENCES Player_Character(Character_Name),FOREIGN KEY (Worn_By_Character_Name) REFERENCES Player_Character(Character_Name), FOREIGN KEY (Location_ID) REFERENCES Location(Location_ID))"
     Container_sql = "CREATE TABLE Container (Container_ID INT UNSIGNED NOT NULL, Volume_Limit INT NOT NULL CHECK(Volume_Limit > 0 AND Volume_Limit < 100), Weight_Limit INT NOT NULL CHECK(Weight_Limit > 0 AND Weight_Limit < 5000), Primary KEY (Container_ID), FOREIGN KEY (Container_ID) REFERENCES Item(Item_ID))"
     Alter_item_sql = "ALTER TABLE Item ADD FOREIGN KEY (In_Container_ID) REFERENCES Container(Container_ID)"
     Armor_sql = "CREATE TABLE Armor (Armor_ID INT UNSIGNED NOT NULL, Slot ENUM('head', 'legs', 'body','boots') NOT NULL, Damage_Reduction INT NOT NULL CHECK(Damage_Reduction >= 0 AND Damage_Reduction < 1000), PRIMARY KEY (Armor_ID), FOREIGN KEY (Armor_ID) REFERENCES Item(Item_ID))"
     Weapon_sql = "CREATE TABLE Weapon (Weapon_ID INT UNSIGNED NOT NULL, Ability_ID INT UNSIGNED NOT NULL, Primary KEY (Weapon_ID), FOREIGN KEY (Ability_ID) REFERENCES Ability(Ability_ID), FOREIGN KEY (Weapon_ID) REFERENCES Item(Item_ID))"
     Generic_Item_sql = "CREATE TABLE Generic_Item (ID INT UNSIGNED NOT NULL, Generic_Item_ID INT UNSIGNED NOT NULL UNIQUE, Generic_Item_Name VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH( Generic_Item_Name)>=3), PRIMARY KEY (ID), FOREIGN KEY (Generic_Item_ID) REFERENCES Item(Item_ID))"
     Creature_sql = "CREATE TABLE Creature (Creature_ID INT UNSIGNED NOT NULL, Max_Hp INT UNSIGNED NOT NULL CHECK(Max_Hp > 0 AND Max_Hp <= 100), Current_Hp INT NOT NULL CHECK(Current_Hp >= 0 AND Current_Hp <= 100), Location_ID INT UNSIGNED NOT NULL, Stamina INT NOT NULL CHECK(Stamina > 0 AND Stamina <= 100), Strength INT NOT NULL CHECK(Strength > 0 AND Strength <= 100), Damage_Reduction INT NOT NULL CHECK(Damage_Reduction >= 0 AND Damage_Reduction <= 1000), PRIMARY KEY (Creature_ID), FOREIGN KEY (Location_ID) REFERENCES Location(Location_ID))"
     C_to_C_A_sql = "CREATE TABLE Creature_To_Creature_Attitude (Creature_A_ID INT UNSIGNED NOT NULL, Creature_B_ID INT UNSIGNED NOT NULL, Attitude INT NOT NULL CHECK(Attitude=1 OR Attitude=-1), FOREIGN KEY (Creature_A_ID) REFERENCES Creature(Creature_ID), FOREIGN KEY (Creature_B_ID) REFERENCES Creature(Creature_ID), PRIMARY KEY (Creature_A_ID, Creature_B_ID))"
     C_to_P_A_sql = "CREATE TABLE Creature_To_Player_Attitude (Creature_ID INT UNSIGNED NOT NULL, Player_Login VARCHAR(64) NOT NULL CHECK(CHAR_LENGTH(Player_Login)>=10), Attitude INT NOT NULL CHECK(Attitude=1 OR Attitude=-1), FOREIGN KEY (Creature_ID) REFERENCES Creature(Creature_ID), FOREIGN KEY (Player_Login) REFERENCES Player(Login), PRIMARY KEY (Creature_ID, Player_Login))"
     Creature_Ability_sql = "CREATE TABLE Creature_Ability (Creature_ID INT UNSIGNED NOT NULL, Creature_Ability_ID INT UNSIGNED NOT NULL, PRIMARY KEY (Creature_ID, Creature_Ability_ID), FOREIGN KEY (Creature_ID) REFERENCES Creature(Creature_ID), FOREIGN KEY (Creature_Ability_ID) REFERENCES Ability(Ability_ID))"
     Creature_Area_sql = "CREATE TABLE Creature_Area (Creature_ID INT UNSIGNED NOT NULL, Area ENUM('mountain', 'forest', 'city') NOT NULL, PRIMARY KEY (Creature_ID, Area), FOREIGN KEY (Creature_ID) REFERENCES Creature(Creature_ID))"
     
     tables_sql = [Manager_sql,Moderator_sql,Player_sql,Location_sql,Exits_sql,Player_Character_sql,Ability_sql,Item_sql,Container_sql,Alter_item_sql,Armor_sql,Weapon_sql,Generic_Item_sql,Creature_sql,C_to_C_A_sql,C_to_P_A_sql,Creature_Ability_sql,Creature_Area_sql]
     
     def build_each_Table(tables):
        for i in tables:
            db_cursor.execute(i)
     try: 
      
         drop_all()   
         build_each_Table(tables_sql)
     
         conn.commit()  
     except Exception as e:
         print(e)
         conn.rollback()
 
# Function for populating all the tables    
def populate_Tables():     
    Ma_sql = "INSERT INTO Manager (Login, Password, Email) VALUES (%s, %s, %s)"
    Ma_val = [] 
    for i in range(5):
        Ma_val.append(('DrDudleyGirard '+str(i), 'password '+str(i), 'cdgira@ship.edu'))
    
    Mo_sql = "INSERT INTO Moderator (Login, Password, Email) VALUES (%s, %s, %s)"
    Mo_val = [] 
    for i in range(5):
        Mo_val.append(('ProfessorChenHou '+str(i), 'password '+str(i), 'chuo@ship.edu'))
    
    P_sql = "INSERT INTO Player (Login, Password, Email) VALUES (%s, %s, %s)"
    P_val = []
    for i in range(5):
        P_val.append(('PatrickHicks '+str(i), 'password '+str(i), 'ph4671@ship.edu'))
    
    L_sql = "INSERT INTO Location (Location_ID, Location_Name, Size, Location_Type) VALUES (%s, %s, %s, %s)"
    L_val = []
    L_val.append((0, 'Defaultlandia', 3.14159, 'mountain'))
    for i in range(1,5):
        L_val.append((i, 'Dr.Girard Office '+str(i), 9999, 'city'))
    
    E_sql = "INSERT INTO Location_Exit (Location_To_ID, Location_From_ID) VALUES (%s, %s)" 
    E_val = [(0,0),(0,1),(1,2),(2,3),(3,4)]

    PC_sql = "INSERT INTO Player_Character (Character_Name, Max_Hp, Current_Hp, Player_Login, Location_ID, Stamina, Strength) VALUES (%s, %s, %s, %s, %s, %s, %s)"
    PC_val = []
    for i in range(5):
        PC_val.append(('SparactusTheWise '+str(i), 10, 10, 'PatrickHicks 1', 1, 10, 10))
    
    Ab_sql = "INSERT INTO Ability (Ability_ID, Stat_Affected, Effect, Cast_Time, Rate_Of_Occurence) VALUES (%s, %s, %s, %s, %s)"
    Ab_val = []
    for i in range(5):
        Ab_val.append((i, 'Current_Hp', 10, 100, 100))
    
    I_sql = "INSERT INTO Item (Item_ID, Location_ID, Mass, Volume, Worn_By_Character_Name, In_Possesion_Of_Character_Name, In_Container_ID) VALUES (%s, %s, %s, %s, %s, %s, %s)"
    I_val = []
    for i in range(25):
        I_val.append((i, 1, 10, 10, None, None, None))
    
    Co_sql = "INSERT INTO Container (Container_ID, Volume_Limit, Weight_Limit) VALUES (%s, %s, %s)"
    Co_val = []
    for i in range(5):
        Co_val.append((i, 20, 20))
    
    Ar_sql = "INSERT INTO Armor (Armor_ID, Slot, Damage_Reduction) VALUES (%s, %s, %s)"
    Ar_val = []
    for i in range(5,10):
        Ar_val.append((i, 'boots', 20))
    
    W_sql = "INSERT INTO Weapon (Weapon_ID, Ability_ID) VALUES (%s, %s)"
    W_val = []
    for i in range(10,15):
        W_val.append((i, 1))
    
    GI_sql = "INSERT INTO Generic_Item (ID, Generic_Item_ID, Generic_Item_Name) VALUES (%s, %s, %s)"
    GI_val = []
    for i in range(5):
        GI_val.append((i, i+15, 'Potion'))
    
    Cr_sql = "INSERT INTO Creature (Creature_ID, Max_Hp, Current_Hp, Location_ID, Stamina, Strength, Damage_Reduction) VALUES (%s, %s, %s, %s, %s, %s, %s)"
    Cr_val = []
    for i in range(5):
        Cr_val.append((i, 10, 10, 1, 10, 10, 10))
    
    CTCA_sql = "INSERT INTO Creature_To_Creature_Attitude (Creature_A_ID, Creature_B_ID, Attitude) VALUES (%s, %s, %s)"
    CTCA_val = [(0, 1, -1),(0, 2, -1),(0, 3, -1),(0, 4, -1),(1, 2, -1)]
    
    CTPA_sql  = "INSERT INTO Creature_To_Player_Attitude (Creature_ID, Player_Login, Attitude) VALUES (%s, %s, %s)"
    CTPA_val = [(1, 'PatrickHicks 1', 1),(2, 'PatrickHicks 1', 1),(3, 'PatrickHicks 1', 1),(4, 'PatrickHicks 1', 1),(0, 'PatrickHicks 1', 1)]
    
    CAb_sql = "INSERT INTO Creature_Ability (Creature_ID, Creature_Ability_ID) VALUES (%s, %s)"
    CAb_val = [(1, 1),(2, 1),(3, 1),(4, 1),(0, 1)]
    
    CAr_sql = "INSERT INTO Creature_Area (Creature_ID, Area) VALUES (%s, %s)"
    CAr_val = [(1, 'city'),(2, 'city'),(3, 'city'),(4, 'city'),(0, 'city')]
    
    def insert_rows(sql,val):
        for i in range(len(sql)):
            for j in range(len(val[i])):
                db_cursor.execute(sql[i],val[i][j])
     
    sqls = [Ma_sql,Mo_sql,P_sql,L_sql,E_sql,PC_sql,Ab_sql,I_sql,Co_sql,Ar_sql,W_sql,GI_sql,Cr_sql,CTCA_sql,CTPA_sql,CAb_sql,CAr_sql]
    vals = [Ma_val,Mo_val,P_val,L_val,E_val,PC_val,Ab_val,I_val,Co_val,Ar_val,W_val,GI_val,Cr_val,CTCA_val,CTPA_val,CAb_val,CAr_val]
            
    try:   
        insert_rows(sqls,vals)       
        conn.commit()  
    except Exception as e:
        print(e)
        conn.rollback() 
        
# Function for testing       
def selectTable():
    tableCommand = "SELECT Character_Name FROM Player_Character"
    db_cursor.execute(tableCommand)
    abc = db_cursor.fetchall()
    print(abc)
      
            
# Main function for the script 
def Main():         
    create_tables()       
    populate_Tables()
    printAllTableRows()
    conn.close()
  
# Calling main  
Main()