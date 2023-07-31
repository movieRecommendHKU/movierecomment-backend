# movierecomment-backend
## Run the application
click run in MovieApplicaiton.

## Data Storage
- Including MySQL, Neo4j and ElasticSearch.
- All deployed in the remote server.
- No need to init data locally.
- Browse the Neo4j: http://121.43.150.228:7474/browser/
  - username: neo4j
  - password: 7507chyfbjhcdwtc_hku

## User Profiling:
- It is a scheduled task.
- You can trigger the task by unit test: UserPreferenceSchedulerTest
- Before user profile generating, please register a new account and load some test user behavior data.
  - register an account: AccountServiceTest.register.
  - login the account:  AccountServiceTest.login.
  - Batch generate Collect: CollectServiceTest.collect. (Use your new account)
  - You can also generate dislikes and ratings.
  - The preparation is done. Go to UserPreferenceSchedulerTest and test the profiling.
- You can see the result in the Neo4j home page.
  - Use "match p=(n:user)-[]-() where n.userId=#{yourId} return p"

## Recommend Process:
- It is a scheduled task.
- You can trigger the task by unit test: DailyRecommendSchedulerTest
- Please use the user registered in the user profiling test.

