#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

// Definitions of functions
int *create_matrix(int size);
void print_matrix(int* A, int size);
void matrix_multiply(int* A, int* B, int *C, int size);
void run_program(int *A, int *B, int size, int cutoff, double *timeBF, double *timeStrassen, double *timeStrassenBF);
char *getFile(int size);
void run_once(int *A, int *B, int size, int cutoff, char *pflag);
int power(int n);
int CUTOFF;

// Reads csv
void read_matrix_from_csv(char* filename, int* matrix, int size) {
    FILE* fp = fopen(filename, "r");
    if (fp == NULL) {
        printf("Helpful error message: %s is not having a good day\n", filename);
        return;
    }

    int i, j;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            if (fscanf(fp, "%d,", &matrix[i*size+j]) != 1) {
                printf("Error: with row %d, column %d\n", i, j);
                fclose(fp);
                return;
            }
        }
    }

    free(filename);
    fclose(fp);
} 

//  Reads example matrix
void read_test_matrix(char* filename, int* matrix, int* matrix2, int size) {
    FILE* fp = fopen(filename, "r");
    if (fp == NULL) {
        printf("%s is not found\n", filename);
        return;
    }
    
    fseek(fp, 89, SEEK_SET);
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (fscanf(fp, "%d,", &matrix[i*size+j]) != 1) {
                printf("Error: with row %d, column %d\n", i, j);
                fclose(fp);
                return;
            }
        }
    }
    fseek(fp, 34, SEEK_CUR);
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (fscanf(fp, "%d,", &matrix2[i*size+j]) != 1) {
                printf("Error: with row %d, column %d\n", i, j);
                fclose(fp);
                return;
            }
        }
    }
    
    
    fclose(fp);
}

// Strassen algorithm
void strassen(int *A, int *B, int *C, int size) {
    int half = size >> 1;
    int offset = (size*half);
    int offset2 = (offset+half);
    
    int *A00 = A;
    int *A01 = (A+half);
    int *A10 = (A+offset);
    int *A11 = (A+offset2);

    int *B00 = B;
    int *B01 = (B+half);
    int *B10 = (B+offset);
    int *B11 = (B+offset2);
    
    int *C00 = C;
    int *C01 = (C+half);
    int *C10 = (C+offset);
    int *C11 = (C+offset2);

    if ( size== 2) { 
        int s11 = *B00;
        int s12 = *A00;
        int s13 = *A11;
        int s14 = *B11;
        int m1 = (*A00 + *A11) * (s11 + *B11);
        int m2 = (*A10 + *A11) * s11;
        int m3 = s12 * (*B01 - *B11);
        int m4 = s13 * (*B10 - *B00);
        int m5 = (*A00 + *A01) * s14;
        int m6 = (*A10 - *A00) * (*B00 + *B01);
        int m7 = (*A01 - *A11) * (*B10 + *B11);

        *C00 = m1 + m4 - m5 + m7;
        *C01 = m3 + m5;
        *C10 = m2 + m4;
        *C11 = m1 + m3 - m2 + m6;
        return;
    }

    int halfxhalf = half*half;
    int *s1 = malloc(halfxhalf * sizeof(int) * 21);
    int *s2 = s1 + halfxhalf;
    int *s3 = s2 + halfxhalf;
    int *s4 = s3 + halfxhalf;
    int *s5 = s4 + halfxhalf;
    int *s6 = s5 + halfxhalf;
    int *s7 = s6 + halfxhalf;
    int *s8 = s7 + halfxhalf;
    int *s9 = s8 + halfxhalf;
    int *s10 = s9 + halfxhalf;
    int *s11 = s10 + halfxhalf;
    int *s12 = s11 + halfxhalf;
    int *s13 = s12 + halfxhalf;
    int *s14 = s13 + halfxhalf;
    int *M1 = s14 + halfxhalf;
    int *M2 = M1 + halfxhalf;
    int *M3 = M2 + halfxhalf;
    int *M4 = M3 + halfxhalf;
    int *M5 = M4 + halfxhalf;
    int *M6 = M5 + halfxhalf;
    int *M7 = M6 + halfxhalf;

    for(int i = 0; i < half; i++){
        int index1;
        int index2;
        int j;
        for(j = 0; j < half; j++){
            index1 = (i * half + j);
            index2 = (i * size + j);
            s11[index1] = B00[index2];
            s12[index1] = A00[index2];
            s13[index1] = A11[index2];
            s14[index1] = B11[index2];
            s1[index1] = s12[index1] + s13[index1];
            s2[index1] = s11[index1] + s14[index1];
            s3[index1] = A10[index2] + s13[index1];
            s4[index1] = B01[index2] - s14[index1];
            s5[index1] = B10[index2] - s11[index1];
            s6[index1] = s12[index1] + A01[index2];
            s7[index1] = A10[index2] - s12[index1];
            s8[index1] = s11[index1] + B01[index2];
            s9[index1] = A01[index2] - s13[index1];
            s10[index1] = B10[index2] + s14[index1];       
        }
     }
    
    strassen(s1, s2, M1, half);
    strassen(s3, s11, M2, half);  
    strassen(s12, s4, M3, half);
    strassen(s13, s5, M4, half);
    strassen(s6, s14, M5, half);
    strassen(s7, s8, M6, half);
    strassen(s9, s10, M7, half);
     
    for(int i = 0; i < half; i++){
        int index1;
        int index2;
        int j;
        for(j = 0; j < half; j++){
            index1 = (i * half + j);
            index2 = (i * size + j);
            C00[index2] = M1[index1] + M4[index1] - M5[index1] + M7[index1];
            C01[index2] = M3[index1] + M5[index1];
            C10[index2] = M2[index1] + M4[index1];
            C11[index2] = M1[index1] + M3[index1] - M2[index1] + M6[index1];
        }
    }

    free(s1); 
    return;
}

// Strassen and bruteforce algorithm
void strassenbruteforce(int *A, int *B, int *C, int size) {

    if ( size== CUTOFF) { 
        matrix_multiply(A, B, C,size);
        return;
    }

    int half = size >> 1;
    int offset = (size*half);
    int offset2 = (offset+half);
    
    int *A00 = A;
    int *A01 = (A+half);
    int *A10 = (A+offset);
    int *A11 = (A+offset2);

    int *B00 = B;
    int *B01 = (B+half);
    int *B10 = (B+offset);
    int *B11 = (B+offset2);
    
    int *C00 = C;
    int *C01 = (C+half);
    int *C10 = (C+offset);
    int *C11 = (C+offset2);

    int halfxhalf = half*half;
    int *s1 = malloc(halfxhalf * sizeof(int) * 21);
    int *s2 = s1 + halfxhalf;
    int *s3 = s2 + halfxhalf;
    int *s4 = s3 + halfxhalf;
    int *s5 = s4 + halfxhalf;
    int *s6 = s5 + halfxhalf;
    int *s7 = s6 + halfxhalf;
    int *s8 = s7 + halfxhalf;
    int *s9 = s8 + halfxhalf;
    int *s10 = s9 + halfxhalf;
    int *s11 = s10 + halfxhalf;
    int *s12 = s11 + halfxhalf;
    int *s13 = s12 + halfxhalf;
    int *s14 = s13 + halfxhalf;
    int *M1 = s14 + halfxhalf;
    int *M2 = M1 + halfxhalf;
    int *M3 = M2 + halfxhalf;
    int *M4 = M3 + halfxhalf;
    int *M5 = M4 + halfxhalf;
    int *M6 = M5 + halfxhalf;
    int *M7 = M6 + halfxhalf;

    for(int i = 0; i < half; i++){
        int index1;
        int index2;
        int j;
        for(j = 0; j < half; j++){
            index1 = (i * half + j);
            index2 = (i * size + j);
            s11[index1] = B00[index2];
            s12[index1] = A00[index2];
            s13[index1] = A11[index2];
            s14[index1] = B11[index2];
            s1[index1] = s12[index1] + s13[index1];
            s2[index1] = s11[index1] + s14[index1];
            s3[index1] = A10[index2] + s13[index1];
            s4[index1] = B01[index2] - s14[index1];
            s5[index1] = B10[index2] - s11[index1];
            s6[index1] = s12[index1] + A01[index2];
            s7[index1] = A10[index2] - s12[index1];
            s8[index1] = s11[index1] + B01[index2];
            s9[index1] = A01[index2] - s13[index1];
            s10[index1] = B10[index2] + s14[index1];       
        }
     }
    
    strassenbruteforce(s1, s2, M1, half);
    strassenbruteforce(s3, s11, M2, half);  
    strassenbruteforce(s12, s4, M3, half);
    strassenbruteforce(s13, s5, M4, half);
    strassenbruteforce(s6, s14, M5, half);
    strassenbruteforce(s7, s8, M6, half);
    strassenbruteforce(s9, s10, M7, half);
     
    for(int i = 0; i < half; i++){
        int index1;
        int index2;
        int j;
        for(j = 0; j < half; j++){
            index1 = (i * half + j);
            index2 = (i * size + j);
            C00[index2] = M1[index1] + M4[index1] - M5[index1] + M7[index1];
            C01[index2] = M3[index1] + M5[index1];
            C10[index2] = M2[index1] + M4[index1];
            C11[index2] = M1[index1] + M3[index1] - M2[index1] + M6[index1];
        }
    }

    free(s1); 
    return;
}

// Create matrix
int* create_matrix(int size) {
    int* ptr = malloc((size * size) * sizeof(int));
    int i, j;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            ptr[i * size + j] = 0;
        }
    }
    return ptr;
}

// Prints matrix
void print_matrix(int* A, int size) {
    int i, j;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            printf("%d ", A[i * size + j]);
        }
        printf("\n");
    }
}

// Bruteforce matrix multiplication
void matrix_multiply(int* A, int* B, int *C, int size) {
    int i, j, k;
    int index;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            index = i * size + j;
            C[index] = 0;
            for (k = 0; k < size; k++) {  
                C[index] += A[i * size + k] * B[k * size + j];
            }
        }
    }
    return;
}

// Main
int main(int argc, char *argv[]){
    double *total_tBF = malloc(sizeof(double));
    double *total_tStr = malloc(sizeof(double));
    double *total_tStrassenBF = malloc(sizeof(double));
    
    int size = 1024;
    if(argc > 1){
        int cutoff = 2;
        char *pflag = "";
        if(strcmp(argv[1],"Test") == 0){
          int *A = create_matrix(32);
          int *B = create_matrix(32);
          read_test_matrix("./example.csv", A, B, 32); // Dr. Armstrong input file
          if(argc > 2){
              pflag = argv[2];
          }
          run_once(A, B, 32, 4, pflag);
          return 0;
        }
        size = power(atoi(argv[1]));
        char *file1 = getFile(0); // DETERMINES WHICH MATRIX FILE IS RUN
        char *file2 = getFile(1); // DETERMINES WHICH MATRIX FILE IS RUN
        //char *file1 = "./test1.csv";
        //char *file2 = "./test2.csv";
        int *A = create_matrix(size);
        int *B = create_matrix(size);
        read_matrix_from_csv(file1, A, size);
        read_matrix_from_csv(file2, B, size);
    
        if(argc > 2){
            cutoff = power(atoi(argv[2]));
            if(argc > 3){
                pflag = argv[3];
            }
        }
        run_once(A, B, size, cutoff,pflag);
        return 0;
      
    }
    else{
        FILE *fp;
        fp = fopen("Tests/N10output.csv", "w+"); // NAME OF FILE TO BE OUTPUT
        
        fprintf(fp,"BruteForce VS Strassen,\n");
        fprintf(fp,"size, BruteForce, Strassen,\n");
        for(int i = 4; i <= size; i *= 2){
            char *file1 = getFile(0);
            char *file2 = getFile(1);
            int *A = create_matrix(i);
            int *B = create_matrix(i);
            read_matrix_from_csv(file1, A, i);
            read_matrix_from_csv(file2, B, i);
            run_program(A, B, i, 2, total_tBF, total_tStr, total_tStrassenBF);
            fprintf(fp,"%d, %f, %f,\n", i, *total_tBF, *total_tStr);
        }
        fprintf(fp,"\n");
        
        for(int i = 0; i < 9; i+=2){
            char *file1 = getFile(i);
            char *file2 = getFile(i+1);
            int *A = create_matrix(size);
            int *B = create_matrix(size);
            read_matrix_from_csv(file1, A, size);
            read_matrix_from_csv(file2, B, size);
            
            printf("%d\n",(i/2)+1);
            fprintf(fp,"trial = %d\n", (i/2)+1);
            fprintf(fp,"cutoff, BruteForce, Strassen, StrassenBF, \n");
            for(int j = 2; j < size; j = j*2){
                run_program(A, B, size, j, total_tBF, total_tStr, total_tStrassenBF);
                fprintf(fp,"%d, %f, %f, %f\n", CUTOFF, *total_tBF, *total_tStr, *total_tStrassenBF);
            }
            free(A); free(B); 
            fprintf(fp,"\n");
        }

        fclose(fp);
    }
    free(total_tBF); free(total_tStr); free(total_tStrassenBF);
    
    return 0;
}

// Gets time of each method
void run_program(int *A, int *B, int size, int cutoff, double *timeBF, double *timeStrassen, double *timeStrassenBF){

    CUTOFF = cutoff;
    
    int *C1 = create_matrix(size);
    int *C2 = create_matrix(size);
    int *C3 = create_matrix(size);
    
    clock_t start_tBF, end_tBF, start_tStr, end_tStr, start_tStrassenBF, end_tStrassenBF;
    
    if(cutoff == 2){
        start_tBF = clock();
        matrix_multiply(A, B, C1, size);
        end_tBF = clock();
        *timeBF = (double)(end_tBF - start_tBF) / CLOCKS_PER_SEC;
        
        start_tStr = clock();
        strassen(A, B, C2, size);
        end_tStr = clock();
        *timeStrassen = (double)(end_tStr - start_tStr) / CLOCKS_PER_SEC;
    }
    else {
        *timeBF = 0.0;
        *timeStrassen = 0.0;
    }
 
    start_tStrassenBF = clock();
    strassenbruteforce(A, B, C3, size);
    end_tStrassenBF = clock();
    *timeStrassenBF = (double)(end_tStrassenBF - start_tStrassenBF) / CLOCKS_PER_SEC;

    free(C1); free(C2); free(C3);
}

// Retrieves file
char *getFile(int size){
    char *matrix = (char *)malloc(sizeof(char)*32);
    sprintf(matrix, "%d", size);
    strcat(matrix,".csv");

    char *file = (char *)malloc(sizeof(char)*64);
    strcpy(file,"./matricesN12/Random_Matrix_");
    strcat(file,matrix);

    return file;
}

// Single check preprocessing
void run_once(int *A, int *B, int size, int cutoff, char *pflag){
    if(size < 2){
        printf("Error: argv[1]\n");
        return;
    }
    if(cutoff == 0 || cutoff > size){
        printf("Error: argv[2]\n");
        return;
    }
    
    CUTOFF = cutoff;
    int *C1 = create_matrix(size);
    int *C2 = create_matrix(size);
    int *C3 = create_matrix(size);

    clock_t start_tBF, end_tBF, start_tStr, end_tStr, start_tboth, end_tboth;
    double total_tBF, total_tStr, total_tboth;
    
    printf("Size = %d Cutoff = %d\n",size,CUTOFF);
    start_tBF = clock();
    matrix_multiply(A, B, C1, size);
    end_tBF = clock();
    if(strcmp(pflag,"-p") == 0){
        printf("bruteForce\n");
        print_matrix(C1, size);
    }
    total_tBF = (double)(end_tBF - start_tBF) / CLOCKS_PER_SEC;
    printf("BF Total time taken by CPU: %f\n", total_tBF);


    start_tStr = clock();
    strassen(A, B, C2, size);
    end_tStr = clock();
    if(strcmp(pflag,"-p") == 0){
        printf("strassen\n");
        print_matrix(C2, size);
    }
    total_tStr = (double)(end_tStr - start_tStr) / CLOCKS_PER_SEC;
    printf("Strassen Total time taken by CPU: %f\n", total_tStr );

    start_tboth = clock();
    strassenbruteforce(A, B, C3, size);
    end_tboth = clock();
    if(strcmp(pflag,"-p") == 0){
        printf("strassenbruteforce\n");
        print_matrix(C1, size);
    }
    total_tboth = (double)(end_tboth - start_tboth) / CLOCKS_PER_SEC;
    printf("Strassen and BF with Cutoff = %d Total time taken by CPU: %f\n", CUTOFF, total_tboth);
    free(A); free(B);
}

// Exponent function
int power(int n){
    int value = 2;
    for(int i = 1; i < n; i++){
        value = value * 2;
    }

    return value;
}
