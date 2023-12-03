#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/mman.h>
#include <pthread.h>
#include <time.h>
#include "functs.h"

// Prints a single file
void printFile(FILE *fp, int_char *buffer, int totalFileNum, int currentFileNum, char *prev, char *check, int *tally, long *bufferlen){  
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
            buffer[*bufferlen].number = *tally;
            buffer[*bufferlen].character = *prev;
            (*bufferlen)++;
            *prev = *check;
            *tally = 1;
        }
    }
}

// Prints multiple files
void printFileMulti(char *filePiece, char *filePiece2, int totalFileNum, int currentFileNum, int getPrev, int_char *buffer, char *prev, int *tally, long *bufferlen){  
    char prev2;
    char *check2 = filePiece;
    int tally2;

    if(getPrev == 1 && currentFileNum > 1){
        prev2 = *prev;
        tally2 = *tally;
        //exit(EXIT_FAILURE);
    }
    else{
        prev2 = *filePiece;
        check2++;
        tally2 = 1;
    }
    while(check2 < filePiece2){
        //printf("%c\n",*check2);
        if( (*check2) == prev2){
            tally2++;
        }
        else{
            buffer[*bufferlen].number = tally2;
            buffer[*bufferlen].character = prev2;
            (*bufferlen)++;
            prev2 = *check2;
            tally2 = 1;
        }
        check2++;
    }
    if(getPrev == 2 && currentFileNum != totalFileNum-1){
        *prev = prev2;
        *tally = tally2;
    }
    else{
        buffer[*bufferlen].number = tally2;
        buffer[*bufferlen].character = prev2;
        (*bufferlen)++;
    } 
}

// Compresses multiple files in parallel
void divideFile(int fd, off_t fsize, int totalFileNum, int currentFileNum, char *prev, char *check, int *tally){
    int offset = (int)(fsize/3);
    char *src = mmap(0, fsize, PROT_READ, MAP_PRIVATE, fd, 0);
    char *src2 = src + offset;
    while(*src2 == *(src + offset - 1)){
        src2++;
    }
    char *src3 = src2 + offset;
    while(*(src3) == *(src2 + offset - 1)){
        src3++;
    }
    char *src4 = (src + fsize);

    int_char *buffer1 = (int_char *)malloc(sizeof(int_char) * offset);
    long *bufferlen1 = malloc(sizeof(long));
    *bufferlen1 = 0;
    int_char *buffer2 = (int_char *)malloc(sizeof(int_char) * offset);
    long *bufferlen2 = malloc(sizeof(long));
    *bufferlen2 = 0;
    int_char *buffer3 = (int_char *)malloc(sizeof(int_char) * offset);
    long *bufferlen3 = malloc(sizeof(long));
    *bufferlen3 = 0;
    
    worker_args *threadArgs1 = malloc(sizeof(worker_args));
    setVar(threadArgs1, src, src2, totalFileNum, currentFileNum, 1, buffer1, prev, tally, bufferlen1);
    worker_args *threadArgs2 = malloc(sizeof(worker_args));
    setVar(threadArgs2, src2, src3, totalFileNum, currentFileNum, 0, buffer2, prev, tally, bufferlen2);
    worker_args *threadArgs3 = malloc(sizeof(worker_args));
    setVar(threadArgs3, src3, src4, totalFileNum, currentFileNum, 2, buffer3, prev, tally, bufferlen3);

    pthread_t threads[3];
    pthread_create(&threads[0], NULL, worker, threadArgs1);
    pthread_create(&threads[1], NULL, worker, threadArgs2);
    pthread_create(&threads[2], NULL, worker, threadArgs3);
    pthread_join(threads[0], NULL);
    pthread_join(threads[1], NULL);
    pthread_join(threads[2], NULL);

    fwrite(buffer1,sizeof(int_char),*bufferlen1,stdout);
    fwrite(buffer2,sizeof(int_char),*bufferlen2,stdout);
    fwrite(buffer3,sizeof(int_char),*bufferlen3,stdout);

    free(buffer1); free(buffer2); free(buffer3);
    free(bufferlen1); free(bufferlen2); free(bufferlen3);
    free(threadArgs1); free(threadArgs2); free(threadArgs3);
}

// Void pointer function
void *worker(void *args){
    worker_args *actual_args = args;
    printFileMulti(actual_args->filePiece, 
        actual_args->filePiece2, 
        actual_args->totalFileNum, 
        actual_args->currentFileNum, 
        actual_args->getPrev,  
        actual_args->buffer,  
        actual_args->prev, 
        actual_args->tally, 
        actual_args->bufferlen);
    return NULL;
}

// Sets variables
void setVar(worker_args *threadArgs, char *filePiece, char *filePiece2, int totalFileNum, int currentFileNum, int getPrev, int_char *buffer, char *prev, int *tally, long *bufferlen){
    threadArgs->filePiece = filePiece;
    threadArgs->filePiece2 = filePiece2;
    threadArgs->totalFileNum = totalFileNum;
    threadArgs->currentFileNum = currentFileNum;
    threadArgs->getPrev = getPrev;
    threadArgs->buffer = buffer;
    threadArgs->prev = prev;
    threadArgs->tally = tally;
    threadArgs->bufferlen = bufferlen;
}