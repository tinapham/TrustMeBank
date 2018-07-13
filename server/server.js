'use strict';

require('dotenv').load();

const express = require('express');

const logger = require('morgan');

const bodyParser = require('body-parser');

const mongoose   = require('mongoose');

// BASE SETUP
// =============================================================================
mongoose.connect(`mongodb://${process.env.MONGODB_USER}:${process.env.MONGODB_PASSWORD}@${process.env.MONGODB_HOST}/${process.env.MONGODB_DB}`, { useNewUrlParser: true }); 
// mongoose.Promise = global.Promise;
// const db = mongoose.connection;
// db.on('error', console.error.bind(console, 'MongoDB connection error:'));

let News = require('./models/news.model');

var test_instance = new News({id: 1, title: 'test', body: 'blablo'});

var test_instance2 = new News({id: 2, title: 'test', body: 'blablo'});

// Save the new model instance, passing a callback
test_instance.save(err => {
  if (err) return handleError(err);
  // saved!
});

test_instance2.save(err => {
  if (err) return handleError(err);
  // saved!
});

const news = News.find((err, news) => {
  if (err) return handleError(err);

});


// Constants
const PORT = 3000;
const HOST = '0.0.0.0';

// App
const app = express();

// Log requests to the console.
app.use(logger('dev'));

// Parse incoming requests data (https://github.com/expressjs/body-parser)
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

// Routers

const newsRouter = require('./routes/news.route')

// REGISTER OUR ROUTES -------------------------------

app.use('/news', newsRouter.router);





app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);