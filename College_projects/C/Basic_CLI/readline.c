#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include "readline.h"

extern int PATH_COUNT;
extern char CWD[CWD_SIZE];
extern int CurrentExe;

char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE];
int INPUTWORD_COUNT[NUM_EXE];
int EXE_COUNT = 1;

// Command for parsing lines
int sortinputLine(FILE *inputText){ // sorts input line 
    clearInputLine(inputLine);
    size_t  bufferSize = 512; // Creates buffer size
    char *bufferLine = (char *)malloc(bufferSize * sizeof(char)); // Allocates buffer memory
    int length = getline(&bufferLine,&bufferSize,inputText); // Gets line from input file pointer
    int numExe = 0;
    int numWord = 0;
    int letter = 0;  
    for(int j = 0; j < length; j++){ // Formats the line
        if(bufferLine[j] == ' '){
            if(letter != 0){
                inputLine[numExe][numWord][letter] = '\0';
                numWord++;
                letter = 0;
            }
        }
        else if(bufferLine[j] == '>'){
            if(letter != 0){
                inputLine[numExe][numWord][letter]  = '\0';
                letter = 0;
                numWord++;
            }
            inputLine[numExe][numWord][letter]  = '>';
            letter++;
            inputLine[numExe][numWord][letter]  = '\0';
            letter = 0;
            numWord++;
        }
        else if(bufferLine[j] == '\n'){
            if(letter != 0){
                inputLine[numExe][numWord][letter]  = '\0'; // adds one last extra work
                INPUTWORD_COUNT[numExe] = numWord+1;
            }
            else {
                INPUTWORD_COUNT[numExe] = numWord; 
            }
            EXE_COUNT = numExe+1;
            free(bufferLine);
            return 0;
        }
        else if(bufferLine[j] == '&'){
            if(letter != 0){
                inputLine[numExe][numWord][letter]  = '\0'; // adds one last extra work
                INPUTWORD_COUNT[numExe] = numWord+1;
            }
            else {
                INPUTWORD_COUNT[numExe] = numWord; 
            }
            if(bufferLine[j+1] != '\n') {
                numExe++;
            }
            numWord = 0;
            letter = 0;
        }
        else{
            inputLine[numExe][numWord][letter] = bufferLine[j];
            letter++;
        }
    }
    free(bufferLine);
    return -1;
}

// Searches for valid paths
char *searchValidPath(char paths[NUM_COMMANDS][WORD_SIZE], char *extension){
   char *validPath = "\0";

    validPath = concatPath(CWD,extension);
    if(access(validPath,X_OK) == 0){
        return validPath;
    }
    free(validPath);

    for(int i = 0; i < PATH_COUNT; i++){
        validPath = concatPath(paths[i],extension);
        if(access(validPath,X_OK) == 0){
            return validPath;
        } 
        free(validPath);
    }
    
    return "No valid path";
    
}

// concatentates paths
char *concatPath(char *first, char *second){ // Makes a concatted copy
    char *combined = (char*)malloc(512 * sizeof(char)); // Allocates buffer memory
    strcpy(combined,first);
    strcat(combined,"/");
    strcat(combined,second);

    //printf("%s\n",combined);

    return combined;
}

// Clears paths and inputline
void clearInputLine(char input[NUM_EXE][NUM_COMMANDS][WORD_SIZE]){ 
    for(int i = 0; i < NUM_EXE; i++){
        for(int j = 0; j < NUM_COMMANDS; j++){
            for(int k = 0; k < WORD_SIZE; k++){
                input[i][j][k] = '\0';
            }
        }
    }
}

// Clears paths and inputline
void clearPath(char input[NUM_COMMANDS][WORD_SIZE]){ 
    for(int i = 0; i < NUM_EXE; i++){
        for(int j = 0; j < WORD_SIZE; j++){
            input[i][j] = '\0';
        }
    }
}

 // Prints inputline
void pInputLine(int line){
    for(int i = 0; i  < INPUTWORD_COUNT[CurrentExe]; i++){
        printf("%d:%s\n",line,inputLine[CurrentExe][i]);
    }
}

// Adds file at path
void addFile(char *inputPath){ 
    FILE *fpWriter = fopen(inputPath,"w+");
    fwrite("\0", 1, sizeof("\0"), fpWriter);
    fclose(fpWriter);
}