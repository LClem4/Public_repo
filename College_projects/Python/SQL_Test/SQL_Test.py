import mysql.connector
import statistics as stat
import math
import time
coding=utf8

# Author: Luke Clement

# It then runs 10 tests inserting code into a table with and without a primary key
# It then computes a t-value for the two data sets based on  their stdDev and Mean

# Makes connection to database
conn = mysql.connector.connect(host='host',user='user',passwd='passwd',database='databasae',port=3306)
db_cursor = db.cursor() # Creates cursor

# Creates table
def Create_Table(primaryKeyCheck):
    if(primaryKeyCheck == True):
      db_cursor.execute("CREATE TABLE TEST (number1 int PRIMARY KEY, number2 int, myName VARCHAR(10), myAddress VARCHAR(30), distance float)")
    if(primaryKeyCheck == False):
      db_cursor.execute("CREATE TABLE TEST (number1 int, number2 int, myName VARCHAR(10), myAddress VARCHAR(30), distance float)")

# Inserts row into table
def insert_row(number1, number2, myName, myAddress, distance):
    sql = "INSERT INTO TEST (number1, number2, myName, myAddress, distance) VALUES (%s,%s,%s,%s,%s)"
    val =  (number1, number2, myName, myAddress, distance)
    db_cursor.execute(sql, val)

# inserts n rows into table and returns the time it takes 
def insert_n(n):
    start = time.time()
    for i in range(n):
      insert_row(i, i, 'Bob' + str(-i), 'Main Street '+str(i), i*10)
    end = time.time()
    insertTime = end - start
    #db.commit()
    return insertTime
      
# Drops table
def drop_table(table):
    sql = "DROP TABLE "+str(table)
    db_cursor.execute(sql)
    
# Prints table
def print_table(table):
    db_cursor.execute("SELECT * FROM "+str(table))
    result = db_cursor.fetchall()
    for row in result:
      print(row)
      
# Shows table list
def show_tables():
    db_cursor.execute("SHOW TABLES")
    for x in db_cursor:
      print(x)
   
# Performs n tests of 10000 inserts into a table and returns stdDev and Mean (No primary key)   
def test_n(n, textCheck):
    insertTimes = []
    for x in range(n):
      Create_Table(False)
      insertTime = insert_n(10000)
      if(textCheck == True):
        print("No Primary Key Test " + str(x) + ": " + str(insertTime))
      else:
        print(insertTime)
      insertTimes.append(insertTime)
      drop_table('TEST')
    stdDev = stat.stdev(insertTimes)
    Mean = stat.mean(insertTimes)
    return Mean #stdDev, Mean
    
# Performs n tests of 10000 inserts into a table and returns stdDev and Mean (With primary key) 
def test_pk_n(n, textCheck):
   insertTimes = []
   for x in range(n):
      Create_Table(True)
      insertTime = insert_n(10000)
      if(textCheck == True):
        print("With Primary Key Test " + str(x) + ": " + str(insertTime))
      else:
        print(insertTime)
      insertTimes.append(insertTime)
      drop_table('TEST')
   stdDevPk = stat.stdev(insertTimes)
   MeanPk = stat.mean(insertTimes)
   return stdDevPk, MeanPk
   
# This is the main function that actually runs   
def Main(textCheck):
  firstTest = test_n(10, textCheck)
  secondTest = test_pk_n(10, textCheck)
  
  accuracy = math.sqrt((10*(firstTest[0]**2) + 10*(secondTest[0]**2))/(10-1)+(10-1)) * math.sqrt(1/10+1/10)
  t = ((firstTest[1] - secondTest[1])/accuracy)
  print("No Primary Key Std Dev: " + str(firstTest[0]) + "  Mean: " + str(firstTest[1]))
  print("With Primary Key Std Dev: " + str(secondTest[0]) + "  Mean: " + str(secondTest[1]))
  print("Accuracy metric: " + str(accuracy))
  print("t-value: " + str(t))
  
  db.close()
  
Main(False)