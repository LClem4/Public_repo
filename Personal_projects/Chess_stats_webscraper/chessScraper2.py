from chessdotcom import get_player_games_by_month, Client
import pprint
import json
import re
import datetime
import pandas as pd

# Required information
Client.request_config["headers"]["User-Agent"] = (
    "Data Project"
    "Contact me at user@email.com"
)

# Dictionary for importing data
game_data = [[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]]
playerDictionary = {'white_username':game_data[0],'black_username':game_data[1], 'opening':game_data[2],
                    'score_white':game_data[3],'score_black':game_data[4],"result_white":game_data[5],
                    "result_black":game_data[6],"time_class":game_data[7],"time_control":game_data[8],
                    'rating_white':game_data[9],"rating_black":game_data[10],'accuracy_white':game_data[11],
                    'accuracy_black':game_data[12],'start_time':game_data[13],'end_time':game_data[14],
                    'game_length':game_data[15],'total_moves':game_data[16],'avg_move_time':game_data[17],
                    'UTC_date':game_data[18],"white_<1":game_data[19],"white_2":game_data[20],"white_5":game_data[21],
                    'white_15':game_data[22],'white_>15':game_data[23],"black_<1":game_data[24],"black_2":game_data[25],
                    "black_5":game_data[26],"black_15":game_data[27],"black_>15":game_data[28]}

# Gets all games for all players
def getGames(username,years,months):
    print("Getting games of " + str(username))
    for x in years:
        for y in months:
            data = get_player_games_by_month(username,year=x,month=y).json
            for i in data['games']:
                try:
                    game = i
                    getGame(game)
                except Exception as e:
                    print(e)

# Adds a single game to game_data       
def getGame(data):
    #pprint.pprint(data)
    txt = r"openings/.*\""
    opening = re.search(txt, data['pgn']) # opening
    split_point = re.search(r"\d|\.\.\d", opening[0])
    if split_point == None:
        opening = opening[0]
    else:
        opening = opening[0].split(split_point[0])[0]
    result1 = re.search(r"Result.*\"",data['pgn'])[0]
    username_white = data['white']['username']
    username_black = data['black']['username']
    resultWhite1 = result1[8] # white 1 or 0
    resultBlack1 = result1[10] # black 1 or 0
    if resultBlack1 == '2':
        resultBlack1 = 0.5
        resultWhite1 = 0.5
    resultWhite2 = data['white']['result']
    resultBlack2 = data['black']['result']
    time_class = data['time_class']
    time_control = data['time_control']
    rating_white = data['white']['rating']
    rating_black = data['black']['rating']
    accuracy_white = data['accuracies']['white']
    accuracy_black = data['accuracies']['black']
    endTime = re.search(r"EndTime.*\"",data['pgn'])[0]
    startTime = re.search(r"StartTime.*\"",data['pgn'])[0]
    endTime2 = datetime.datetime.strptime(endTime[9:17], '%H:%M:%S')
    startTime2 = datetime.datetime.strptime(startTime[11:19], '%H:%M:%S')
    total_seconds = (endTime2 - startTime2).total_seconds()
    if total_seconds < 0:
        raise Exception("Sorry, no numbers below zero")
    moves = re.findall(r"([1-9]|[1-9][0-9]|[1-9][0-9][0-9])\.\s",data['pgn'])
    num_moves = moves[-1]
    time_per_move = round(float(total_seconds) / int(num_moves),2)
    UTC_Date = re.search(r"UTCDate.*\"",data['pgn'])[0] # Date
    move_times = re.findall(r"%clk [0-9]:[0-9][0-9]:[0-9][0-9]",data['pgn'])
    increment = 0
    if '+' in data['time_control']:
        increment = int(data['time_control'].split('+')[1])
    timeFreqs = timesFrequency(move_times,increment)

    game_data[0].append(username_white)
    game_data[1].append(username_black)
    game_data[2].append(opening[9:-1]) # opening
    game_data[3].append(resultWhite1) # result binary
    game_data[4].append(resultBlack1)
    game_data[5].append(resultWhite2) # checkmate vs won on time
    game_data[6].append(resultBlack2)
    game_data[7].append(time_class) # time control/class
    game_data[8].append(time_control) # time control/class
    game_data[9].append(rating_white) # rating
    game_data[10].append(rating_black)
    game_data[11].append(accuracy_white)
    game_data[12].append(accuracy_black) # accuracy
    game_data[13].append(startTime[11:19])
    game_data[14].append(endTime[9:17]) # start and end time
    game_data[15].append(total_seconds) # total seconds
    game_data[16].append(num_moves) # number of moves
    game_data[17].append(time_per_move)
    game_data[18].append(UTC_Date[9:19])
    game_data[19].append(timeFreqs[0][0])
    game_data[20].append(timeFreqs[0][1])
    game_data[21].append(timeFreqs[0][2])
    game_data[22].append(timeFreqs[0][3])
    game_data[23].append(timeFreqs[0][4])
    game_data[24].append(timeFreqs[1][0])
    game_data[25].append(timeFreqs[1][1])
    game_data[26].append(timeFreqs[1][2])
    game_data[27].append(timeFreqs[1][3])
    game_data[28].append(timeFreqs[1][4])

    return game_data

# Gets time differences
def getPlayerDiffs(times, player):

    playerTimes = [] # seconds
    playerDiffs = [] # Diff times
    if player == 1:
        playerTimes = times[::2] # Player 1 time seconds
    elif player == 2:
        playerTimes = times[1::2] # Player 2 time seconds
  
    for i, value in enumerate(playerTimes):
        if i > 0:
            playerDiffs.append(playerTimes[i-1] - playerTimes[i])

    return playerDiffs

# Gets frequencies of move durations
def getPlayerFrequencies(timeDiffs,increment):

    freqPlayer = [0,0,0,0,0]
    for x in timeDiffs:
        i = x.total_seconds()
        if i + increment < 1:
            freqPlayer[0] = freqPlayer[0] + 1
        elif i + increment <= 2:
            freqPlayer[1] = freqPlayer[1] + 1
        elif i + increment <= 5:
            freqPlayer[2] = freqPlayer[2] + 1
        elif i + increment <= 15:
            freqPlayer[3] = freqPlayer[3] + 1
        else:
            freqPlayer[4] = freqPlayer[4] + 1

    return freqPlayer

# Gets the frequencies for both players
def timesFrequency(times,increment):
    
    for i, value in enumerate(times):
        times[i] = value[5:]
        times[i] = datetime.datetime.strptime(times[i], '%H:%M:%S')
    
    player1Diffs = getPlayerDiffs(times,1)
    player2Diffs = getPlayerDiffs(times,2)
    player1Freq = getPlayerFrequencies(player1Diffs,increment)
    player2Freq = getPlayerFrequencies(player2Diffs,increment)

    return player1Freq, player2Freq

# Gets data for all players and works as the main function
def getAllPlayers(usernames,years,months):
    for i in usernames:
        getGames(i,years,months)
    player_data = pd.DataFrame(playerDictionary)
    player_data.to_csv("player_game_data_2.csv",index=False)

getAllPlayers(["Hikaru"],[2023],[1])



