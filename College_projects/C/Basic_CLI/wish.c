#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include "readline.h"

extern char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE];
extern int INPUTWORD_COUNT[NUM_EXE];

char path[NUM_COMMANDS][WORD_SIZE];
int PATH_COUNT = 1;

char CWD[CWD_SIZE];

// Main function
int main(int argc, char *argv[]){
    int running = 1; 
    int lineNum = 0;
    strcat(path[0],"/bin");
    strcat(path[1],"/usr/bin");
    getcwd(CWD, sizeof(CWD));

    FILE *fp = fopen(argv[1],"r"); 

    while(running == 1){
        if(argc == 1){
            printf("wish>");
            sortinputLine(stdin);
        }
        else if(fp != NULL){
            if(sortinputLine(fp) == -1 && EOFCheck(fp) == 0){
                throw_error("There is nothing?");
                return 1;
            }
            running = EOFCheck(fp);
        }
        else{
            throw_error("Error in Main");
            return 1;
        }
        
        running = runCommand3(inputLine,lineNum) && running == 1;
        //printf("%d",running);
        lineNum++;
    }

    return 0;
}