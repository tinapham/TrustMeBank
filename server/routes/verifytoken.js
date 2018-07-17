const express = require('express');
const router = express.Router();
const env = require('../config/env');
const jwt    = require('jsonwebtoken'); 

exports.user = (req, res, next) => {
    const authorizationHeader = req.headers['authorization'].split(' ');
    const scheme = authorizationHeader[0];
    const token = authorizationHeader[1];
    // decode token
    if (token && scheme === 'Bearer') {

        // verifies secret and checks exp
        
        jwt.verify(token, env.secret, (err, decoded) => {      
            if (err) {
            return res.json({ success: false, message: 'Failed to authenticate token.' });    
            } else {
            // if everything is good, save to request for use in other routes
            req.decoded = decoded;    
            next();
        
            }
        });
    
    } else {

        // if there is no token
        // return an error
        return res.status(403).send({ 
            success: false, 
            message: 'No token provided.' 
        });
    
    }
};