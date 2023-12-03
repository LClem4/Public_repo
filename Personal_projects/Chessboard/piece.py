import pygame
import operator

BLACK_PIECE = (40, 40, 40)
WHITE_PIECE = (200, 200, 200) 

# Piece class
class Piece():
    # Initialize
    def __init__(self, color = 'none', piece_type=''):
        self.color = color
        self.piece_type = piece_type
        self.castle = False

        self.font = pygame.font.SysFont('Comic Sans MS', 30)
        if(self.color == 'white'):
            self.sprite = self.font.render(piece_type, False, WHITE_PIECE)
        else:
            self.sprite = self.font.render(piece_type, False, BLACK_PIECE)
        
    # Draws piece
    def draw(self,SCREEN,BLOCKSIZE,x,y):
        self.text_surface = self.font.render(self.piece_type, False, (0,0,0)) # color doesn't mean anything in this case
        SCREEN.blit(self.sprite, (x*BLOCKSIZE+BLOCKSIZE*.28,y*BLOCKSIZE))

    # Check loop
    def checkLoop(self,board,options,x,y,limitOperatorX,limitX,limitOperatorY,limitY,incrementX,incrementY):
        if(limitX > 7 or limitX < 0 or limitY > 7 or limitY < 0):
            return

        def get_truth(inp, relate, cut):
                ops = {'>': operator.gt,
                    '<': operator.lt,
                    '>=': operator.ge,
                    '<=': operator.le,
                    '==': operator.eq}       
                return ops[relate](inp, cut)
        
        checkX = x
        checkY = y
        checkX = checkX + incrementX
        checkY = checkY + incrementY

        while(get_truth(checkX,limitOperatorX,limitX) and get_truth(checkY,limitOperatorY,limitY)):
            if(board.checkSpace(x,y) != board.checkSpace(checkX,checkY)):
                options.append([checkX,checkY])
                if(board.checkSpace(checkX,checkY) != 'none'):
                    break
            else:
                break
            checkX = checkX + incrementX
            checkY = checkY + incrementY

    # Returns move options
    def move_options(self,board,x,y):
        options = [[x,y]]

        return options
 
# Pawn class
class Pawn(Piece):
    # Initialize
    def __init__(self, color, piece_type='P'):
        super(Pawn, self).__init__(color, piece_type)

    # Pawn move options
    def move_options(self,board,x,y):
        options = []

        offset = 1
        if(self.color == 'white'):
            offset = -1

        boundsCheck1 = (y+offset <= 7 and y+offset >= 0)
        checkEmpty1 = (boundsCheck1 and board.checkSpace(x,y+offset) == 'none')
        checkDiff1_1 = (boundsCheck1 and x-offset >= 0 and x-offset <= 7)
        checkDiff2_1 = (boundsCheck1 and x+offset >= 0 and x+offset <= 7)
        
        if(checkEmpty1): # Checks if piece can move forward 1
            options.append([x,y+offset])
            if((y == 1 and board.checkSpace(x,y) == 'black') or (y == 6 and board.checkSpace(x,y) == 'white') ): # Checks if piece can move forward 2
                checkEmpty2 = (board.checkSpace(x,y+offset*2) == 'none' and boundsCheck1)
                if(checkEmpty2):
                    options.append([x,y+offset*2])

        if(checkDiff1_1): # Checks if can capture corner to corner (switches corner based on side)
            checkDiff1_2 = (board.checkSpace(x-offset,y+offset) != board.checkSpace(x,y) and board.checkSpace(x-offset,y+offset) != 'none')
            if(checkDiff1_2): # corner capture
                options.append([x-offset,y+offset]) 
            if(board.check_last_move(x,y,"P",-offset)): # En passant
                options.append([x-offset,y+offset]) 

        if(checkDiff2_1): # Checks if can capture corner to corner (switches corner based on side)
            checkDiff2_2 = (board.checkSpace(x+offset,y+offset) != board.checkSpace(x,y) and board.checkSpace(x+offset,y+offset) != 'none')
            if(checkDiff2_2):
                options.append([x+offset,y+offset])
            if(board.check_last_move(x,y,"P",offset)): # En passant
                options.append([x+offset,y+offset])

        
          
        return options

# Bishop class
class Bishop(Piece):
    # Initialize
    def __init__(self, color, piece_type='B'):
        super(Bishop, self).__init__(color, piece_type)
    
    # Bishop options
    def move_options(self,board,x,y):
        options = []
        
        self.checkLoop(board,options,x,y,'>=',0,'>=',0,-1,-1)
        self.checkLoop(board,options,x,y,'<=',7,'>=',0,1,-1)
        self.checkLoop(board,options,x,y,'>=',0,'<=',7,-1,1)
        self.checkLoop(board,options,x,y,'<=',7,'<=',7,1,1)

        return options

# Knight class
class Knight(Piece):
    # Initialize
    def __init__(self, color, piece_type='N'):
        super(Knight, self).__init__(color, piece_type)
    
    # Knight Move options
    def move_options(self,board,x,y):
        options = []
        
        self.checkLoop(board,options,x,y,'==',x-2,'==',y-1,-2,-1)
        self.checkLoop(board,options,x,y,'==',x-2,'==',y+1,-2,+1)
        self.checkLoop(board,options,x,y,'==',x-1,'==',y-2,-1,-2)
        self.checkLoop(board,options,x,y,'==',x+1,'==',y-2,1,-2)
        self.checkLoop(board,options,x,y,'==',x+2,'==',y-1,2,-1)
        self.checkLoop(board,options,x,y,'==',x+2,'==',y+1,2,1)
        self.checkLoop(board,options,x,y,'==',x-1,'==',y+2,-1,2)
        self.checkLoop(board,options,x,y,'==',x+1,'==',y+2,1,2)

        return options
    
# Rook class
class Rook(Piece):
    # Initialize
    def __init__(self, color, piece_type='R'):
        super(Rook, self).__init__(color, piece_type)
        self.castle = True
    
    # Rook move options
    def move_options(self,board,x,y):
        options = []
        
        self.checkLoop(board,options,x,y,'>=',0,'>=',0,-1,0)
        self.checkLoop(board,options,x,y,'<=',7,'>=',0,1,0)
        self.checkLoop(board,options,x,y,'>=',0,'>=',0,0,-1)
        self.checkLoop(board,options,x,y,'>=',0,'<=',7,0,1)

        return options

# King move option 
class King(Piece):
    # Initialize
    def __init__(self, color, piece_type='K'):
        super(King, self).__init__(color, piece_type)
        self.castle = True
    
    # King move options
    def move_options(self,board,x,y):

        # Checks piece
        def checkPiece(board,x,y,type):
           return board.board[x][y].piece.piece_type == type
        
        # Checks castle
        def checkCastle(board,x,y):
           return board.board[x][y].piece.castle
        
        options = []
        
        self.checkLoop(board,options,x,y,'==',x-1,'==',y-1,-1,-1)
        self.checkLoop(board,options,x,y,'==',x,'==',y-1,0,-1)
        self.checkLoop(board,options,x,y,'==',x+1,'==',y-1,1,-1)
        self.checkLoop(board,options,x,y,'==',x+1,'==',y,1,0)
        self.checkLoop(board,options,x,y,'==',x+1,'==',y+1,1,1)
        self.checkLoop(board,options,x,y,'==',x,'==',y+1,0,1)
        self.checkLoop(board,options,x,y,'==',x-1,'==',y+1,-1,1)
        self.checkLoop(board,options,x,y,'==',x-1,'==',y,-1,0)

        if(self.castle == True):
            checkKingSide = checkPiece(board,x+1,y,'') and checkPiece(board,x+2,y,'') and checkCastle(board,x+3,y)
            checkQueenSide = checkPiece(board,x-1,y,'') and checkPiece(board,x-2,y,'') and checkPiece(board,x-3,y,'') and checkCastle(board,x-4,y)

            if(checkKingSide):
                options.append([x+2,y])
            
            if(checkQueenSide):
                options.append([x-2,y])

        return options

# Queen class   
class Queen(Piece):
    # Initialize
    def __init__(self, color, piece_type='Q'):
        super(Queen, self).__init__(color, piece_type)
    
    # Queen move options
    def move_options(self,board,x,y):
        options = []
        
        self.checkLoop(board,options,x,y,'>=',0,'>=',0,-1,0)
        self.checkLoop(board,options,x,y,'<=',7,'>=',0,1,0)
        self.checkLoop(board,options,x,y,'>=',0,'>=',0,0,-1)
        self.checkLoop(board,options,x,y,'>=',0,'<=',7,0,1)

        self.checkLoop(board,options,x,y,'>=',0,'>=',0,-1,-1)
        self.checkLoop(board,options,x,y,'<=',7,'>=',0,1,-1)
        self.checkLoop(board,options,x,y,'>=',0,'<=',7,-1,1)
        self.checkLoop(board,options,x,y,'<=',7,'<=',7,1,1)

        return options