#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <dirent.h>
#include <fcntl.h>
#include "readline.h"
#include <pthread.h>

extern char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE];
extern char path[NUM_COMMANDS][WORD_SIZE];
extern int PATH_COUNT;
extern int INPUTWORD_COUNT[NUM_EXE];
extern char CWD[CWD_SIZE];
extern int EXE_COUNT;
extern int flag;

int CurrentExe = 0;

// Third way to run commands
int runCommand3(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum){
    int check = 1;
    pid_t pid[EXE_COUNT];

    if(EXE_COUNT > 1){
        while(CurrentExe < EXE_COUNT){
            pid[CurrentExe] = fork();
            if(pid[CurrentExe] == 0){
                check = runCommand(inputLine,lineNum);
                exit(EXIT_SUCCESS);
            }
            else{

            }
            CurrentExe++;
        }
    }
    else {
        check = runCommand2(inputLine, lineNum);
    }

    int status;
    for (int i = 0; i < EXE_COUNT; i++) {
        pid_t wpid = waitpid(-1, &status, 0);
    }
    CurrentExe = 0;

    return check;
}


// Second way to run commands
int runCommand2(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum){
     int check = 0;
     while(CurrentExe < EXE_COUNT){
        check = runCommand(inputLine,lineNum);
        CurrentExe++;
     }
     CurrentExe = 0;

     return check;
}


// Way to run commands not in parallel
int runCommand(char inputLine[NUM_EXE][NUM_COMMANDS][WORD_SIZE], int lineNum){
    if(commandCheck("exit",inputLine[CurrentExe][0])){ // Exit command
        commandExit(inputLine[CurrentExe]);
        return 0;
    }
    else if(commandCheck("&",inputLine[CurrentExe][0])){ // cd command

    }
    else if(commandCheck("cd",inputLine[CurrentExe][0])){ // cd command
        commandcd(inputLine[CurrentExe][1]);
    }
    else if(commandCheck("ls",inputLine[CurrentExe][0])){ // ls command
        commandls(inputLine[CurrentExe]);
    }
    else if(commandCheck("path",inputLine[CurrentExe][0])){ // path command
        commandpath(inputLine[CurrentExe]);
    }
    else if(commandCheck("rm",inputLine[CurrentExe][0])){ // rm command
        commandrm(inputLine[CurrentExe]);
    }
    else if(commandCheck("cat",inputLine[CurrentExe][0])){ // cat command
        commandcat(inputLine[CurrentExe]);
    }
    else if(commandCheck("echo",inputLine[CurrentExe][0])){ // cat command
        commandecho(inputLine[CurrentExe]);
    }
    else if(commandCheck(">",inputLine[CurrentExe][1]) && inputLine[CurrentExe][2][0] != '\0'){ // print directory command
        int save_stdout = dup(1);   // Saves stdout
        addFile(inputLine[CurrentExe][2]);
        char *argv2[] = {NULL, NULL};
        commandExe(inputLine[CurrentExe][0], argv2, 2);
        dup2(save_stdout, 1); // restores stdout
        close(save_stdout);  
    }
    else if(commandCheck("",inputLine[CurrentExe][0])){ // print directory command   
    }
    else {								// other
        char *argv2[] = {NULL, NULL};
        commandExe(inputLine[CurrentExe][0], argv2, 2);
    }
    return 1;
}

// Exit command
int commandExit(char inputLine[NUM_COMMANDS][WORD_SIZE]){
    if(inputLine[1][0] != '\0'){
            throw_error("Bad Exit");
    }
    return 0;   
}

void commandcd(char *inputWord){	
    int checkDir = chdir(inputWord);

    if(checkDir == -1) {
        throw_error("Bad Directory");
    }
}

// ls command
void commandls(char inputLine[NUM_COMMANDS][WORD_SIZE]){
        if(inputLine[1][0] == '>'){
            throw_error("Bad ls");     
        }
        else if(inputLine[1][0] != '\0' && inputLine[2][0] == '>' && inputLine[3][0] != '\0'){  
            int save_stdout = dup(1);   // Saves stdout
            addFile(inputLine[3]);
            char *argv2[] = {NULL, inputLine[1], NULL};
            commandExe("ls", argv2, 3); 
        } 
        else if(inputLine[1][0] != '\0'){
            throw_error_noFile("Bad ls file");
        }
        else if(inputLine[1][0] == '\0'){
            char *argv2[] = {NULL, NULL};
            commandExe("ls", argv2, 2);
        }  
        else{
            throw_error("Bad ls"); // fix this
        }  
}

// rm command
void commandrm(char inputLine[NUM_COMMANDS][WORD_SIZE]){
    if(access(inputLine[2],F_OK) == 0){
            char *argv2[] = {NULL, inputLine[1], inputLine[2], NULL};
            commandExe("rm",argv2, 4);
    }
}

// cat command
void commandcat(char inputLine[NUM_COMMANDS][WORD_SIZE]){
    if(access(inputLine[1],R_OK) == 0){
        char *argv2[] = {NULL, inputLine[1], NULL};
        commandExe("cat",argv2, 3);
    }
    
} 

// echo command
void commandecho(char inputLine[NUM_COMMANDS][WORD_SIZE]){

    char *echoWords[NUM_COMMANDS];
    int wordNum = INPUTWORD_COUNT[CurrentExe];
    for(int i = 0; i  < wordNum; i++){
       echoWords[i] = inputLine[i];
    }
    echoWords[wordNum] = NULL;
    commandExe("echo",echoWords,wordNum);
}

// set path command
void commandpath(char inputLine[NUM_COMMANDS][WORD_SIZE]){
    int numPaths = 0;
    clearPath(path);
    
    for(int i = 0; i < INPUTWORD_COUNT[CurrentExe]; i++){
        if(access(inputLine[i],X_OK) == 0){
            strcpy(path[numPaths],inputLine[i]);
            numPaths++;
        }
        else{
            // error statement
        }
    } 

    PATH_COUNT = numPaths;
}

// regular exe command
void commandExe(char *inputWord, char *argv2[], int argc2){
    char *exePath = searchValidPath(path,inputWord);

    argv2[0] = exePath;
    if(strcmp(exePath,"No valid path") != 0){  
        int pid= fork();
        if(pid < 0){
            throw_error("Bad fork"); 
        }
        else if(pid == 0 && strcmp(">",inputLine[CurrentExe][2]) == 0){
            int fd  = open(inputLine[CurrentExe][3], O_RDWR);
            if (fd < 0) {
                throw_error("frick, that's not supposed to break");
            }
            dup2(fd, 1);
            close(fd);
            execv(exePath, argv2);
        }
        else if(pid == 0 && inputLine[CurrentExe][1][0] == '>' && inputLine[CurrentExe][2][0] != '\0'){ 
            int fd  = open(inputLine[CurrentExe][2], O_RDWR);
            if (fd < 0) {
                throw_error("frick, that's not supposed to break");
            }
            dup2(fd, 1);
            close(fd);
            execv(exePath, argv2); 
        } 
        else if(pid == 0){
            execv(exePath, argv2); 
        }
        else{
            int rc_wait = wait(NULL);
            free(exePath);
        }
    }
    else{
        throw_error("Bad Exe");
    }  
}

// Checks which commands needs to be run
int commandCheck(char *commandword, char *inputWord){
    if(strcmp(commandword,inputWord) == 0){ // cd command
        return 1;
    }
    else{
        return 0;
    }
}