import pandas as pd
path_credits = "/Users/jackwu/Desktop/HKU/Project/credits.csv"
path_metadata = "/Users/jackwu/Desktop/HKU/Project/movies_metadata.csv"

data = pd.read_csv(path_credits, encoding="latin1")
data.drop_duplicates(subset=['id'], keep='first', inplace=True)
data.to_csv(path_credits, encoding="latin1")