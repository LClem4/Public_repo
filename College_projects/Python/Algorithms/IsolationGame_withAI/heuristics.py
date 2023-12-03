import copy
import random as r

# Global variable for accessing adjacent tiles
neighbors = [(-1,-1), (-1,0), (-1,1),
             (0,-1),           (0,1),
             (1,-1), (1,0), (1,1)]

# This class is used for implementing adversial search along with the heuristics for it
class heuristics():
    # This function implements heuristic 1
    def heuristicPrune(self,board,i,j,p,i2,j2,p2,depth,finalDepth): # board, i, j, pathweight, depth    
        if depth == finalDepth:
            return p, i, j  
        
        max, x, y, rx, ry, nextX, nextY = 0, 0, 0, 0, 0, 0, 0 
        pruneList = self.prune(board,i,j,p,3)  # pruning / heuristic calculation
        for n in pruneList: # traverse top 3
            nextBoard = copy.deepcopy(board)
            eliminateList = self.prune(board,i2,j2,p,2) # pruning
            for e in eliminateList: # traverse top 2
                self.eliminateTile(nextBoard,e[0],e[1])
                if ((e[0] == n[0] and e[1] == n[1]) or (i2 == n[0] and j2 == n[1])): # Skip when the same
                    continue
                h2, x1, y1 = self.heuristicPrune(nextBoard,i2,j2,p2,n[0],n[1],n[2]+e[2],depth+1,finalDepth) # Recursive return
                if h2 > max: # find highest heuristic
                    max, x, y = h2, x1, y1
                    nextX, nextY = n[0], n[1]
                    rx, ry = e[0], e[1]
                elif h2 > max*.95 and r.randint(0,h2) > max*.75: # Added chance
                    max, x, y = h2, x1, y1
                    nextX, nextY = n[0], n[1]
                    rx, ry = e[0], e[1]
        if depth == 0:
            if nextX == 0 and nextY == 0: # border case
                pruneList = self.prune(board,i,j,p,4)  
                rx, ry = self.removeOther(board,i,j,i2,j2) 
                for n in pruneList:
                    return n[0], n[1], rx, ry
            # print(nextX, nextY, rx, ry)
            return nextX, nextY, rx, ry
        elif len(pruneList) == 0:
            return p2, i2, j2
        else:
            return max, x, y
        
    # This function implements heuristic 2
    def heuristicPrune2nd(self,board,i,j,p,i2,j2,p2,depth,finalDepth): # board, i, j, pathweight, depth 
        if depth == finalDepth:
            return p, i, j 
        
        max, x, y, rx, ry, nextX, nextY = 0, 0, 0, 0, 0, 0, 0
        pruneList = self.prune2nd(board,i,j,p,3)  # pruning / heuristic calculation     
        for n in pruneList: # travers top 3
            nextBoard = copy.deepcopy(board)
            eliminateList = self.prune2nd(board,i2,j2,p,2) # pruning
            for e in eliminateList: # travers top 3
                self.eliminateTile(nextBoard,e[0],e[1])
                if ((e[0] == n[0] and e[1] == n[1]) or (i2 == n[0] and j2 == n[1])): # Skip when the same
                    continue
                h2, x1, y1 = self.heuristicPrune2nd(nextBoard,i2,j2,p2,n[0],n[1],n[2]+(64-e[2]),depth+1,finalDepth) # Recursive return
                if h2 > max: # find highest heuristic
                    max, x, y = h2, x1, y1
                    nextX, nextY = n[0], n[1]
                    rx, ry = e[0], e[1]
                elif h2 > max*.90 and r.randint(0,h2) > max*.75: # Added chance
                    max, x, y = h2, x1, y1
                    nextX, nextY = n[0], n[1]
                    rx, ry = e[0], e[1]
        if depth == 0:
            if nextX == 0 and nextY == 0: # border case
                pruneList = self.prune2nd(board,i,j,p,3)  
                rx, ry = self.removeOther(board,i,j,i2,j2) 
                for n in pruneList:
                    return n[0], n[1], rx, ry
            # print(nextX, nextY, rx, ry)
            return nextX, nextY, rx, ry
        if len(pruneList) == 0:
            return p2, i2, j2
        else:
            return max, x, y

    # This function is used to prune heuristic 1
    def prune(self,board,i,j,p,num):
        pruneList = []
        for n in neighbors:
            h = board[i+n[0]][j+n[1]] + p
            if h == p:
                continue
            pruneList.append([i+n[0],j+n[1],h])
        pruneList.sort(key=lambda x: x[2])
        if len(pruneList) > num:
            return pruneList[-num:]
        else:
            return pruneList
    
    # This function is used to prune heuristic 2
    def prune2nd(self,board,i,j,p,num):
        pruneList = []
        for n in neighbors:
            h = self.addAdjacent(board,i+n[0],j+n[1]) + p
            if h == p:
                continue
            pruneList.append([i+n[0],j+n[1],h])
        pruneList.sort(key=lambda x: x[2])
        if len(pruneList) > num:
            return pruneList[-num:]
        else:
            return pruneList

    # This function is used to initialize the board
    def spacesInit(self):
        for i in range(len(self.spaces)):
            self.spaces[i][0] = 0
            self.spaces[i][len(self.spaces[0])-1] = 0
        for j in range(len(self.spaces[0])):
            self.spaces[0][j] = 0
            self.spaces[len(self.spaces)-1][j] = 0

    # This is used to update a space
    def updateSpaces(self,board,fromBoard,i,j):
        for n in neighbors:
            board[i+n[0]][j+n[1]] = self.addAdjacent(fromBoard,i+n[0],j+n[1])
    
    # This is used to update all spaces
    def updateAll(self,board,fromBoard):
        for i in range(1,self.colLen):
            for j in range(1,self.rowLen):
                board[i][j] = self.addAdjacent(fromBoard,i,j)

    # This is used to add adjacent spaces
    def addAdjacent(self,fromBoard,i,j):
        if fromBoard[i][j] == 0:
            return 0
        tmp = 0
        for n in neighbors:
            tmp = tmp + fromBoard[i+n[0]][j+n[1]]
        return tmp
    
    # This eliminates a Tile
    def eliminateTile(self,board,i,j):
        board[i][j] = 0
        self.subtractAdjacent(board,i,j)
       
    # This subtracts one from adjacent tiles
    def subtractAdjacent(self,board,i,j):
        for n in neighbors:
            if not board[i+n[0]][j+n[1]] == 0:
                board[i+n[0]][j+n[1]] = board[i+n[0]][j+n[1]] -1

    # Prints board when needed
    def printBoard(self,board):
        for i in board:
            print(i)
        print("---")

    # The main function for running the heuristics
    def run(self,gameboard,turn,depth):
        board = gameboard.gameboard
        x1 = gameboard.player1.i
        y1 = gameboard.player1.j
        x2 = gameboard.player2.i
        y2 = gameboard.player2.j
        if turn == "player1":
            if gameboard.player1.heuristic == "first":
                return self.heuristicPrune(board,x1,y1,0,x2,y2,0,0,depth)
            elif gameboard.player1.heuristic == "second":
                return self.heuristicPrune2nd(board,x1,y1,0,x2,y2,0,0,depth)
        if turn == "player2":
            if gameboard.player2.heuristic == "first":
                return self.heuristicPrune(board,x2,y2,0,x1,y1,0,0,depth)
            elif gameboard.player2.heuristic == "second":
                return self.heuristicPrune2nd(board,x2,y2,0,x1,y1,0,0,depth)

    # Removes a tile when a border case arises       
    def removeOther(self,board,i2,j2,i3,j3):
        for i in range(len(board)-1):
            for j in range(len(board[0])-1):
                if board[i][j] > 0 and not(i == i2 and j == j2) and not(i == i3 and j == j3):
                    return i, j 

