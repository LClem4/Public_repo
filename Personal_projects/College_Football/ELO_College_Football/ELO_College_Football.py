from __future__ import print_function
import time
import cfbd
from cfbd.rest import ApiException
from pprint import pprint
import pandas as pd # dataframe
import openpyxl
import sys
pd.options.mode.chained_assignment = None  # default='warn'

# Configure API key authorization: ApiKeyAuth
configuration = cfbd.Configuration()
configuration.api_key['Authorization'] = 'KEY'
configuration.api_key_prefix['Authorization'] = 'KEY PREFIX'

# create an instance of the API class
api_instance = cfbd.GamesApi(cfbd.ApiClient(configuration))
api_instance2 = cfbd.TeamsApi(cfbd.ApiClient(configuration))
y = 0
try:
    year = int(sys.argv[1]) # int | Year/season filter for games
    if year > 2022:
       raise Exception("Invalid year")
except:
   print("Invalid input")
   exit(-1)
   
division = 'fbs' # str | Division classification filter (fbs/fcs/ii/iii) (optional)

StartingElo = 1500
BiasFormula = 0 # Doesn't actually do anything right now

try:
    # Betting lines
    api_gamesRegular = api_instance.get_games(year = year, season_type = 'regular', division = division) # Gets regular season
    api_gamesPost = api_instance.get_games(year = year, season_type = 'postseason', division = division) # Gets post season
    api_fbsTeams = api_instance2.get_fbs_teams(year = year) # Gets teams
    games_df = pd.DataFrame.from_records([p.to_dict() for p in api_gamesRegular]) # formatting
    games_df2 = pd.DataFrame.from_records([p.to_dict() for p in api_gamesPost]) # formatting
    games_df = games_df[['season','week','start_date','home_team','home_points','away_team','away_points']] # Columns
    games_df2 = games_df2[['season','week','start_date','home_team','home_points','away_team','away_points']] # Columns
    games_df2['week'] = 20 # Sets postseason game week to 20 so that it isn't confused with regular season weeks
    numPostSeasonGames = len(games_df2['week']) # Stores number of post seasons games
    games_df = pd.concat([games_df, games_df2]) # Combine regular and postseason
    games_df['Elo_Prediction'] = -1 # whether the higher elo team won. Default is -1
    games_df['Elo_Home'] = -1 # whether the higher elo team won. Default is -1
    games_df['Elo_Away'] = -1 # whether the higher elo team won. Default is -1
    teams_df = pd.DataFrame.from_records([p.to_dict() for p in api_fbsTeams]) # Formatting
    teams_df = teams_df[['school']] # Column
    teams_df = teams_df.rename(columns={'school': 'Team'}) # Rename
    teams_df['Elo'] = StartingElo # Starting Elo
    ExcelName = 'FBS_Team_Elo_'+str(year)+'.xlsx'
    games_df.to_excel(ExcelName, sheet_name='FBS_schedule',index=False)
    with pd.ExcelWriter(ExcelName, engine="openpyxl", mode="a") as writer:
        teams_df.to_excel(writer, sheet_name="FBS_teams",index=False)
except ApiException as e:
    print("Exception when calling BettingApi->get_lines: %s\n" % e)

# Expected result
def expected_result(loc,aw): # Expected Elo
  dr=loc-aw
  we=(1/(10**(-dr/400)+1))
  return [round(we,1),1-round(we,1)]

# Actual result
def actual_result(loc,aw): # Actual Elo result

  wa = 0.5
  wl = 0.5

  if loc<aw:
      wa=1
      wl=0
  elif loc>aw:
      wa=0
      wl=1
  elif loc==aw:
      wa=0.5
      wl=0.5
  return [wl,wa]

# Calculate new elo
def calculate_elo(elo_l,elo_v,local_score,away_score): # Calculate elo score
  k=50+(abs(local_score-away_score)*4)
  wl,wv=actual_result(local_score,away_score)
  wel,wev=expected_result(elo_l,elo_v)

  prediction = 0.5

  if(local_score > away_score and elo_l > elo_v):
    prediction = 1 
  elif(local_score < away_score and elo_v > elo_l):
    prediction = 1
  elif(local_score > away_score and elo_l < elo_v):
    prediction = 0
  elif(local_score < away_score and elo_l > elo_v):
    prediction = 0

  elo_ln=elo_l+k*(wl-wel)
  elo_vn=elo_v+k*(wv-wev)
  
  return elo_ln,elo_vn,prediction

games_df = pd.read_excel(ExcelName, sheet_name='FBS_schedule') # Gets schedule
games_df = games_df.fillna(0) # Fills scores that were left blank
teams_df = pd.read_excel(ExcelName, sheet_name='FBS_teams') # Gets teams

# Runs through calculator
def EloSeasonCalculator(teams,games,postSeasonCheck): # Calculates new elo for each team based on the season schedule
    sumCorrectPrediction = 0
    for i in range(len(games['home_team'])): # Goes through schedule
        team1 = games['home_team'][i] # Finds home team of match
        team2 = games['away_team'][i] # Finds away team of match
        team1_score = games['home_points'][i] # Finds home team score
        team2_score = games['away_points'][i] # Away team score
        team1_elo = teams[teams['Team']==team1].Elo # Finds elo of home team
        team2_elo = teams[teams['Team']==team2].Elo # ELo of away team
        
        if(postSeasonCheck == False and games['week'][i] == 20): # If postseason is not updated with Elo if postSeasonCheck is false
          break

        if(team1_elo.values.size > 0 and team2_elo.values.size > 0): # Checks if team was set (ie. Ignores D2 teams and D1 vs D2 games)
          newElo = calculate_elo(team1_elo.values[0],team2_elo.values[0],team1_score,team2_score) # Calculates new elo
          teams.loc[teams['Team'] == team1, ['Elo']] = newElo[0] # Sets home team elo
          teams.loc[teams['Team'] == team2, ['Elo']] = newElo[1] # Away team elo
          games['Elo_Home'][i] = newElo[0] #  Returns home teams new elo
          games['Elo_Away'][i] = newElo[1] # Returns away teams new elo
          games['Elo_Prediction'][i] = newElo[2] # Returns whether elo predicted winner 1 = yes, 0 = no
          if(games['week'][i] == 20): # Adds up how many post season games were predicted correctly
            sumCorrectPrediction += newElo[2]
        else:
          games = games.drop(i)
  
    games.reset_index(inplace=True,drop=True) # Reindex and drop
  
    avgCorrectPrediction = sumCorrectPrediction/numPostSeasonGames # Percent prediction correct base on elo
    print('Prediction Percentage:'+str(avgCorrectPrediction)) # Prints the value to be seen
  
    return teams, games # return the dataframe of teams

# Main functions to run
teams_df, games_df = EloSeasonCalculator(teams_df, games_df, True) # Actual function

teams_df['New_Elo'] = teams_df['Elo'] # Creates new column with new team
teams_df['Elo'] = StartingElo # Resets starting elo column
with pd.ExcelWriter(ExcelName, engine="openpyxl", mode="a",if_sheet_exists="overlay") as writer: # Writes to excel sheet FBS_teams
    teams_df.to_excel(writer, sheet_name="FBS_teams",index=False)
with pd.ExcelWriter(ExcelName, engine="openpyxl", mode="a",if_sheet_exists="replace") as writer: # Wrties to excel sheet FBS schedule (same excel document)
    games_df.to_excel(writer, sheet_name="FBS_schedule",index=False)

