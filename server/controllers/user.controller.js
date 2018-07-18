const User = require('../models/user.model');
const Decimal = require('decimal');
const db = require('../config/db');

//Simple version, without validation or sanitation
exports.test = (req, res) => {
    res.send('Greetings from the Test controller!');
};

exports.getUser = (req, res) => {
    db.sql.User.find({
        where: { username: req.decoded.username }
        }).then(user => {
        res.json(user);
      });
}
exports.transferMoney = (req, res) => {
    
    db.sql.User.find({
        where: { username: req.decoded.username }
        }).then(from_user => {
            //Check balance
            if (from_user.balance < req.body.amount) {
                res.json({ success: false, message: 'Not enough balance!' });
            }
            else {
                //Check receiver existence
                db.sql.User.find({
                    where: { username: req.body.to_user }
                }).then(to_user =>{
                    if (!to_user) {
                        res.json({ success: false, message: 'Receiver not found.' });
                    }
                    //sending money
                    else {
                        return db.sql.sequelize.transaction((t) => {
                            // logging result
                            const rs = {
                                from_user: from_user.username,
                                to_user: to_user.username,
                                amount: Decimal(req.body.amount).toNumber()
                            };

                            return from_user.updateAttributes({balance: Decimal(from_user.balance).sub(req.body.amount).toNumber()}, {transaction: t})
                            .then(() => {
                                return to_user.updateAttributes({balance: Decimal(to_user.balance).add(req.body.amount).toNumber()}, {transaction: t}).then(() => {
                                    rs.success = true;
                                    db.sql.Transaction.create(rs);
                                    res.json({ success: true, message: 'Successful', transaction: rs});

                                })
                                
                            //Rollback if error
                            }).catch((error) => {
                                rs.success = false; 
                                db.sql.Transaction.create(rs);
                                res.json({ success: false, message: 'There is an error', transaction: rs});
                            })
                        })
                       
                    }
                })
            }
      });
}

exports.getTransaction = (req, res) => {
    db.sql.Transaction.findAll({
        where: { [db.sql.Sequelize.Op.or]: [{from_user: req.decoded.username}, {to_user: req.decoded.username}] }
    }).then(transactions => {
        res.json(transactions);
    })
}