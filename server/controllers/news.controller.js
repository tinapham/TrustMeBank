const News = require('../models/news.model');

//Simple version, without validation or sanitation
exports.test = function (req, res) {
    res.send('Greetings from the Test controller!');
};
exports.create = function (req, res) {
    res.json({test: 'Greetings from the Test controller!'});
};