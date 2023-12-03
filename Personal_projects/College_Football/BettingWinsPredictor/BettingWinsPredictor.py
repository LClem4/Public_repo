from __future__ import print_function
import time
import cfbd
from cfbd.rest import ApiException
from pprint import pprint
import pandas as pd # dataframe
import re
import warnings

warnings.simplefilter("ignore")

# Configure API key authorization: ApiKeyAuth
configuration = cfbd.Configuration()
configuration.api_key['Authorization'] = 'YOUR KEY HERE'
configuration.api_key_prefix['Authorization'] = 'KEY PREFIX'

# create an instance of the API class
api_instance = cfbd.BettingApi(cfbd.ApiClient(configuration))
game_id = 56 # int | Game id filter (optional)
year = 56 # int | Year/season filter for games (optional)
week = 56 # int | Week filter (optional)
season_type = 'regular' # str | Season type filter (regular or postseason) (optional) (default to regular)
team = 'team_example' # str | Team (optional)
home = 'home_example' # str | Home team filter (optional)
away = 'away_example' # str | Away team filter (optional)
conference = 'conference_example' # str | Conference abbreviation filter (optional)

def SortDataBetting(df, rows, cols):
   
    listCols= ["" for x in range(cols)] # Column names list
    newList = [["" for x in range(cols)] for y in range(rows)] # Holds the 2D array of data
    pattern = r'(\{|)(?:\')([^\']*)(?:\'\:) ((?:\')([^\']*)(?:\')|(\-|)\d*\.\d*|(\-|)\d*)(\}|)' #Formats stats
    
    # Finds the names of the columns by going through the first data entry
    match2 = re.findall(pattern, df['lines'][20])
    for x in range(len(match2)): #
        listCols[x] = match2[x][1]

    # Finds the data points for each column. Can be name or data point
    for i in range(len(df['lines'])):
        match = re.findall(pattern, df['lines'][i])
        for j in range(len(match)):
            newList[i][j] = match[j][2]

    # refines names becaue of how regex submatches work. Reason: ' ' are not removed like they are supposed to
    pattern2 = r'[^\'\-\d)]*'
    for i in range(len(newList)):
        match3 = re.findall(pattern2, newList[i][2])
        if(len(match3) > 1):
            newList[i][2] = match3[1][:-1] # Also removes last character which is a space

    # Creates dataframe and merges with main one after removing old column
    temp_College_df = pd.DataFrame(newList, columns = listCols) # Creates new temp dataframe from previous code
    temp_College_df = temp_College_df.loc[:, ~temp_College_df.columns.duplicated()] # Drops duplicate names (Takes only first betting site sideeffect)
    result = pd.concat([df, temp_College_df], axis=1) # combines dataframes and line below drops useless columns
    result = result.drop(columns=['id','week','season_type','start_date','provider','over_under_open','home_moneyline','away_moneyline'])
    result.drop(result[result['spread'] == ''].index,inplace=True) # Drops unwanted rows
    result.reset_index(drop=True,inplace=True) # Resets index due to row drops

    for i in range(len(result['home_team'])):
        # Sorts data so winning team is in left column
        if(result['home_score'][i] < result['away_score'][i]):
            tempScore = result['home_score'][i]
            result['home_score'][i] = result['away_score'][i]
            result['away_score'][i] = tempScore

            tempTeam = result['home_team'][i]
            result['home_team'][i] = result['away_team'][i]
            result['away_team'][i] = tempTeam
        # Puts a 1 in 'lines' if team was correctly predicted otherwise 0
        if(result['home_team'][i] == result['formatted_spread'][i]):
            result['lines'][i] = 1
        else:
            result['lines'][i] = 0

    result = result.append({'lines': result['lines'].mean()}, ignore_index = True) # Finds the accuracy of bets and puts it at bottom of lines

    return result
   
year1 = 2013
year2 = 2023
try:
    # Betting lines
    mainDf = [] 
    mainDf = pd.DataFrame(mainDf)
    for i in range(year1,year2):
        api_response = api_instance.get_lines(year = i, season_type = "postseason") # retrieves data for a year
        College_df = pd.DataFrame.from_records([p.to_dict() for p in api_response]) # puts data into a specific format
        # College_df.rename(columns={'season': 'year'},inplace=True)
        College_df.to_csv('tempBettingCSV.csv',index=False) # Puts it into a temp csv (This part could be eliminated depending on format)
        College_df = pd.read_csv('tempBettingCSV.csv') # Read back tempCSV to put it into the format I have it in
        College_df = SortDataBetting(College_df,len(College_df['home_team'])+1,100) # Run the sortDataBetting function
        mainDf = pd.concat([mainDf, College_df]) # Add that to dataframe
    mainDf.to_csv('CollegeFootball_bettinglines2022.csv',index=False) # Convert dataframe to CSV
 
except ApiException as e:
    print("Exception when calling BettingApi->get_lines: %s\n" % e)
