// const User = require('../models/user.model');

const db = require('../config/db');
const jwt    = require('jsonwebtoken'); 
const env = require('../config/env');

//Simple version, without validation or sanitation
exports.test = (req, res) => {
    res.send('Greetings from the Test controller!');
};
exports.authenticate = (req, res) => {
    db.sql.User.find({
        where: { username: req.body.username }
    }).then((user) => {
        if (!user) {
            res.json({ success: false, message: 'Authentication failed. User not found.' });
        } else if (user) {
            // check if password matches
            if (user.password != req.body.password) {
                res.json({ success: false, message: 'Authentication failed. Wrong password.' });
            } else {
                const payload = {
                    id: user.id,
                    username: user.username
                  };
                const token = jwt.sign(payload, env.secret, {
                    expiresIn: '24h' // expires in 24 hours
                  });
                
                res.json({
                success: true,
                message: 'Authentication succeeded!',
                token: token
                });
            }
        }
    })
};

