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


    def insertMovie(self):

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

        data = pd.read_csv(self.path, encoding="latin1")

        cursor = connection.cursor(cursor=pymysql.cursors.DictCursor)

        # get_sql = "select * from Movie"  # sql语句
        # cursor.execute(get_sql)
        # get_df = pd.DataFrame(cursor.fetchall())  # 获取结果转为dataframe
        # print(get_df)
        cnt = 0

        for row in data.itertuples():
            query = "INSERT INTO Movie (movieId, movieName, overview, producer, rating, releaseDate) VALUES (%s, %s, %s, %s, %s, %s)"
            # query = "INSERT INTO Movie (movieId) VALUES (%s)"
            producer = Movie.get_first_item(self, row.production_companies)
            values = (row.id, str(row.title), str(row.overview), str(producer), str(row.vote_average),str(row.release_date))
            cursor.execute(query, values)
            cnt += 1
            # print(cnt)
            if cnt % 1000 == 0:
                connection.commit()
                print("commit " + str(cnt) + " records")


        connection.commit()
        print("commit" + str(cnt) +  "lines")
        cursor.close()
        connection.close()


if __name__ == "__main__":
    movie = Movie("/Users/jackwu/Desktop/HKU/Project/movies_metadata.csv")
    movie.insertMovie()