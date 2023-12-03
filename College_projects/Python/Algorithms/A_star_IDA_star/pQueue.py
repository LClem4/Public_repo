# pQueue class
class pQueue(object):
    def __init__(self):
        self.queue = []

    # Print Queue
    def print(self,spaced=True,nodeNames=True):
        if spaced:
            if nodeNames:
                for i in self.queue:
                    print(i[0] + " ",end="")
                print()
        else:
            print(self.queue)

    # for checking if the queue is empty
    def isEmpty(self):
        return len(self.queue) == 0
    
    # Check values in list
    def checkIfInList(self,node):
        for queueNode in self.queue:
            if node == queueNode[0]:
                return True
        return False
            
    # for removing a value from list
    def remove(self,node):
        for queueNode in self.queue:
            if node == queueNode[0]:
                self.queue.remove(queueNode)
 
    # for inserting an element in the queue
    def insert(self,node,value):
        i = 0
        if self.isEmpty():
            self.queue.insert(0,[node,value])
            return

        while i < len(self.queue):
            if value < self.queue[i][1]:
                self.queue.insert(i,[node,value])
                #print(node)
                return
            i = i + 1
        self.queue.insert(i,[node,value])
      
    # for popping an element based on Priority
    def dequeue(self):
        return self.queue.pop(0)
    
def test():
    q = pQueue()
    q.insert("Bob",3)
    q.insert("Jack",5)
    q.insert("Tom",7)
    q.insert("Steve",5)
    q.remove("Jack")
    q.print()

