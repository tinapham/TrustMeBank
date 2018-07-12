// This data will be inserted into database given by 'MONGO_INITDB_DATABASE' environment variable.
// If the environment variable is not set then it will be inserted into database name 'test'

db.news.insert({"id" : 1, "title" : "first news", "body": "foo bar", "date":  "2018-07-12T00:00:01"});