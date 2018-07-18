'use strict'
const User = require('./user.model');

module.exports = (sequelize, DataTypes) => {
    const Transaction = sequelize.define('transaction', {
        id: {
            type: DataTypes.UUID,
            defaultValue: DataTypes.UUIDV1,
            primaryKey: true
        },
        from_user: {
            type: DataTypes.STRING,
            refrences: {
                model: User,
                key: 'username'
            }
        },
        to_user: {
            type: DataTypes.STRING,
            refrences: {
                model: User,
                key: 'username'
            }
        },
        amount: {
            type: DataTypes.DECIMAL(13,2),
            allowNull: false
        },
        success: {
            type: DataTypes.BOOLEAN,
            allowNull: false
        }
    });
    return Transaction;
};
 