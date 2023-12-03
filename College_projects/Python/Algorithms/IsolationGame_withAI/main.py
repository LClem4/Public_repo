import pandas as pd
import sys
import gui
import heuristics
import pygame
import board
import numpy as np

# Author: Luke Clement
# Main function

# Global variable for processing adjacent tiles
neighbors = [(-1,-1), (-1,0), (-1,1),
             (0,-1),           (0,1),
             (1,-1), (1,0), (1,1)]

# Main classes for getting the program running
class Main:
    # initialization
    def __init__(self,rows,cols):
        if not len(sys.argv) == 3:
            print("Error: need 2 input parameters")
            exit(-1)
        self.first = sys.argv[1]
        self.second = sys.argv[2]
        if not( (self.first == "ai" or self.first == "human") and (self.second == "ai" or self.second == "human") ):
            print("Error: Incorrect inputs. See readme")
            exit(-1)
        self.gameboard = board.Board(rows,cols,1,4,8,3,self.first,self.second)
        self.display = gui.Display(rows,cols)
        self.heuristics = heuristics.heuristics()
        self.turn = "player1"
        self.whoGoFirst = "player2"
        self.player1wins = 0
        self.player2wins = 0
        self.numGames = 0
        self.counter = 0
        self.counters = []
        b, x1, y1, x2, y2 = self.formatBoard(self.gameboard)
        self.display.update(b, x1, y1, x2, y2)
        pygame.display.update() 
        
    # This is the first function run. Runs the main gameloop
    def main(self):  
        running = True
        check = "Not isolated"
        
        while running:
            self.display.CLOCK.tick(30)
            if check == "Not isolated":
                check = self.runGame()
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
            pygame.display.update() 
            
    # Switches player turn
    def switchTurn(self):
        if self.turn == "player1":
            self.turn = "player2"
        elif self.turn == "player2":
            self.turn = "player1"
        
    # Formats the board for the gui
    def formatBoard(self,gameboard):
         x1 = gameboard.player1.j
         y1 = gameboard.player1.i
         x2 = gameboard.player2.j
         y2 = gameboard.player2.i
         guiBoard = np.array(gameboard.gameboard)[1:-1,1:-1]
         return guiBoard,x1,y1,x2,y2
    
    # This is the primary function for moving the game forward
    def runGame(self):
        nextX, nextY, rx, ry = self. playerMove()
        self.gameboard.playerMove(self.turn,nextX,nextY,rx,ry)
        check = self.gameboard.checkIsolated(self.turn)
        b, x1, y1, x2, y2 = self.formatBoard(self.gameboard)
        self.display.update(b, x1, y1, x2, y2)

        if check == "isolated":
            print(str(self.turn) + " wins! in " + str(self.counter) + " moves")
            self.incrementScore()
            self.counters.append(self.counter)
            self.counter = 0
            self.changeWhoGoesFirst()
            self.turn = self.whoGoFirst
            self.gameboard.setBoard(1,4,8,3,self.first,self.second)
            b, x1, y1, x2, y2 = self.formatBoard(self.gameboard)
            self.display.update(b, x1, y1, x2, y2)
            self.numGames = self.numGames + 1
            if self.numGames < 50:
                check = "Not isolated"
            else:
                print(sum(self.counters)/len(self.counters))
                exit(0)
            return check
        self.switchTurn()
        self.counter = self.counter + 1
        return check
    
    # This function obtains the moves from players
    def playerMove(self):
        nextX, nextY, rx, ry = 0, 0, 0, 0
        if self.turn == "player1":
            if self.gameboard.player1.type == "ai":
                nextX, nextY, rx, ry = self.heuristics.run(self.gameboard,self.turn,6)
            elif self.gameboard.player1.type == "human":
                nextX, nextY, rx, ry = self.display.run(self.gameboard,self.turn)
        elif self.turn == "player2":
            if self.gameboard.player2.type == "ai":
                nextX, nextY, rx, ry = self.heuristics.run(self.gameboard,self.turn,6)
            elif self.gameboard.player2.type == "human":
                nextX, nextY, rx, ry = self.display.run(self.gameboard,self.turn)
        return nextX, nextY, rx, ry
    
    # Increments the score
    def incrementScore(self):
        if self.turn == "player1":
            self.player1wins = self.player1wins + 1
        elif self.turn == "player2":
            self.player2wins = self.player2wins + 1
        print("Player1: " + str(self.player1wins) + "  Player2: " + str(self.player2wins))

    def changeWhoGoesFirst(self):
        if self.whoGoFirst == "player1":
           self.whoGoFirst = "player2"
        elif self.whoGoFirst == "player2":
           self.whoGoFirst = "player1"


# Runs Main
main = Main(8,6)
main.main() 