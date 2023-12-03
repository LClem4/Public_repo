
# Global variable for accessing adjacent tiles
neighbors = [(-1,-1), (-1,0), (-1,1),
             (0,-1),           (0,1),
             (1,-1), (1,0), (1,1)]

# This class holds all the important information for processing the board
class Board:
        # This initializes the board
        def __init__(self,rows,cols,x1,y1,x2,y2,type1,type2):
            self.tmpboard = [[1 for i in range(cols+2)] for j in range(rows+2)]
            self.gameboard = [[0 for i in range(cols+2)] for j in range(rows+2)] 
            self.testBoard = [[0 for i in range(cols+2)] for j in range(rows+2)] 
            self.rowLen = len(self.gameboard[0])-1 # one less because loops will start at 1 and not 0
            self.colLen = len(self.gameboard)-1 # one less because loops will start at 1 and not 0
            self.tmpboardInit() 
            self.setBoard(x1,y1,x2,y2,type1,type2)
           

        # This is used to set/reset the board
        def setBoard(self,x1,y1,x2,y2,type1,type2):  
            self.updateAll(self.gameboard,self.tmpboard)
            self.player1 = Player(x1,y1,"first",type1)
            self.player2 = Player(x2,y2,"second",type2)

        # Updates all the spaces
        def updateAll(self,board,fromBoard):
            for i in range(1,self.colLen):
                for j in range(1,self.rowLen):
                    board[i][j] = self.addAdjacent(fromBoard,i,j)
        
        # Adds adjacent spaces
        def addAdjacent(self,fromBoard,i,j):
            if fromBoard[i][j] == 0:
                return 0
            tmp = 0
            for n in neighbors:
                tmp = tmp + fromBoard[i+n[0]][j+n[1]]
            return tmp
        
        # Moves the player
        def playerMove(self,turn,i,j,ri,rj):
            if turn == "player1":
                self.player1.i = i
                self.player1.j = j
            elif turn == "player2":
                self.player2.i = i
                self.player2.j = j
            self.gameboard[ri][rj] = 0 
            self.subtractAdjacent(ri,rj)
            
        # Initializes the first board
        def tmpboardInit(self):
            for i in range(len(self.tmpboard)):
                self.tmpboard[i][0] = 0
                self.tmpboard[i][len(self.tmpboard[0])-1] = 0
            for j in range(len(self.tmpboard[0])):
                self.tmpboard[0][j] = 0
                self.tmpboard[len(self.tmpboard)-1][j] = 0

        # Subtracts adjacent spaces
        def subtractAdjacent(self,i,j):
            for n in neighbors:
                if not self.gameboard[i+n[0]][j+n[1]] == 0:
                    self.gameboard[i+n[0]][j+n[1]] = self.gameboard[i+n[0]][j+n[1]] -1

        # prints out Board when needed
        def printBoard(self,board):
            for i in board:
                print(i)
            print("---")

        # Checks when isolated
        def checkIsolated(self,turn):
            status = "isolated"
            if turn == "player1":
                for n in neighbors:
                    if self.gameboard[self.player2.i+n[0]][self.player2.j+n[1]] > 0 and not (self.player1.i == self.player2.i+n[0] and self.player1.j == self.player2.j+n[1]):
                        status = "Not isolated"
            elif turn == "player2":
                for n in neighbors:
                    if self.gameboard[self.player1.i+n[0]][self.player1.j+n[1]] > 0 and not (self.player2.i == self.player1.i+n[0] and self.player2.j == self.player1.j+n[1]):
                        status = "Not isolated"
            return status
  
# This is class is used to store player information
class Player:
     # This initializes the player
     def __init__(self,i,j,heuristic,type):
          self.i = i
          self.j = j
          self.heuristic = heuristic
          self.type = type
