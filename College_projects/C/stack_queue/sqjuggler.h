/*
 * Project: Stack & Queue ADTs Juggler
 * Program: sqjuggler.h
 * Programmer: Luke Clement
 * Course: CSC111
 * Professor: Dr. Lee
 * File Created:  April 20th, 2022
 * File Updated:  April 26th, 2022
*/

#ifndef STACKQUEUE_H
#define STACKQUEUE_H
	
typedef int Item; 			// Defines the Item Abstract Datatype

typedef struct queue_type *Queue;	// Defines the functions for queue
void terminate2(const char *message);	// terminate function
Queue create2(void);			// creates queue
void make_empty2(Queue s); 		// makes queue empty
int is_empty2(Queue s);			// Checks if queue is empty
void enqueue2(Queue s, Item i);		// enqueues item into Queue
Item dequeue2(Queue s);			// dequeues item from Queue

typedef struct stack_type *Stack;	// Defines the functions for Stack
void terminate(const char *message);	// terminate function
Stack create(void);			// Creates stack
void make_empty(Stack s);		// empties stack		
int is_empty(Stack s);			// Checks if queue is empty			
void push(Stack s, Item i);		// Pushes item into Stack
Item pop(Stack s);			// Pops item from stack

#endif

