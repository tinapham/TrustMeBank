const mongoose   = require('mongoose');

const Sequelize = require('sequelize');



// BASE SETUP
// =============================================================================
mongoose.connect(`mongodb://${process.env.MONGODB_USER}:${process.env.MONGODB_PASSWORD}@${process.env.MONGODB_HOST}/${process.env.MONGODB_DB}`, { useNewUrlParser: true }); 

// const sequelize = new Sequelize(`postgres://${process.env.POSTGRES_USER}:${process.env.POSTGRES_PASSWORD}@${process.env.POSTGRES_HOST}/${process.env.POSTGRES_DB}`);

const sequelize = new Sequelize(process.env.POSTGRES_DB, process.env.POSTGRES_USER, process.env.POSTGRES_PASSWORD, {
    host: process.env.POSTGRES_HOST,
    dialect: 'postgres',
    operatorsAliases: false,
  
    pool: {
      max: 15,
      min: 0,
      acquire: 10000,
      idle: 10000
    }
  
  });

// =============================================================================

const db = {};
db.sql = {};
db.nosql = {};

db.sql.sequelize = sequelize;
db.sql.Sequelize = Sequelize;

const News = require('../models/news.model');

db.nosql.News = News;


const User = require('../models/user.model')(sequelize, Sequelize);
db.sql.User = User;    
const Transaction = require('../models/transaction.model')(sequelize, Sequelize);
Transaction.sync();
db.sql.Transaction = Transaction;   

module.exports = db;