/* 
 * File:   main.c
 * Author: Luke Clement
 * 
 * This code is used to implements parts 1 and 2 of lab 2
 * 
 * 10/25/23
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <xc.h>

/**
 * Define Functions
 */
void delay(int timeDelay);
void setUp();
void setLight(int LED, int onOFF);
int readInput();
int convertBinary(int binary);
int getNextBit(int binary, int shift);

/**
 * Main function
 * @return 
 */
int main(){
    setUp();
    while(1) {
        int input = readInput();
        int check = convertBinary(input);
        setLight(check,1);
        delay(10000);
        setLight(check,0);
    }
    return (EXIT_SUCCESS);
}

/**
 * Convert Binary and Gray
 * @param timeDelay
 */
int convertBinary(int binary){
    int grayCode = 0x00;
    int nextBit = getNextBit(binary, 0);
    grayCode = grayCode | nextBit;
    for(int i = 1; i < 8; i ++){
        grayCode = grayCode << 1;
        int currentBit = getNextBit(binary, i-1);
        int nextBit = getNextBit(binary, i);
        if(currentBit ^ nextBit){
            grayCode = grayCode | 0x01;
        }
    } 
    return grayCode;
}

/**
 * Gets the next bit from binary
 * @param timeDelay
 */
int getNextBit(int binary, int shift){
    int nextBit = 0x00;
    binary = binary << shift;
    if(binary & 0x80){
        nextBit = 0x01;
        return nextBit;
    }
    return nextBit;
}

/**
 * Adds a delay to functions
 * @param timeDelay
 */
void delay(int timeDelay){
    for(int i = 0; i < timeDelay; i++){}   
}

/**
 * This is the initial setup and it is also used
 * to clear everything
 */
void setUp(){
    DDPCONbits.JTAGEN = 0;
    ANSELBbits.ANSB11 = 0;
    ANSELBbits.ANSB10 = 0;
    ANSELBbits.ANSB9 = 0;
    TRISAbits.TRISA0 = 0;
    TRISAbits.TRISA1 = 0;
    TRISAbits.TRISA2 = 0;
    TRISAbits.TRISA3 = 0;
    TRISAbits.TRISA4 = 0;
    TRISAbits.TRISA5 = 0;
    TRISAbits.TRISA6 = 0;
    TRISAbits.TRISA7 = 0;
    LATAbits.LATA0 = 0;
    LATAbits.LATA1 = 0;
    LATAbits.LATA2 = 0;
    LATAbits.LATA3 = 0;
    LATAbits.LATA4 = 0;
    LATAbits.LATA5 = 0;
    LATAbits.LATA6 = 0;
    LATAbits.LATA7 = 0;  
    TRISFbits.TRISF3 = 1; // RF3 (SW0) configured as input
    TRISFbits.TRISF5 = 1; // RF5 (SW1) configured as input
    TRISFbits.TRISF4 = 1; // RF4 (SW2) configured as input
    TRISDbits.TRISD15 = 1; // RD15 (SW3) configured as input
    TRISDbits.TRISD14 = 1; // RD14 (SW4) configured as input
    TRISBbits.TRISB11 = 1; // RB11 (SW5) configured as input
    TRISBbits.TRISB10 = 1; // RB10 (SW6) configured as input
    TRISBbits.TRISB9 = 1; // RB9 (SW7) configured as input
}

/**
 * Sets/Clears LEDS based on the corresponding switch
 * @param LED - which LED
 * @param onOFF - 1 - on, 0 - off
 */
void setLight(int LED, int onOFF){
    if (LED & 1){ LATAbits.LATA0 = onOFF; }
    if (LED & 2){ LATAbits.LATA1 = onOFF; }
    if (LED & 4){ LATAbits.LATA2 = onOFF; }
    if (LED & 8){ LATAbits.LATA3 = onOFF; }
    if (LED & 16){ LATAbits.LATA4 = onOFF; }
    if (LED & 32){ LATAbits.LATA5 = onOFF; }
    if (LED & 64){ LATAbits.LATA6 = onOFF; }
    if (LED & 128){LATAbits.LATA7 = onOFF; }   
}

/**
 * How to read in input from switches
 * @return A number associated with what bits are active
 */
int readInput(){
    int val0 = PORTFbits.RF3; // read SW0
    int val1 = PORTFbits.RF5; // read SW1
    int val2 = PORTFbits.RF4; // read SW2
    int val3 = PORTDbits.RD15; // read SW3
    int val4 = PORTDbits.RD14; // read SW4
    int val5 = PORTBbits.RB11; // read SW5
    int val6 = PORTBbits.RB10; // read SW6
    int val7 = PORTBbits.RB9; // read SW7
    int allBits = val0*1 + val1*2 + val2*4 + val3*8 + val4*16 + val5*32 + val6*64 + val7*128;
    return allBits;
}

