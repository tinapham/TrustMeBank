'use strict';

require('dotenv').load();

const express = require('express');

const logger = require('morgan');

const bodyParser = require('body-parser');

const startup = require('./startup');

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
const loginRouter = require('./routes/login.route')


// REGISTER OUR ROUTES -------------------------------

app.use('/news', newsRouter.router);
app.use('/login', loginRouter.router);


app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);