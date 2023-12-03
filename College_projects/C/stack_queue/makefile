#  Project: Stack & Queue ADTs Juggler
#  Program: makefile
#  Programmer: Luke Clement
#  Course: CSC111
#  Professor: Dr. Lee
#  File Created:  April 22th, 2022
#  File Updated:  April 26th, 2022

SOURCES = queue.c stack.c sqjuggler.c 		# Source files including the queue, stack, and juggler program 
OBJECTS = queue.c stack.o sqjuggler.o 		# Object files for each of the source files
HEADERS = sqjuggler.h		      		# header file used in all three source files
CC = gcc			      		# gcc is the compiler
CFLAGS = -g -Wall		     		# Checks for errors

sqjuggler: $(OBJECTS)		      		# Assembles sqjuggler and compiles it
	$(CC) $(CFLAGS) $(OBJECTS) -o sqjuggler
clean:				     		# Cleans the data
	rm -fR *o sqjuggler
