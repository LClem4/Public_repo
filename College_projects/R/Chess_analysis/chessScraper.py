from chessdotcom import get_player_profile, Client
import chessdotcom
import json
import pandas as pd
import time
import random
import requests
import pprint

# Sets the rate for webscraping
Client.rate_limit_handler.tries = 1
Client.rate_limit_handler.tts = 3

# This is the header needed for webscraping
Client.request_config["headers"]["User-Agent"] = (
    "Data Project"
    "Contact me at user@email.com"
)

# A set of lists that will contain all the columns of data
class player_lists:
    def __init__(self):
        self.name = [] # Name of player
        self.title = [] # Chess title ie. GM,FM,etc
        self.country = [] # Country abbreviation
        self.joined = [] # When joined
        self.last_online = [] # last online
        self.followers = [] # followers
        self.league = [] # league
        self.bullet_last = [] # bullet rating
        self.bullet_best = [] # highest rating
        self.bullet_win = [] # wins
        self.bullet_loss = [] # losses
        self.bullet_draw = [] # draws
        self.blitz_last = [] # blitz rating
        self.blitz_best = [] # highest rating
        self.blitz_win = [] # wins
        self.blitz_loss = [] # losses
        self.blitz_draw = [] # draws
        self.rapid_last = [] # rapid rating
        self.rapid_best =[] # highest rating
        self.rapid_win = [] # wins
        self.rapid_loss = [] # losses
        self.rapid_draw = [] # draws
        self.fide = [] # fide
        self.tactics = [] # tactics
        self.puzzle_attempts = [] # puzzle attempts
        self.puzzle_best = [] # most puzzles solved in time

    # Adds player data to lists
    def append(self,name,title,country,joined,last_online,followers,league,b_last,b_best,b_win,b_loss,b_draw,bl_last,bl_best,bl_win,bl_loss,bl_draw,r_last,r_best,r_win,r_loss,r_draw,fide,tactics,p_attempts,p_best):
        self.name.append(name)
        self.title.append(title)
        self.country.append(country)
        self.joined.append(joined)
        self.last_online.append(last_online)
        self.followers.append(followers)
        self.league.append(league)
        self.bullet_last.append(b_last)
        self.bullet_best.append(b_best)
        self.bullet_win.append(b_win)
        self.bullet_loss.append(b_loss)
        self.bullet_draw.append(b_draw)
        self.blitz_last.append(bl_last)
        self.blitz_best.append(bl_best)
        self.blitz_win.append(bl_win)
        self.blitz_loss.append(bl_loss)
        self.blitz_draw.append(bl_draw)
        self.rapid_last.append(r_last)
        self.rapid_best.append(r_best)
        self.rapid_win.append(r_win)
        self.rapid_loss.append(r_loss)
        self.rapid_draw .append(r_draw)
        self.fide.append(fide)
        self.tactics.append(tactics)
        self.puzzle_attempts.append(p_attempts)
        self.puzzle_best.append(p_best)

    # Creates a dictionary that will eventually be converted into a dataframe
    def dictionary(self):
        playerDictionary = {'name':self.name,'title':self.title,'country':self.country,'joined':self.joined,
                                 'last_online':self.last_online,'followers':self.followers,'league':self.league,'bullet_last':self.bullet_last,
                                 'bullet_best':self.bullet_best,'bullet_win':self.bullet_win,'bullet_loss':self.bullet_loss,'bullet_draw':self.bullet_draw,
                                 'blitz_last':self.blitz_last,'blitz_best':self.blitz_best,'blitz_win':self.blitz_win,'blitz_loss':self.blitz_loss,
                                 'blitz_draw':self.blitz_draw,'rapid_last':self.rapid_last,'rapid_best':self.rapid_best,'rapid_win':self.rapid_win,
                                 'rapid_loss':self.rapid_loss,'rapid_draw':self.rapid_draw,'fide':self.fide,'tactics_rating':self.tactics,
                                 'puzzle_attempts':self.puzzle_attempts,'puzzle_best':self.puzzle_best}
        return playerDictionary

# initialize list
players_data = player_lists()

# Chess titles (Used for finding players)
chess_titles = ['GM', 'WGM', 'IM', 'WIM', 'FM', 'WFM', 'NM', 'WNM', 'CM', 'WCM']

# Get list of players by title
def get_list_of_players(titles):
    df = []
    for title in titles:
        players = chessdotcom.get_titled_players(title)
        print(title,len(players.players))
        df = df  + players.players
        time.sleep(2)
    print(len(df))
    df = pd.DataFrame(df)
    df.to_csv("test.csv",index=False)

# Gets data from player profile
def get_profile(player):
    try:
        profile =  chessdotcom.get_player_profile(player)
        title = profile.player.title
        country = profile.player.country[-2:]
        joined = profile.player.joined
        last_online = profile.player.last_online
        followers = profile.player.followers
        league = profile.player.league
        return title, country, joined, last_online, followers, league
    except Exception as e:
        print("profile",e)

# Gets data from player stats 
def get_stats(player):
    try:
        stats =  chessdotcom.get_player_stats(player)
        bullet = stats.stats.chess_bullet
        b_last, b_best, b_win, b_loss, b_draw = bullet.last.rating,bullet.best.rating,bullet.record.win,bullet.record.loss,bullet.record.draw
        blitz = stats.stats.chess_blitz
        bl_last, bl_best, bl_win, bl_loss, bl_draw  = blitz.last.rating,blitz.best.rating,blitz.record.win,blitz.record.loss,blitz.record.draw
        rapid = stats.stats.chess_rapid
        r_last, r_best, r_win, r_loss, r_draw = rapid.last.rating,rapid.best.rating,rapid.record.win,rapid.record.loss,rapid.record.draw
        fide = stats.stats.fide
        tactics = stats.stats.tactics.highest.rating
        puzzle_rush = stats.stats.puzzle_rush

        return b_last, b_best, b_win, b_loss, b_draw,  bl_last, bl_best, bl_win, bl_loss, bl_draw, r_last, r_best, r_win, r_loss, r_draw, fide, tactics, puzzle_rush.best.total_attempts, puzzle_rush.best.score
    except Exception as e:
        print("stats",e)

# Adds player to list
def add_player(player):
  
    try:
        title,country,joined,last_online,followers,league = get_profile(player)
        time.sleep(1)
        b_last,b_best,b_win,b_loss,b_draw,bl_last,bl_best,bl_win,bl_loss,bl_draw,r_last,r_best,r_win,r_loss,r_draw,fide,tactics,p_attempts,p_best = get_stats(player)
        players_data.append(player,title,country,joined,last_online,followers,league,b_last,b_best,b_win,b_loss,b_draw,bl_last,bl_best,bl_win,bl_loss,bl_draw,r_last,r_best,r_win,r_loss,r_draw,fide,tactics,p_attempts,p_best)
        print(player + " was added!")
    except:
        print("player could not be added")

# Adds all players from list of usernames to list and then converts it to csv
def add_all_players(player_usernames):
    for name in player_usernames:
        add_player(name)
        time.sleep(1)
    player_df = pd.DataFrame(players_data.dictionary())
    player_df.to_csv("player_data.csv",index=False)

# imports list of players usernames and takes a sample of them and then runs add_all_players
def import_player_data(num_players):
    list_of_names = pd.read_csv('player_list.csv')
    list_of_names = list_of_names["0"].values.tolist()
    list_of_names =  random.sample(list_of_names, num_players)
    add_all_players(list_of_names)
    
# imports the local data we webscraped, removes duplicate users, and then changes player's username for privacy reasons (produces final refined list)
def sort_data():
    player_df = pd.read_csv('player_data.csv')
    print(player_df)
    player_df.drop_duplicates(subset=['name'],keep="last",ignore_index=True,inplace=True)
    for i in range(len(player_df["name"])):
        player_df["name"][i] = "Player " + str(i+1)
    print(player_df)
    player_df.to_csv("player_data.csv",index=False)

import_player_data(100)
sort_data()
