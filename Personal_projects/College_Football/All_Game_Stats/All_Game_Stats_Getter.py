from __future__ import print_function
import time
import cfbd
from cfbd.rest import ApiException
from pprint import pprint
import pandas as pd # dataframe

# Configure API key authorization: ApiKeyAuth
configuration = cfbd.Configuration()
configuration.api_key['Authorization'] = 'YOUR KEY HERE'
configuration.api_key_prefix['Authorization'] = 'KEY PREFIX'

# create an instance of the API class
api_instance = cfbd.GamesApi(cfbd.ApiClient(configuration))
year = 2022 # int | Year/season filter for games (optional)
week = 1 # int | Week filter (optional)
season_type = 'regular' # str | Season type filter (regular or postseason) (optional) (default to regular)
division = 'fbs' # str | Division classification filter (fbs/fcs/ii/iii) (optional)

try:  
    # Sorts and individual Game (Sorts 2 lines: home and away team)
    def sortGameData(games):
        newDataFrame2 = pd.DataFrame(games.get('teams')) # Sets first four columns
        newDataFrame2 = newDataFrame2.drop(["stats"], axis = 1) # Drops stat column that hasn't been sorted yet
        newDataFrame3 = pd.DataFrame() # Creates dataframe that will contain the divided up 'stat' column

        # Loops through the home and away team for stats
        for i in games.get('teams'):
            tempDataFrame = pd.DataFrame() # Temporary dataframe

            # Loops through the annoying stat column and converts the stats into individual columns
            for j in i.get('stats'): 
                tempDataFrame2 = pd.Series(j) # Temporary stat column
                tempDataFrame = pd.concat([tempDataFrame ,tempDataFrame2],axis=1) # Adds temporary stat column to build the dataframe
            tempDataFrame = pd.DataFrame(tempDataFrame.values[1:], columns = tempDataFrame.iloc[0]) # Renames the columns to the first row of the dataframe otherwise they are all called '0'
            tempDataFrame = tempDataFrame.reset_index(drop=True) # resets index (forget if this is needed or not) Might just look nicer
            newDataFrame3 = pd.concat([newDataFrame3,tempDataFrame]) # Adds the first team and then tacks on the second in the next loop to build a 2 line dataframe
              
        newDataFrame3.reset_index(inplace=True) # Resets index. This is needed for whatever reason
        newDataFrameFinal = pd.concat([newDataFrame2, newDataFrame3],axis=1) # Combines the first 2 line dataframe with the second "stat" 2 line dataframe
   
        return newDataFrameFinal # returns a 2 line dataframe. One line for the home team and one for the away team

    # Combines all the completed 2 line dataframes together
    def sortWeekOfGames(games):
        week_df = pd.DataFrame() # temporary dataframe

        # Loops through every set of games for the week
        for i in games:
            tempdf = sortGameData(i) # Runs an individual game
            week_df = pd.concat([week_df, tempdf]) # Adds it to the week dataframe

        return week_df # Returns a single week of games

    # Combines all weeks from week 1 to week x
    def combineAllWeeks(weeks):
        combinedWeeks = pd.DataFrame() # temporary dataframe

        # Loops through week 1 to week x and the adds it to a multiweek dataframe
        for i in range(1,weeks+1):
            api_response = api_instance.get_team_game_stats(year=year, week = i, season_type=season_type, classification=division) # Retrieves the API game data for a single week
            games = [p.to_dict() for p in api_response] # The first format. It's just needed. Probably could be improved
            tempWeek_df = sortWeekOfGames(games) # stores and individual week of data in temporary dataframe
            combinedWeeks = pd.concat([combinedWeeks, tempWeek_df]) # Adds it to the multiweek dataframe

        combinedWeeks = combinedWeeks.reset_index(drop=True) # Resets index. Not needed but might look better
        combinedWeeks = combinedWeeks.drop(["index"], axis = 1) # Drops and unnecessary index column. I think it is left over from 'stat' dataframe
       
        return combinedWeeks # Returns dataframe from weeks 1 to week x

    spreadSheetName = 'All_Game_Stats_'+str(year)+".xlsx" # Creates a name based on the year to store the data in
    allGames = combineAllWeeks(15) # Runs the function to find all games from weeks 1 to week x
    allGames.to_excel(spreadSheetName) # Stores the games in an excel spread sheet

except ApiException as e:
    print("Exception when calling BettingApi->get_lines: %s\n" % e)