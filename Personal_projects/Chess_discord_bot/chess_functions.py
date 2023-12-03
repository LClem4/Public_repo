import requests
import time
import random
import pandas as pd
import re
import warnings
import os

warnings.filterwarnings("ignore")

# Prints table in full
def printRatingTableFull():
    player_data = playerData()
    message = ""
    for i in player_data.index:
        message = message + player_data['Name'][i] + "  " + str(player_data['USCF_ID'][i]) + "  " + str(player_data['Rating'][i]) + "  " + player_data['Date'][i]
        if i < len(player_data.index)-1:
            message = message + "\n"
    print(message)

# Prints data table in part
def printRatingTablePart():
    player_data = playerData()
    message = ""
    for i in player_data.index:
        message = message + player_data['Name'][i] + "  " + str(player_data['Rating'][i])
        if i < len(player_data.index)-1:
            message = message + "\n"

    return message
 
# Prints a particular player rating
def printPlayerRating(Name):
    playerInfo = getPlayerInfo(Name)
    if playerInfo == "Could not find Name":
        return playerInfo
    else:
        message = str(playerInfo[0]) + "  " + str(playerInfo[1]) + "  " + str(playerInfo[2]) + "  " + str(playerInfo[3])

    return message

# Gets player info
def getPlayerInfo(Name):
    player_data = playerData(True)
    for i, val in enumerate(player_data["Name"]):
        checkName = Name.lower()
        checkVal = val.lower()
        if checkVal == checkName:
            return player_data["Name"][i], player_data["USCF_ID"][i], player_data["Rating"][i], player_data["Date"][i]

    return "Could not find Name"

# Gets new rating data from website
def getNewRatings():
    player_data = playerData()
    for i in player_data.index:
        print(player_data['Name'][i])
        USCF_ID = str(player_data['USCF_ID'][i])
        new_rating, new_date = getNewPlayerRating(USCF_ID)
        player_data['Rating'][i] = new_rating
        player_data['Date'][i] = new_date
        time.sleep(random.randint(3, 9))

    player_data = sortRatings(player_data)
    player_data.to_csv("ratings.csv",index=False)
    print("Updated List!")

# Updates a single player rating
def getNewPlayerRating(USCF_ID):
    data_table = getDataTable(USCF_ID)
    x = 0
    while re.search(r"=> \d{3,4}",str(data_table[2][x])) == None:
         x = x + 1
    new_rating = re.search(r"=> \d{3,4}",data_table[2][x])[0].split(' ')[1]
    new_date = data_table[0][x].split(' ')[0]

    return int(new_rating), new_date

# Gets data table from ratings csv
def getDataTable(USCF_ID):
    print('start')
    websearch  = 'https://www.uschess.org/msa/MbrDtlTnmtHst.php?'+USCF_ID
    response = requests.get(websearch)
    print(response)
    df_list = pd.read_html(response.text) # this parses all the tables in webpages to a list
    data_table = df_list[3]

    return data_table

# THIS FUNCTION IS UNTESTED. DO NOT USE. IT PEEKS AT CHESS RATINGS TO CHECK FOR UPDATES
def updatePeek(maxTime=480,checkFrequency=30): # Max time in minutes, how often checked
    startTime = time.time()
    lastTimeCheck = time.time()
    lastTournamentDate = getPlayerInfo("NATHAN")[3]
    while True:
        currentTimeCheck = time.time()
        totalMinutesPassed = (currentTimeCheck - startTime)/60
        minutesPassed = (currentTimeCheck - lastTimeCheck)/60
        if minutesPassed > checkFrequency:
            new_rating, new_date = getNewPlayerRating("17328765")
            if new_date != lastTournamentDate:
                getNewRatings()
                print("Ratings Updated!")
                return
            LastTimeCheck = time.time()

        if totalMinutesPassed > maxTime:
            print("Did not Update!")
            return

# Sorts the ratings.csv           
def sortRatings(player_data):
    player_data['Rating'] = player_data['Rating'].astype(int)
    player_data = player_data.sort_values(by=['Rating'],ascending=False)
    player_data.reset_index(drop=True, inplace=True)

    return player_data

# Gathers player data
def playerData(isSimpleName=False):
    player_data = pd.DataFrame(pd.read_csv('ratings.csv'))

    if(isSimpleName):
        for i, val in enumerate(player_data["Name"]):
            player_data["Name"][i] = player_data["Name"][i].split(" ")[0]

    return player_data

getNewRatings()