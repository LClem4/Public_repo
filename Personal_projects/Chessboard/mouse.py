import math
import pygame

# Mouse class
class Mouse():
    # Initialize
    def __init__(self, mouse):
        self.mouse = mouse
        self.x = 0
        self.y = 0

    # Mouse pressed
    def pressed(self):
        return self.mouse.get_pressed()[0] == True
    
    # Mouse released
    def released(self):
         for event in pygame.event.get():
            if event.type == pygame.MOUSEBUTTONUP:
                return True
            else:
                return False

    # Mouse update
    def update(self,BLOCKSIZE):
        self.x = math.floor(self.mouse.get_pos()[0]/BLOCKSIZE)
        self.y = math.floor(self.mouse.get_pos()[1]/BLOCKSIZE)
