const express = require('express');
const router = express.Router();

const newsController = require('../controllers/news.controller');


// a simple test url to check that all of our files are communicating correctly.
router.get('/test', newsController.test);
router
    .post('/', newsController.create)
    .get('/', newsController.getAll);


module.exports = { router: router };
