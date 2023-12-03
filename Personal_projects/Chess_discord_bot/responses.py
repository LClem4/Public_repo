import requests
import chess_functions
import re
import asyncio
import time
from multiprocessing import Process

# Handles input for messages
def handle_response(message) -> str:
    p_message = message.lower()
    message_word = p_message.split(" ")

    player_data = chess_functions.playerData(True)
    print(p_message)

    if message_word[0] == "testing":
        print("1")
        return "Ignore this message. This is just to test if the bot is working."
    
    if message_word[0] == "help":
        return "update [name], update all\nrating [First Name], rating all"
    
    if message_word[0] == "update":
        output_message = ""
        if len(message_word) > 1:
            if message_word[1] == "all":
                Process(target=chess_functions.getNewRatings).start()
                output_message = "Updating. Current Ratings:\n"
                output_message = output_message + chess_functions.printRatingTablePart()
            else:
                output_message = chess_functions.printPlayerRating(message_word[1]) # Only prints it not update for now
        else:
            output_message = "usage: 'update all or [name]"
         
        return output_message

    if message_word[0] == "rating":
        output_message = ""
        if message_word[1] == "all":
            output_message = chess_functions.printRatingTablePart()
        else:
            output_message = chess_functions.printPlayerRating(message_word[1])

        return output_message

