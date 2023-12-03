# Project Information 
        Author: Luke Clement
        Assignment: Homework 3
        Last Updated: 12/3/2023
        Note: version Python 3.10.12 and pygame 2.5.2

# Description
        The purpose of this program was to design an Ai to play a two player game called isolation. The game works by moving to one of 8
        adjacent tiles and then you remove any tile. First person to eliminate all possble ways for their opponent to move wins.
        The game is played on an 8x6 board. The Ai makes decision by evaluationg all outcomes down to a certain depth and then
        picks the decision that would lead to the best case senario based on a given heuristic. 

# Run
        1. python3 main.py [ai or human] [ai or human] example: python3 main.py ai ai will put two ai's against each other. python3 main.py ai human will have you play against the ai.
        2. You can change the heuristic that each ai uses by going into board.py and changing "first" to "second" in self.player1/2 = Player(x1,y1,"first",type1)

# Gameplay notes
        1. When playing as a human, you first pick the tile you move to and then the tile to be removed. There is no highlighting but the game does 
           check for invalid moves and will not proceed until a valid space is selected.
        2. The top right character is player1 and the bottom left character is player2
        3. An ongoing score will be tracked in the console.

# Known Bugs
        1. There is a small chance of isolating an opponent and the game doesn’t immediately react, just continue playing and it will figure it out.
