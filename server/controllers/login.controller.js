const News = require('../models/user.model');

const db = require('../db');


//Simple version, without validation or sanitation
exports.test = (req, res) => {
    res.send('Greetings from the Test controller!');
};
exports.create = (req, res) => {
    let u = new User(
        {
            username: req.body.username,
            password: req.body.password
        }
    );

    u.save( err => {
        if (err) throw err;
        res.json({result: 'News Created successfully'});
    });
};
exports.getAll = (req, res) => {
    db.sql.User.findAll().then(owners => {
        res.json(owners);
      });

}