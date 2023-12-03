import pygame
import board
import settings

BLACK = (0,0,0)

WINDOW_HEIGHT = 400
WINDOW_WIDTH = 400
BLOCKSIZE = WINDOW_WIDTH/8
SCREEN = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
SCREEN.fill(BLACK)

CLOCK = pygame.time.Clock()

# Main function
def main(): 
    settings.init()

    running = True
    BOARD = board.Board()
    
    while running:
        CLOCK.tick(60)
        settings.MOUSE.update(BLOCKSIZE)
        BOARD.update(SCREEN,BLOCKSIZE)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
        pygame.display.update()

main()