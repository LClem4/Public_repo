#pragma pack(1)

typedef struct {
    int number;
    char character;
} int_char;

typedef struct {
    char *filePiece; 
    char *filePiece2; 
    int totalFileNum; 
    int currentFileNum; 
    int getPrev; 
    int_char *buffer; 
    char *prev; 
    int *tally; 
    long *bufferlen;
} worker_args;

void printFile(FILE *fp, int_char *buffer, int totalFileNum, int currentFileNum, char *prev, char *check, int *tally, long *bufferlen);
void printFileMulti(char *filePiece, char *filePiece2, int totalFileNum, int currentFileNum, int getPrev, int_char *buffer, char *prev, int *tally, long *bufferlen);
void divideFile(int fd, off_t fsize, int totalFileNum, int currentFileNum, char *prev, char *check, int *tally);
void setVar(worker_args *threadArgs, char *filePiece, char *filePiece2, int totalFileNum, int currentFileNum, int getPrev, int_char *buffer, char *prev, int *tally, long *bufferlen);
void *worker(void *args);