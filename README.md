# secondhandmarketBackend

## to use database, please do the following: 
1. run onlineorder database in docker
2. run our project in intellij
3. connect database in intellij
4. run database-init.sql
   
## to implement Redis, please follow the following steps:
1. install Redis in Docker:
   打开cmd到project所在目录
  - docker pull redis
  - docker run --name secondhand -p 6379:6379 -d redis
2. 运行docker上的secondhand redis和onlineorder
3. 运行项目
  
