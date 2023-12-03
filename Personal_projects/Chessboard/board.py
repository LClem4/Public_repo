import pygame
import piece
import settings

GREEN_BOARD = (12, 168, 7)
PURPLE_BOARD = (115, 7, 130)
BLUE_BOARD = (25, 25, 255)
BLACK = (0,0,0)

# Board class
class Board():
    def __init__(self):
        self.board = [[Tile(piece.Piece()) for x in range(8)] for y in range(8)]
        self.moves = []
        self.last_move = [(-1,-1),(-1,-1)]
        self.active_piece = ()
        for i in range(8):
            self.board[i][1].piece = piece.Pawn('black')
            self.board[i][6].piece = piece.Pawn('white')
        self.board[0][0].piece = piece.Rook('black')
        self.board[1][0].piece = piece.Knight('black')
        self.board[2][0].piece = piece.Bishop('black')
        self.board[3][0].piece = piece.Queen('black')
        self.board[4][0].piece = piece.King('black')
        self.board[5][0].piece = piece.Bishop('black')
        self.board[6][0].piece = piece.Knight('black')
        self.board[7][0].piece = piece.Rook('black')

        self.board[0][7].piece = piece.Rook('white')
        self.board[1][7].piece = piece.Knight('white')
        self.board[2][7].piece = piece.Bishop('white')
        self.board[3][7].piece = piece.Queen('white')
        self.board[4][7].piece = piece.King('white')
        self.board[5][7].piece = piece.Bishop('white')
        self.board[6][7].piece = piece.Knight('white')
        self.board[7][7].piece = piece.Rook('white')
       
    # Draws board
    def drawGrid(self,SCREEN,BLOCKSIZE):  
        for x in range(0, 8):
            for y in range(0, 8):
                self.board[x][y].update(SCREEN,BLOCKSIZE,x,y,False)
    
    # Highlights tile
    def drawGridHighlight(self,SCREEN,BLOCKSIZE,moves): 
        if moves:
            for i in moves:
                x = i[0]
                y = i[1]
                self.board[x][y].update(SCREEN,BLOCKSIZE,x,y,True)
    
    # Clears highlight
    def clearHighlights(self):
        for x in range(0, 8):
            for y in range(0, 8):
                self.board[x][y].clearHighlight()

    # Returns piece color
    def checkSpace(self,x,y):
        return self.board[x][y].piece.color

    # Checks piece type
    def checkPieceType(self,x,y,type):
        return self.board[x][y].piece.piece_type == type
    
    # Checks for EnPennsant
    def checkEnPennsant(self,checkX,checkY):
        checkXs = checkX == self.last_move[0][0] and checkX == self.last_move[0][1]
        if(self.checkSpace(checkX,checkY) == 'black'):
            if( (self.last_move[0][0] + self.last_move[0][1])/2 == checkY and checkXs and self.checkPieceType(checkX,checkY-1,"P")):
                 self.board[checkX][checkY-1] = Tile(piece.Piece())
        else:
            if( (self.last_move[0][0] + self.last_move[0][1])/2 == checkY and checkXs and self.checkPieceType(checkX,checkY+1,"P")):
                 self.board[checkX][checkY+1] = Tile(piece.Piece())

    # Gets last move
    def check_last_move(self,checkX,checkY,type,offset): # Mainly for pawns
        if( not(self.checkPieceType(checkX+offset,checkY,type)) ):
           return False
        elif( not(self.checkSpace(checkX+offset,checkY) != self.checkSpace(checkX,checkY)) ):
           return False
        elif( not(self.checkSpace(checkX+offset,checkY) != 'none') ):
           return False
        elif( not(self.last_move[0][1] == checkY+offset*2) ):
           return False
        else:
           self.en_passent_moves = (checkX+offset,checkY)
           return True
    
    # Returns options for moving
    def move_options(self,SCREEN,BLOCKSIZE):
        if(settings.MOUSE.pressed()):
            for i in self.moves:
                if(i[0] == settings.MOUSE.x and i[1] == settings.MOUSE.y):
                    self.move_piece(self.active_piece[0],self.active_piece[1],i[0],i[1])
                    self.switch_turn()
            if(self.checkSpace(settings.MOUSE.x,settings.MOUSE.y) == settings.TURN):
                self.moves = self.board[settings.MOUSE.x][settings.MOUSE.y].piece.move_options(self,settings.MOUSE.x,settings.MOUSE.y)
                self.active_piece = (settings.MOUSE.x, settings.MOUSE.y)
        self.drawGridHighlight(SCREEN,BLOCKSIZE,self.moves)

    # Moves piece
    def move_piece(self,x,y,x2,y2):
        self.board[x2][y2] = self.board[x][y]
        self.board[x][y] = Tile(piece.Piece())
        self.update_lastmove(x,y,x2,y2)
   
        if(self.checkPieceType(x2,y2,'K')):
            if(x2 - x == 2):
                self.move_piece(x2+1,y,x2-1,y)
            elif(x2 - x == -2):
                self.move_piece(x2-2,y,x2+1,y)
                self.board[x2][y2].piece.castle = False
        if(self.checkPieceType(x2,y2,'R')):
            self.board[x2][y2].piece.castle = False
        if(self.checkPieceType(x2,y2,'P')):
            self.checkEnPennsant(x2,y2)

    # Switches turn
    def switch_turn(self):
        if(settings.TURN == 'white'):
            settings.TURN = 'black'
        else:
            settings.TURN = 'white'
        self.moves = []
        self.active_piece = ()

    # Updates last move
    def update_lastmove(self,x,y,x2,y2):
        self.last_move = [(x,y),(x2,y2)]

    # Updates the board        
    def update(self,SCREEN,BLOCKSIZE):
        self.drawGrid(SCREEN,BLOCKSIZE)
        self.move_options(SCREEN,BLOCKSIZE)
        
# Tile class    
class Tile():
      def __init__(self, piece):
          self.piece = piece
          
      def drawSquare(self,SCREEN,BLOCKSIZE,x,y):
          rect = pygame.Rect(x*BLOCKSIZE, y*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE)
          if(settings.MOUSE.x == x and settings.MOUSE.y == y):
                pygame.draw.rect(SCREEN, BLUE_BOARD, rect)
          elif( (x + y) % 2 == 0):
                pygame.draw.rect(SCREEN, GREEN_BOARD, rect)
          else:
                pygame.draw.rect(SCREEN, PURPLE_BOARD, rect)
        
      def addHighlight(self,SCREEN,BLOCKSIZE,x,y):
            rect = pygame.Rect(x*BLOCKSIZE, y*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE)
            pygame.draw.rect(SCREEN, BLUE_BOARD, rect, 1)
              
      def addPiece(self,SCREEN,BLOCKSIZE,x,y):
            self.piece.draw(SCREEN,BLOCKSIZE,x,y)
            
      def drawTile(self,SCREEN,BLOCKSIZE,x,y,highlight):
          self.drawSquare(SCREEN,BLOCKSIZE,x,y)
          if(highlight):
            self.addHighlight(SCREEN,BLOCKSIZE,x,y)
          self.addPiece(SCREEN,BLOCKSIZE,x,y)
    
      def update(self,SCREEN,BLOCKSIZE,x,y,highlight):
          self.drawTile(SCREEN,BLOCKSIZE,x,y,highlight)