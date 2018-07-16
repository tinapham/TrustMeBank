const User = require('../models/user.model');

const db = require('../config/db');

//Simple version, without validation or sanitation
exports.test = (req, res) => {
    res.send('Greetings from the Test controller!');
};

exports.getAll = (req, res) => {
    db.sql.User.findAll().then(users => {
        res.json(users);
      });
    

}