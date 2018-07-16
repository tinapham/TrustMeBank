const db = require('./config/db');

const News = db.nosql.News;

const User = db.sql.User;

const sampleNews = require('./sample/news.json');
for (i in sampleNews) {
  const st = new News(sampleNews[i]);
  st.save(err => {
    if (err) return handleError(err);
    // saved!
  });
}

User.sync({force: true}).then(() => {
    const sampleUsers = require('./sample/users.json');
    for (i in sampleUsers) {
      User.create(sampleUsers[i]);
    }
    
});
