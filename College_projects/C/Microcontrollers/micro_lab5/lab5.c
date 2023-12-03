/* 
 * File:   lab5.c
 * Author: lclem
 *
 * Created on November 20, 2023
 */

#include <stdio.h>
#include <stdlib.h>
#include <xc.h>

// Function Definitions
void setup();
void switch_mode(int mode);
void delay(int timeDelay);
int input();
void colorCycle();
void colorCycle2();
void showColor(char R, char G, char B, int time);

/*
 * Main function of the program
 */
int main(int argc, char** argv) {

    setup();
    int time = 0;
    while(1){
        int mode = input();
        switch_mode(mode);
    }
    return (EXIT_SUCCESS);
}

// Delay function
void delay(int timeDelay){
    for(int i = 0; i < timeDelay; i++){}   
}

// Sets up all the bits for input/output
void setup(){
    
    ANSELDbits.ANSD2 = 0;
    ANSELDbits.ANSD3 = 0;
    TRISDbits.TRISD2 = 0;
    TRISDbits.TRISD12 = 0;
    TRISDbits.TRISD3 = 0;
    RPD2R = 0;
    RPD12R = 0;
    RPD3R = 0;
    LATDbits.LATD2 = 1;
    LATDbits.LATD12 = 1;
    LATDbits.LATD3 = 1;
    
    
    TRISFbits.TRISF3 = 1; // RF3 (SW0) configured as input
    TRISFbits.TRISF5 = 1; // RF5 (SW1) configured as input
    TRISFbits.TRISF4 = 1; // RF4 (SW2) configured as input
    TRISDbits.TRISD15 = 1; // RD15 (SW3) configured as input
    TRISDbits.TRISD14 = 1; // RD14 (SW4) configured as input
}

// Uses SWE2 and SWE3 to change the mode to toggle between lab questions
void switch_mode(int mode){
    int time = 1000;
    switch(mode){
        case 0: showColor(1,1,1,time);
        break;
        case 1: showColor(50,1,1,time);
        break;
        case 2: showColor(1,50,1,time);
        break;
        case 3: showColor(1,1,50,time);
        break;
        case 4: showColor(50,50,1,time);
        break;
        case 5: showColor(1,80,40,time);
        break;
        case 6: showColor(50,1,50,time);
        break;
        case 7: colorCycle();
        break;
        case 8: colorCycle2();
        break;
    }
}

// Takes input from the SWE3 and SW2 switches
int input(){
    int val0 = PORTFbits.RF3; // read SW2
    int val1 = PORTFbits.RF5; // read SW3
    int val2 = PORTFbits.RF4; // read SW3
    int val3 = PORTDbits.RD15; // RD15 (SW3) configured as input
    
    return val0*1 + val1*2 + val2*4 + val3*8;
}

// Shows a specific color
void showColor(char R, char G, char B, int time){
    for(int i = 0; i < time; i++){
        LATDbits.LATD2 = 1;
        delay(R);
        LATDbits.LATD2 = 0;
        LATDbits.LATD12 = 1;
        delay(G);
        LATDbits.LATD12 = 0;
        LATDbits.LATD3 = 1;
        delay(B);
        LATDbits.LATD3 = 0;
    }
}

// Cycles in continuous steps
void colorCycle(){
    char R = 0;
    char G = 0;
    char B = 0;
    while(1){
        showColor(R,G,B,10);
        delay(100);
        if(R < 100)
            R++;
        else if(G < 100)
            G++;
        else if(B < 100)
            B++;
        else{
            break;
        }
    }
}

// Cycles in discrete steps
void colorCycle2(){
    int color = 0;
    int time = 2000;
    while(color <= 7){
        switch(color){
            case 0: showColor(10,10,10,time);
            break;
            case 1: showColor(50,1,1,time);
            break;
            case 2: showColor(1,50,1,time);
            break;
            case 3: showColor(1,1,50,time);
            break;
            case 4: showColor(50,50,1,time);
            break;
            case 5: showColor(1,20,10,time);
            break;
            case 6: showColor(20,1,40,time);
            break;
            case 7: showColor(40,1,20,time);
            break;   
        }
        delay(100);
        color++;
    }
}

