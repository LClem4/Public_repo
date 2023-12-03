import yfinance as yf # For importing stock data
import pandas as pd # dataframe
import sys

# Get specific stock price
def get_current_price(symbol):
    try:
        ticker = yf.Ticker(symbol).fast_info # Retrieves ticker info
        last_close_price = ticker['regularMarketPreviousClose'] # Finds last close price of ticker
        return last_close_price # returns ticker price
    except Exception as e:
        print("Error: Check " + str(symbol))
    return 0.0
    
# Get values of a list of sticks
def get_StockValues(tickers):
    prices = [] # Creates array of prices
    df = pd.DataFrame(tickers,columns=['Stock'])
    for i in tickers: # Goes through each ticker adding it to list
        price = (get_current_price(i)) # Gets ticker price
        price = round(price,2) # Rounds to second decimal point
        prices.append(price) # Adds price to list
    df['value'] = prices
    return df

# Main run functions
tickers = sys.argv # Gets tickers from command line
stock_prices = get_StockValues(tickers[1:]) # Gets stock tickers
print(stock_prices) # Prints stock values