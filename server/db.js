const mongoose   = require('mongoose');

const Sequelize = require('sequelize');



// BASE SETUP
// =============================================================================
mongoose.connect(`mongodb://${process.env.MONGODB_USER}:${process.env.MONGODB_PASSWORD}@${process.env.MONGODB_HOST}/${process.env.MONGODB_DB}`, { useNewUrlParser: true }); 

const sequelize = new Sequelize(`postgres://${process.env.POSTGRES_USER}:${process.env.POSTGRES_PASSWORD}@${process.env.POSTGRES_HOST}/${process.env.POSTGRES_DB}`);

// =============================================================================

const db = {};
db.sql = {};
db.sql.sequelize = sequelize;
db.sql.Sequelize = Sequelize;

const News = require('./models/news.model');
db.nosql = {};
db.nosql.News = News;


const User = require('./models/user.model')(sequelize, Sequelize);
db.sql = {};
db.sql.User = User;    

module.exports = db;