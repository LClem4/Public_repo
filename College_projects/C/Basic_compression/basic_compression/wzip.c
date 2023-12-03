#include <stdio.h>
#include <stdlib.h>

void printByChar(FILE *fp, int totalFileNum, int currentFileNum, char *prev, char *check, int *tally);
void sortFilesByChar(int argc, char *argv[]);

// Main function
int main(int argc, char *argv[]){

    if(argc == 1){
        printf("wzip: file1 [file2 ...]\n");
        return 1;
    }
 
    sortFilesByChar(argc,argv);

    return 0;   
}

// Sorts files
void sortFilesByChar(int argc, char *argv[]){
    char *prev = malloc(sizeof(char));
    char *check = malloc(sizeof(char));
    int *tally = malloc(sizeof(int));
    for(int i = 1; i < argc; i++){
        FILE *fp = fopen(argv[i],"r");
        printByChar(fp, argc, i, prev, check, tally);
        fclose(fp);
    }
}

// Write compression to stdout
void printByChar(FILE *fp, int totalFileNum, int currentFileNum, char *prev, char *check, int *tally){  
    if((currentFileNum) == 1){
        *prev = fgetc(fp);
        *tally = 1;
    }
    while( (*check) != EOF){
        //printf("%s\n",check);
        *check = fgetc(fp);
        if( (*check) == EOF && currentFileNum != totalFileNum-1){
            *check = '\0';
            break;
        }
        else if( (*check) == (*prev)){
            (*tally)++;
        }
        else{
            fwrite(tally,sizeof(int),1,stdout);
            fwrite(prev,sizeof(char),1,stdout);
            *prev = *check;
            *tally = 1;
        }
    }
}