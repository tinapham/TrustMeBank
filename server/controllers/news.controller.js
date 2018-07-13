const News = require('../models/news.model');

//Simple version, without validation or sanitation
exports.test = function (req, res) {
    res.send('Greetings from the Test controller!');
};
exports.create = function (req, res) {
    let news = new News(
        {
            id: req.body.id,
            title: req.body.title,
            body: req.body.body
        }
    );

    news.save(function (err) {
        if (err) throw err;
        res.json({result: 'News Created successfully'});
    })
};
exports.getAll = (req, res) => {
    News.find((err, news) => {
        if (err) return handleError(err);
        res.json(news);
      });

}