const express = require('express');
const router = express.Router();

const loginController = require('../controllers/login.controller');

router.get('/', loginController.getAll);

module.exports = { router: router };