# Project Information 
    Author: Luke Clement
    Last update: 12/3/23

# Description
    This program takes a year as an input on the command line. It then retreives the D1 college football schedule for that year and assigns every
    team a starting rating. It then processes the entire schedule updating each teams elo rating after they play a game. The elo rating is used to
    predict the winner of games and the percentage correctly guessed is displayed in the console. Example outputs are provided. Note that authorization 
    is required from where cfbd gets the data. This key can be provided on line 13 and the key prefix on line 14.

# Run
    Example: python3 ELO_College_Football.py 'year'
             python3 ELO_College_Football.py 2023
             python3 ELO_College_Football.py 1985