wzip: wzip.o wzip.o sortfunctions.o
	gcc -pthread -o wzip wzip.o sortfunctions.o

wzip.o: wzip.c
	gcc -pthread -c wzip.c 

sortfunctions.o: sortfunctions.c
	gcc -pthread -c sortfunctions.c 
