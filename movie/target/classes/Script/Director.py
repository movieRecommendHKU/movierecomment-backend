import pymysql
import pandas as pd
from pymysql import Error


class Director:
    def __init__(self, path):
        self.path = path
    
    def insert_to_movie(self):
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
        
        cnt = 0
        for row in data.itertuples():
            str_director = self.get_director(row.crew)
            query = "UPDATE Movie SET `director` = %s WHERE `movieId` = %s"

            cursor.execute(query, (str_director, row.id))
            cnt += 1
            # print(cnt)
            if cnt % 1000 == 0:
                connection.commit()
                print("commit " + str(cnt) + " records")
        

        # connection.commit()
        print("commit" + str(cnt) +  "lines")    
        cursor.close()
        connection.close()
    
    def get_director(self, crew):
        if "'job': 'Director'" in crew:
            d_idx = crew.index('Director')
            start = 0
            for i in range(len(crew)):
                if i == d_idx:
                    break
                if crew[i] == '{':
                    start = i
            end = crew.index('}', start)
            return crew[start: end+1]
'''
[
{'credit_id': '52fe466a9251416c75077a89', 'department': 'Directing', 'gender': 2, 'id': 26502, 'job': 'Director', 'name': 'Howard Deutch', 'profile_path': '/68Vae1HkU1NxQZ6KEmuxIpno7c9.jpg'}, 
{'credit_id': '52fe466b9251416c75077aa3', 'department': 'Writing', 'gender': 2, 'id': 16837, 'job': 'Characters', 'name': 'Mark Steven Johnson', 'profile_path': '/6trChNn3o2bi4i2ipgMEAytwmZp.jpg'}, 
{'credit_id': '52fe466b9251416c75077aa9', 'department': 'Writing', 'gender': 2, 'id': 16837, 'job': 'Writer', 'name': 'Mark Steven Johnson', 'profile_path': '/6trChNn3o2bi4i2ipgMEAytwmZp.jpg'}, 
{'credit_id': '5675eb4b92514179dd003933', 'department': 'Crew', 'gender': 2, 'id': 1551320, 'job': 'Sound Recordist', 'name': 'Jack Keller', 'profile_path': None}
]
'''

if __name__ == '__main__':
    director = Director("/Users/jackwu/Desktop/HKU/Project/credits.csv")
    # s = "[{'credit_id': '52fe466a9251416c75077a89', 'department': 'Directing', 'gender': 2, 'id': 26502, 'job': 'Director', 'name': 'Howard Deutch', 'profile_path': '/68Vae1HkU1NxQZ6KEmuxIpno7c9.jpg'}, {'credit_id': '52fe466b9251416c75077aa3', 'department': 'Writing', 'gender': 2, 'id': 16837, 'job': 'Characters', 'name': 'Mark Steven Johnson', 'profile_path': '/6trChNn3o2bi4i2ipgMEAytwmZp.jpg'}, {'credit_id': '52fe466b9251416c75077aa9', 'department': 'Writing', 'gender': 2, 'id': 16837, 'job': 'Writer', 'name': 'Mark Steven Johnson', 'profile_path': '/6trChNn3o2bi4i2ipgMEAytwmZp.jpg'}, {'credit_id': '5675eb4b92514179dd003933', 'department': 'Crew', 'gender': 2, 'id': 1551320, 'job': 'Sound Recordist', 'name': 'Jack Keller', 'profile_path': None}]"
    # print(len(director.get_director(s)))
    director.insert_to_movie()