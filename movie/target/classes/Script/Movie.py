import pymysql
import pandas as pd
from pymysql import Error
class Movie:
    def __init__(self, path):
        self.path = path

    def get_first_item(self, json_str):
        if(json_str == "[]"):
            return json_str
        return json_str.replace('[','').replace(']','').split('}')[0] + '}'
        

    def insert_movie(self):

        try:
            connection = pymysql.connect(
                host='localhost',
                port=3306,
                database='PMRS',
                user='root',
                password='123456',
                # charset="utf8"
            )

            if connection:
                print("Connected to MySQL database")

        except Error as e:
            print("Error while connecting to MySQL", e)

        data = pd.read_csv(self.path, encoding="utf-8")

        cursor = connection.cursor(cursor=pymysql.cursors.DictCursor)
        
        # cursor.execute(get_sql)
        # get_df = pd.DataFrame(cursor.fetchall())  # 获取结果转为dataframe
        # print(get_df)
        cnt = 0
        

        for row in data.itertuples():
            query = "INSERT INTO Movie (movieId, movieName, overview, producer, rating, releaseDate, popularity, posterPath, voteCount) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)"
            producer = Movie.get_first_item(self, row.production_companies)
            popularity = 0
            if not ':' in row.popularity:
                popularity = row.popularity
                # print(cnt)

            values = (row.id, str(row.title), str(row.overview), str(producer), str(row.vote_average),str(row.release_date), str(popularity), str(row.poster_path), str(row.vote_count))
            cursor.execute(query, values)
            cnt += 1

            if cnt % 1000 == 0:
                connection.commit()
                print("commit " + str(cnt) + " records")

        connection.commit()
        print("commit" + str(cnt) +  "lines")    
        cursor.close()
        connection.close()
    



if __name__ == "__main__":
    movie = Movie("/Users/jackwu/Desktop/HKU/Project/movies_metadata.csv")
    movie.insert_movie()