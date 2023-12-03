/* 
 * File:   lab3.c
 * Author: lclem
 *
 * Created on November 6, 2023, 8:21 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <xc.h>

// Function Definitions
void delay(int timeDelay);
void setup();
void reset_pins();
void set_pins(int LED);
void set_segment_display();
void display_num(int number);
void switches();
void switch_mode(int mode);
void display_order(int order);

/*
 * Main function of the program
 */
int main(int argc, char** argv) {

    setup();
    reset_pins();
    set_segment_display();
    while(1){
        int mode = input();
        switch_mode(mode);
        reset_pins();
    }
    return (EXIT_SUCCESS);
}

// Delay function
void delay(int timeDelay){
    for(int i = 0; i < timeDelay; i++){}   
}

// Sets up all the bits for input/output
void setup(){
    TRISBbits.TRISB12 = 0; //RB12 set as output
    ANSELBbits.ANSB12 = 0; //RB12 analog functionality disabled
    TRISBbits.TRISB13 = 0; //RB13 set as output
    ANSELBbits.ANSB13 = 0; //RB13 analog functionality disabled
    
    TRISAbits.TRISA9 = 0; //RA9 set as output
    TRISAbits.TRISA10 = 0; //RA10 set as output
    TRISGbits.TRISG12 = 0; //RG12 set as output
    TRISAbits.TRISA14 = 0; //RA14 set as output
    TRISDbits.TRISD6 = 0; //RD6 set as output
    TRISGbits.TRISG13 = 0; //RG13 set as output
    TRISGbits.TRISG15 = 0; //RG15 set as output
    TRISDbits.TRISD7 = 0; //RD7 set as output
    TRISDbits.TRISD13 = 0; //RD13 set as output
    TRISGbits.TRISG14 = 0; //RG14 set as output
    
    TRISFbits.TRISF3 = 1; // RF3 (SW0) configured as input
    TRISFbits.TRISF5 = 1; // RF5 (SW1) configured as input
    TRISFbits.TRISF4 = 1; // RF4 (SW2) configured as input
    TRISDbits.TRISD15 = 1; // RD15 (SW3) configured as input
    TRISDbits.TRISD14 = 1; // RD14 (SW4) configured as input
}

// Sets up the 7 segment display so that only the first position is shown
void set_segment_display(){
   LATBbits.LATB12 = 0; //RB12 set as output
   LATBbits.LATB13 = 1; //RB13 set as output
   LATAbits.LATA9 = 1; //RA9 set as output
   LATAbits.LATA10 = 1; //RA10 set as output
}

// Clears all the pins of the 7 segment display
void reset_pins(){ 
    LATGbits.LATG12 = 1; //RG12 set as output
    LATAbits.LATA14 = 1; //RA14 set as output
    LATDbits.LATD6 = 1; //RD6 set as output
    LATGbits.LATG13 = 1; //RG13 set as output
    LATGbits.LATG15 = 1; //RG15 set as output
    LATDbits.LATD7 = 1; //RD7 set as output
    LATDbits.LATD13 = 1; //RD13 set as output
    LATGbits.LATG14 = 1; //RG14 set as output
}

// Sets the pins that are needed for the 7 segment display
void set_pins(int LED){
    if (LED & 1){ LATGbits.LATG12 = 0; } // top
    if (LED & 2){ LATAbits.LATA14 = 0; } // top right
    if (LED & 4){ LATDbits.LATD6 = 0; } // bottom right
    if (LED & 8){ LATGbits.LATG13 = 0; } // bottom
    if (LED & 16){ LATGbits.LATG15 = 0; } // bottom left
    if (LED & 32){ LATDbits.LATD7 = 0; } // top right
    if (LED & 64){ LATDbits.LATD13 = 0; } // middle
    if (LED & 128){LATGbits.LATG13 = 0; } // dot 
}

// Displays a particular number on the seven segment display
void display_num(int number){
    int pins = 0x00;
    switch(number){
        case 0: pins = 0x3f;
        break;
        case 1: pins = 0x06;
        break;
        case 2: pins = 0x5B;
        break;
        case 3: pins = 0x4f;
        break;
        case 4: pins = 0x66;
        break;
        case 5: pins = 0x6d;
        break;
        case 6: pins = 0x7d;
        break;
        case 7: pins = 0x07;
        break;
        case 8: pins = 0xff;
        break;
        case 9: pins = 0x67;
        break;
    }
    set_pins(pins);
}

// This function is for #4 in the lab. It changes the display to 0, 1, or none depending on which switches are on
// It takes input from SW0 and SW1
void switches(){
    int val0 = PORTFbits.RF3; // RF3 (SW0) configured as input
    int val1 = PORTFbits.RF5; // RF5 (SW1) configured as input

    if(val0 == 1 && val1 == 0){
        display_num(0);
    }
    else if(val0 == 0 && val1 == 1){
        display_num(1);
    }
}

// Adds two numbers and then displays it
void add(int num1, int num2){
    int new_num = num1 + num2;
    display_num(new_num);
}

// Uses SWE2 and SWE3 to change the mode to toggle between lab questions
void switch_mode(int mode){
    switch(mode){
        case 0: display_increase();
        break;
        case 1: display_decrease();
        break;
        case 2: switches();
        break;
        case 3: add(6,2);
        break;
    }
}

// Function for displaying numbers in increasing order
int display_increase(){
    for(int i = 0; i <= 9; i++){
        display_num(i);
        delay(50000);
        reset_pins();
    }
}

// Function for displaying numbers in decreasing order
int display_decrease(){
    for(int i = 9; i >= 0; i--){
        display_num(i);
        delay(50000);
        reset_pins();
    }
}

// Takes input from the SWE3 and SW2 switches
int input(){
    int val2 = PORTFbits.RF4; // read SW2
    int val3 = PORTDbits.RD15; // read SW3
    
    return val2*1 + val3*2;
}

