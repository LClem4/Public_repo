import pygame
import mouse

def init():
    pygame.init()
    pygame.font.init()
    global MOUSE, TURN
    MOUSE = mouse.Mouse(pygame.mouse)
    TURN = 'white'
