#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUM_EXE 32
#define NUM_COMMANDS 32
#define WORD_SIZE 128
#define CWD_SIZE 512

int sortinputLine(FILE *inputText);
char *searchValidPath(char paths[NUM_COMMANDS][WORD_SIZE], char *next);
char *concatPath(char *first, char *second);
void clearPath(char input[NUM_COMMANDS][WORD_SIZE]);
void clearInputLine(char input[NUM_EXE][NUM_COMMANDS][WORD_SIZE]);
void pInputLine(int line);
void addFile(char *inputPath); 

void throw_error(char *errorMsg);
void throw_error_noFile(char *errorMsg);
int EOFCheck(FILE *fp);
 
int runCommand3(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum);
int runCommand2(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum);
int runCommand(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum);
int runCommandParallel(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum);
int commandExit(char inputLine[NUM_COMMANDS][WORD_SIZE]);
void commandcd(char *inputWord);
void commandls(char inputLine[NUM_COMMANDS][WORD_SIZE]);
void commandrm(char inputLine[NUM_COMMANDS][WORD_SIZE]);
void commandcat(char inputLine[NUM_COMMANDS][WORD_SIZE]);
void commandecho(char inputLine[NUM_COMMANDS][WORD_SIZE]);
void commandpath(char inputLine[NUM_COMMANDS][WORD_SIZE]);
void commandExe(char *inputWord, char *argv2[], int argc2);
int commandCheck(char *commandword, char *inputWord);
