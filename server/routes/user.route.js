const express = require('express');
const router = express.Router();

const userController = require('../controllers/user.controller');

const verifytoken = require('./verifytoken');
router.use(verifytoken.user);
router.get('/',userController.getAll);

module.exports = { router: router }; 