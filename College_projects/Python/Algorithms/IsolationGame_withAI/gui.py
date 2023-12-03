import pygame
import math

# Global variables for color, mouse input, and adjacent tiles
GREEN_BOARD = (12, 168, 7)
PURPLE_BOARD = (115, 7, 130)
RED_BOARD = (255, 0, 0)
BLUE_BOARD = (25, 25, 255)
BLACK = (0,0,0)
BLOCKSIZE = 50
MOUSE = pygame.mouse
neighbors = [(-1,-1), (-1,0), (-1,1),
             (0,-1),           (0,1),
             (1,-1), (1,0), (1,1)]

# This class is used for displaying the gameboard
class Display():
    # Initialization
    def __init__(self,cols,rows):
        WINDOW_HEIGHT = (cols*(BLOCKSIZE+1))+20
        WINDOW_WIDTH = (rows*(BLOCKSIZE+1))+20
        pygame.font.init()
        self.SCREEN = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
        self.SCREEN.fill(BLACK)
        self.CLOCK = pygame.time.Clock()

    # This draws the gameboard grid
    def drawGrid(self,BOARD,x1,y1,x2,y2):  
        for x in range(0, BOARD.shape[1]):
            for y in range(0, BOARD.shape[0]):
                self.drawSquare(BOARD,x,y)
        self.drawPlayer(PURPLE_BOARD,x1,y1)
        self.drawPlayer(RED_BOARD,x2,y2)
    
    # This draws and individual square of the grid
    def drawSquare(self,BOARD,x,y):
          rect = pygame.Rect(x*(BLOCKSIZE+1)+10, y*(BLOCKSIZE+1)+10, BLOCKSIZE, BLOCKSIZE)
          if BOARD[y,x] > 0:
                pygame.draw.rect(self.SCREEN, GREEN_BOARD, rect)
          elif BOARD[y,x] == 0:
                pygame.draw.rect(self.SCREEN, BLACK, rect)

    # This draws a player on the grid
    def drawPlayer(self,color,x,y):
          adjustX = x*(BLOCKSIZE+1)-15
          adjustY = y*(BLOCKSIZE+1)-15
          pygame.draw.circle(self.SCREEN, color, (adjustX,adjustY), 15)

    # This is used to get input from the player
    def run(self,BOARD,turn):
        if turn == "player1":
            playeri,playerj,player2i,player2j = BOARD.player1.i, BOARD.player1.j, BOARD.player2.i, BOARD.player2.j
        elif turn == "player2":
            playeri,playerj,player2i,player2j = BOARD.player2.i, BOARD.player2.j, BOARD.player1.i, BOARD.player1.j
        board = BOARD.gameboard
        nextX, nextY, rx, ry = 0,0,0,0
        nextX, nextY = self.clickMoveSquare(playeri,playerj,player2i,player2j,board)
        rx, ry = self.clickRemoveSquare(nextX,nextY,player2i,player2j,board)
        return nextX, nextY, rx, ry

    # This is used to click the tile to be moved to
    def clickMoveSquare(self,playeri,playerj,player2i,player2j,BOARD):
         while True:
            pos = pygame.mouse.get_pos()
            x, y = math.floor((pos[0]-10)/(BLOCKSIZE+1)), math.floor((pos[1]-10)/(BLOCKSIZE+1))
            for event in pygame.event.get():
            # handle MOUSEBUTTONUP    
                if event.type == pygame.MOUSEBUTTONDOWN:
                    #print(y,x)
                    if self.checkCordinates(y+1,x+1,playeri,playerj,player2i,player2j,BOARD) and not (y+1 == player2i and x+1 == player2j):
                        return y+1, x+1
                if event.type == pygame.QUIT:
                    exit(0)
            pygame.time.wait(100)

    # This is used to remove a tile                
    def clickRemoveSquare(self,nexti,nextj,player2i,player2j,BOARD):
         while True:
            pos = pygame.mouse.get_pos()
            x, y = math.floor((pos[0]-10)/(BLOCKSIZE+1)), math.floor((pos[1]-10)/(BLOCKSIZE+1))
            for event in pygame.event.get():
            # handle MOUSEBUTTONUP
                if event.type == pygame.MOUSEBUTTONDOWN:
                    #print(y,x)
                    if not (y+1 == nexti and x+1 == nextj) and not (BOARD[y+1][x+1] == 0) and not(y+1 == player2i and x+1 == player2j):
                        return y+1, x+1
                if event.type == pygame.QUIT:
                    exit(0)
            pygame.time.wait(100)
    
    # This is used to check the cordinates of a player
    def checkCordinates(self,nexti,nextj,playeri,playerj,player2i,player2j,BOARD):
        check = False
        for n in neighbors:
            if nexti == playeri+n[0] and nextj == playerj+n[1] and not (BOARD[nexti][nextj] == 0):
                 check = True
        return check
    
    # This is used to update the screen
    def update(self,BOARD,x1,y1,x2,y2):
        self.drawGrid(BOARD,x1,y1,x2,y2)

