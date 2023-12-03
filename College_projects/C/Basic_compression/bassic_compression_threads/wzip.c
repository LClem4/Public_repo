#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/mman.h>
#include <pthread.h>
#include <time.h>
#include "functs.h"

// Main
int main(int argc, char *argv[]){

    if(argc == 1){
        printf("wzip: file1 [file2 ...]\n");
        return 1;
    }

    char *prev = malloc(sizeof(char));
    char *check = malloc(sizeof(char));
    int *tally = malloc(sizeof(int));
    
    for(int i = 1; i < argc; i++){
        FILE *fp = fopen(argv[i],"r");
        int fd = fileno(fp);

        struct stat fileInfo;
        fstat(fd, &fileInfo);
        off_t fsize = fileInfo.st_size;

        if(fsize > 4096){
            divideFile(fd, fsize, argc, i, prev, check, tally);
        }
        else{
            long *bufferlen = malloc(sizeof(long));
            *bufferlen = 0;
            int_char *buffer = (int_char *)malloc(sizeof(int_char) * 50000000);
            printFile(fp, buffer, argc, i, prev, check, tally, bufferlen);
            fwrite(buffer,sizeof(int_char),*bufferlen,stdout);
            free(bufferlen);
            free(buffer);
        }
              
        fclose(fp);
    }

    free(prev);
    free(check);
    free(tally);

    return 0;   
}

/*
    clock_t start_t, end_t;
    double total_t;
    FILE *timeFile = fopen("./time_filez.txt", "a+");
 
    start_t = clock();
    sortFiles(argc,argv);
    end_t = clock();
    total_t = (double)(end_t - start_t) / CLOCKS_PER_SEC;
    fprintf(timeFile,"CPU Time: %f\n", total_t);
*/


