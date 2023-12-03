import pandas as pd
import requests
import openpyxl
from os.path import exists
   
# Finds the fantasy points for all players of a position
def getPlayerData(year,position): 
    websearch  = 'https://www.fantasypros.com/nfl/reports/leaders/'+str(position)+'.php?year='+str(year) # websearch string
    response = requests.get(websearch) # html request
    player_data = pd.read_html(response.text) # finds tables
    player_data = pd.DataFrame(player_data[0]) # Gets the table needed
    player_data = player_data[['Player','Pos','Team','AVG','TTL']] # positions to be webscraped
    player_data.rename(columns={'TTL': 'TTL_'+str(year), 'AVG': 'AVG_'+str(year)}, inplace=True) # add year to column name
    
    return player_data

# Gets a range of years
def getMultiYear(start_year,end_year,position):

    player_table = getPlayerData(start_year,position) # First year
    for year in range(start_year+1,end_year+1): # combine other years
        next_table = getPlayerData(year,position) # get a year
        next_table.drop(columns=['Pos','Team'],inplace=True) # get rid of unneeded columns
        player_table = pd.merge(player_table, next_table, on=['Player'],  how ='outer') # merge years
    
    return player_table

# Write to excel file
def writeToExcel(player_table,start_year,end_year,position):
    fileName = 'NFL_FF_data_'+str(start_year)+'_'+str(end_year)+'.xlsx' # file name
    sheetName = str(position)+"_data" # sheet name
    if not exists(fileName): # makes file if does not exist
        player_table.to_excel(fileName)
    with pd.ExcelWriter(fileName, engine="openpyxl",mode="a", if_sheet_exists="replace") as writer: # write to individual sheet
        player_table.to_excel(writer, sheet_name=sheetName)

# Imports all positions
def ImportPositionData(start_year,end_year,positions):
    for position in positions: 
        print("importing "+position)
        player_table = getMultiYear(start_year,end_year,position) # Gets all data for a positon
        player_table['AVG'] = player_table[['AVG_2020', 'AVG_2021','AVG_2022']].mean(axis=1) # Gets AVG
        player_table['TTL'] = player_table[['TTL_2020', 'TTL_2021','TTL_2022']].mean(axis=1) # Gets TTL
        player_table.sort_values(by=['TTL'],inplace=True,ascending=False) # Sorts based on TTL
        player_table.reset_index(inplace=True,drop=True) # fix index
        valueDataSeries = [] # Value metric
        for i in range(len(player_table["TTL"])-1): # Gets "Value" numbers and adds them to sheet
            valueDataSeries.append(player_table["TTL"].values[i]/player_table["TTL"].values[0])
        player_table['Value'] = pd.Series(valueDataSeries) # Add to sheet
        writeToExcel(player_table,start_year,end_year,position) # Write to excel
    print("All positions imported!")

#
# THIS FUNCTION STILL NEEDS TO BE FINISHED
#
def importLocalData(fileName,sheetName): # Imports local data
    player_data = pd.read_excel(fileName,sheet_name=sheetName,index_col=0)

    print(len(player_data['Player']))

    valueDataSeries = []
    for i in range(len(player_data["AVG"])-1):
       # print(str(player_data["AVG"].values[i]) + " " + str(player_data["AVG"].values[i+1]))
       valueDataSeries.append(player_data["TTL"].values[i]/player_data["TTL"].values[0])
    player_data['Value'] = pd.Series(valueDataSeries)
    print(player_data)

   # for i in range(len(player_data["Value"])):
   #     print(str(player_data["Player"][i]) + " " + str(player_data["AVG"][i]) + " " + str(player_data["Value"][i]))
   
   

# importLocalData('NFL_FF_data_2020_2022.xlsx','te_data')
# Run main function
ImportPositionData(2020,2022,('qb','rb','wr','te','k','dst'))

