/*
 * Project: Stack & Queue ADTs Juggler
 * Program: queue.c
 * Programmer: Luke Clement
 * Course: CSC111
 * Professor: Dr. Lee
 * File Created:  April 22th, 2022
 * File Updated:  April 26th, 2022
 */

#include <stdio.h>
#include <stdlib.h>
#include "sqjuggler.h"

/* This code is modified from King, C Programming A Modern Approach, p.500-501 stackADT3.c */

typedef int Item;

  struct node2 {								// Creates a node struct for the ADT queue to build a linked list
 	 Item data;
  	 struct node2 *next;
  };

  struct queue_type {								// Creates a pointer to the front and rear of a list
	struct node2 *front;
	struct node2 *rear;
  };	  				

  void terminate2(const char *message)						// Error message that makes it easier to insert error
  {
  	printf("%s\n", message);
 	exit(EXIT_FAILURE);
  }

  Queue create2(void)								// Creates a Queue
  {
	  Queue s = malloc(sizeof(struct queue_type));
	  if(s == NULL)
		  terminate2("Error in create: stack could not be created.");
	  s->front = NULL;
	  s->rear = NULL;
	  return s;
  }

  void make_empty2(Queue s)							// Makes queue empty and prints out the values
  {
  	while (!is_empty2(s))
	{
   		printf("%d ",dequeue2(s));
	}
  }

  int is_empty2(Queue s)							// Checks if queue is empty
  {
  	return s->front == NULL;
  }

  void enqueue2(Queue s, Item i)				             	// Enqueues value into queue and changes where nodes point
  {
 	struct node2 *new_node = malloc(sizeof(struct node2)); 			// Creates new space
  	if (new_node == NULL)
   	 terminate2("Error in enqueue2: que is full.");
	new_node->data = i;							// sets new node to i
	new_node->next = NULL;							// defines where new node points to make sure it's set

	if(s->front == NULL && s->rear == NULL)					// If both top and rear are null then it sets them to point to the new node 
	{
		s->front = new_node;
		s->rear = new_node;
	}
	else
	{
  		s->rear->next = new_node;			             	// Sets rear->next to point to new node
  		s->rear = new_node;						// Sets rear to point to new node
	}	
  }

  Item dequeue2(Queue s)					     		// Dequeues value out of queue and changes where nodes point
  {
        
	struct node2 *cur;							// Sets pointer for removing the cur front node
        Item i;

  	if (is_empty2(s))
    		terminate2("Error in dequeue2: queue is empty.");
		
        else
	{	
  		cur = s->front;							// Sets cur to point to front node
		s->front = s->front->next;					// Sets front to point to the next node in list
		if(s->front == NULL)						// If front equals Null then rear is set to Null
			s->rear = NULL;
	}

	i = cur->data;								// i is set to cur node data
  	return i;								// i is returned
  }

