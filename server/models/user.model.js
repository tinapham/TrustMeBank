'use strict'

module.exports = (sequelize, DataTypes) => {
    const User = sequelize.define('user', {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true
        },
        username: {
            type: DataTypes.STRING,
            primaryKey: true
        },
        password: {
            type: DataTypes.STRING
        },
        balance: {
            type: DataTypes.DECIMAL(13,2),
            allowNull: false, 
            defaultValue: 0 
        }
    });
    return User;
};
