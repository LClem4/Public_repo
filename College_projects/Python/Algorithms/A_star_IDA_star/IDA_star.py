import pandas as pd
import csv
import pQueue
import math

# Author: Luke Clement
# This program takes in input from the user and applies the IDA* algorithm

# Global variables for making the graph
graph_matrix = {}
visited = []

# Class for A_star algorithm for IDA*
class IDA_star:
    # Initialization
    def __init__(self, EdgeWeights_fileName, hCost_filename):
        self.load_EdgeWeights_from_csv(EdgeWeights_fileName)
        self.load_hCost_from_csv(hCost_filename) 

    # Adds edge
    def add_edge(self, nodeName, connection, weight):
        if nodeName not in graph_matrix:
            graph_matrix[str(nodeName)] = Node()
        graph_matrix[str(nodeName)].addConnection(connection, weight)

    # Loads Edge weights from csv
    def load_EdgeWeights_from_csv(self, csv_filename):
        with open(csv_filename, mode='r') as file:
            reader = csv.reader(file)
            next(reader)
            for row in reader:
                if len(row) == 3:
                    nodeName, connection, weight = row[0], row[1], row[2]
                    self.add_edge(nodeName, connection, weight)

    # loads hCosts from csv 
    def load_hCost_from_csv(self,csv):
        global distance_matrix
        distance_matrix = pd.read_csv(csv, skiprows=6, index_col=0, dtype={'FROM': str})
        distance_matrix.dropna(axis = 0, how = 'all', inplace = True)
    
    # main
    def main(self,x,y):
        if x == y:
            print("IDA* minimum cost path")
            print("[0.0] " + str(x))
        else:
            self.IDA_star(x,y)

    # Actually implements IDA* algorithm
    def IDA_star(self,startNode,endNode):
        stack = []
        stack.append(startNode)
        bound = self.heuristic(startNode,endNode)

        while True:
            result, new_bound = self.DFS(startNode, endNode, 0, bound, stack)
            if result == "FOUND":
                self.printPath2(stack, bound)
                return
            if new_bound == float('inf'):
                return None
            print("new bound: " + str(new_bound))
            bound = new_bound
        
    # DFS used with IDA*
    def DFS(self, node, endNode, g, bound, stack):
        f = g + self.heuristic(node, endNode)
        if f > bound:
            return "NOT_FOUND", f
        if node == endNode:
            return "FOUND", f

        min_val = float('inf')
        for connection in graph_matrix[node].connections:
            if connection not in stack:
                stack.append(connection)
                result, new_bound = self.DFS(connection, endNode, g + graph_matrix[node].connections[connection], bound, stack)
                if result == "FOUND":
                    return "FOUND", f
                if new_bound < min_val:
                    min_val = new_bound
                stack.pop()
        return "NOT_FOUND", min_val
       
    # Grabs heuristic
    def heuristic(self,currentNode,endNode):
        if math.isnan(distance_matrix[currentNode][endNode]):
             return distance_matrix[endNode][currentNode]
        else:
             return distance_matrix[currentNode][endNode]
        
    # Prints path of IDA*
    def printPath(self,stack):
        pathCost = self.traceDistance(stack,0)
        print("IDA* minimum cost path")
        print("[" + str(pathCost) + "] ",end="")
        print(str(stack[0]),end="")
        for i in stack[1:]:
            print(" - " + str(i),end="")
        print()

     # Prints path of IDA*
    def printPath2(self,stack,pathCost):
        print("IDA* minimum cost path")
        print("[" + str(pathCost) + "] ",end="")
        print(str(stack[0]),end="")
        for i in stack[1:]:
            print(" - " + str(i),end="")
        print()
        

    # Prints distance of IDA*
    def traceDistance(self,stack,pathCost):
        if len(stack) > 1:
            node = stack[0]
            nextNode = stack[1]
            pathCost = pathCost + graph_matrix[node].connections[nextNode]
            return self.traceDistance(stack[1:],pathCost)
        return pathCost
    
    # Checks if a node is invalid
    def checkValidNodes(self,node):
        if not node in graph_matrix:
            raise Exception("Invalid node")

# Node class for storing node data       
class Node:
    # Initialization
    def __init__(self):
        self.connections = {}

    # AddConnection
    def addConnection(self,connection,weight):
        self.connections[connection] = float(weight)