/*
 * Project: Stack & Queue ADTs Juggler
 * Program: stack.c
 * Programmer: Luke Clement
 * Course: CSC111
 * Professor: Dr. Lee
 * File Created:  April 20th, 2022
 * File Updated:  April 26th, 2022
 */

/* Code is taken from King, C Programming a Modern Approach, p.500-501 */

#include <stdio.h>
#include <stdlib.h>
#include "sqjuggler.h"

struct node 							// Creates new node for building the list
{
  Item data;
  struct node *next;
};

struct stack_type 						// Creates a top node pointer to the stack 
{
  struct node *top;
};

void terminate(const char *message)				// Creates a way way to print an error message and quickly terminate the program
{
  printf("%s\n", message);
  exit(EXIT_FAILURE);
}

Stack create(void)						// Creates stack 
{
  Stack s = malloc(sizeof(struct stack_type));			// allocates memory for the stack
  if (s == NULL)
    terminate("Error in create: stack could not be created.");
  s->top = NULL;						// Sets top of stack to NULL
  return s;
}

void destroy(Stack s)						// Destroys stack and frees up memory
{
  make_empty(s);
  free(s);
}

void make_empty(Stack s)					// Makes stack empty
{
  while (!is_empty(s))
  {
    printf("%d ",pop(s));
  }
}

int is_empty(Stack s)						// Checks if stack is empty
{
  return s->top == NULL;
}

int is_full(Stack s)						// Checks if stack is full, should return false because it is a linked list
{
  return 0;
}

void push(Stack s, Item i)					// Takes stack and pushes item into stack
{
  struct node *new_node = malloc(sizeof(struct node));		// allocates node memory
  if (new_node == NULL)
    terminate("Error in push: stack is full.");

  new_node->data = i;						// Puts data in new node
  new_node->next = s->top;					// Sets new node->next to where s->top is pointing
  s->top = new_node;						// Sets top to point to new node
}

Item pop(Stack s)						// Pops top value from stack
{
  struct node *old_top;
  Item i;

  if (is_empty(s))
    terminate("Error in pop: stack is empty.");

  old_top = s->top;						// Sets old top to where top is pointing
  i = old_top->data;						// Puts old top data in i
  s->top = old_top->next;					// Sets top to point to next node in list
  free(old_top);						// free the old front node from memory
  return i;							// returns i item
}

