/*
 * Project: Stack & Queue ADTs Juggler
 * Program: sqjuggler.c
 * Programmer: Luke Clement
 * Course: CSC111
 * Professor: Dr. Lee
 * Created: April 20th, 2022
 * Updated: April 26th, 2022
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define N 1000
#include "sqjuggler.h"

// Main function
int main(int argc, char *argv[]){

	FILE *fp;						// Creates file pointer

	if(argc != 2)						// Code from canopen.c King, Programming a modern approach p. 547
	{							// Checks whether can be run or not
		printf("Program needs two arguments\n");
		exit(EXIT_FAILURE);
	}
	if((fp = fopen(argv[1], "r")) == NULL)
	{
		printf("%s can't be opened\n", argv[1]);
		exit(EXIT_FAILURE);
	}


	int i = 0;						// initializes i to 0. i keeps tracks of of current char being read from buffer
	char ch = 0;						// initializes ch to 0 temporarily. file characters are stored in ch and then stored in buffer
	char buffer[N];						// initializes buffer to size N. So it can store up to 1000 characters

	fp = fopen(argv[1], "r");				// Creates a pointer to file pointer pointing to test.dat
	while(ch != EOF)					// Reads in data from text file
	{
		ch = fgetc(fp);					// Stores current character into ch
		buffer[i++] = ch;				// Puts ch character into buffer and then increments 
	}
	fclose(fp);						// Closes file stream
	

	char command[10];					// array for storing commands
	char add_num[5];					// array for storing number being added
	int  new_num;						// int variable for storing number being added to inStack and inQueue
	i = 0;							// resets i to 0
	int j = 0;						// int used for inputting values into command and add_num

	Stack inStack = create();				// Creates inStack, inQueue, outStack, outQueue
	Queue inQueue = create2();
	Stack outStack = create();
	Queue outQueue = create2();
	
	while(buffer[i]!= '\0')					// Reads in integer
		{

		j = 0;						// resets j with each new command
		while(buffer[i]!= ' ' && buffer[i]!= '\n'){	// reads in the next command from buffer and stores it into command as a string
			command[j++] = buffer[i++];
		}
		command[j] = '\0';				// adds terminating character at end of string
	
		if(strcmp(command,"add") == 0)			// If command string equals "add", then the next number is added to inStack and inQueue 
		{
	  		j = 0;					// reset j for add_num
			while(buffer[i]!= '\n')			// Reads number until the end of the line
			{
				add_num[j++] = buffer[i++];	// Puts the numbers in buffer into add_num
			}
			add_num[j] = '\0';			// Adds null character on end of number string
			sscanf(add_num, "%d", &new_num);	// Converts number from char to int
	        	push(inStack,new_num);			// Puts new_num into inStack
			enqueue2(inQueue, new_num); 		// Puts new_num into inQueue
		}
		
		if(strcmp(command,"delete") == 0)		// If command string equals "delete", then inStack value is enqueued into queue and
		{						
			enqueue2(outQueue, pop(inStack));	// inStack top Item is popped and enqueued into outQueue
			push(outStack, dequeue2(inQueue));	// inQueue front Item is dequeued and pushed into outStack 
		
		}
		
		i++;						// i is incremented to continue reading in buffer
	}							// Stops once it reaches end of buffer
	
		printf("outStack: ");				// Empties and prints out outStack
		make_empty(outStack);
		printf("\n");

		printf("outQueue: ");				// Empties and prints out outQueue
		make_empty2(outQueue);
		printf("\n");

  return 0;
}
