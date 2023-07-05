import pandas as pd
from Director import *

di = Director("/Users/jackwu/Desktop/HKU/Project/credits.csv")

credits = pd.read_csv("/Users/jackwu/Desktop/HKU/Project/credits.csv",  encoding="utf-8")
movie = pd.read_csv("/Users/jackwu/Desktop/HKU/Project/movies_metadata.csv",  encoding="utf-8")
keywords = pd.read_csv("/Users/jackwu/Desktop/HKU/Project/keywords.csv")

result = pd.merge(movie, credits, on="id")
# result.add() 
directors = []
for row in result.itertuples():
    director = di.get_director(row.crew)
    directors.append(director)

result['director'] = directors


result = pd.merge(result, keywords)

result.to_csv("/Users/jackwu/Desktop/HKU/Project/movies_es.csv", encoding="utf-8")

