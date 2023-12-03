#include <stdio.h>

int flag = 0;
// Throw Error
void throw_error(char *errorMsg){
        if(flag == 0){
                fprintf(stderr,"An error has occurred\n");
        }
        else {
                fprintf(stderr,"%s\n",errorMsg);
        }
}

// File error
void throw_error_noFile(char *errorMsg){    
        if(flag == 0){
                fprintf(stderr,"ls: cannot access '/no/such/file': No such file or directory\n");
        }
        else {
                fprintf(stderr,"%s\n",errorMsg);
        }
}

// End of File Check
int EOFCheck(FILE *fp){
    int running = 1;
    if(fgetc(fp) == EOF){ 
       running = 0;
    }
    fseek(fp,-1,SEEK_CUR);
    return running;
}