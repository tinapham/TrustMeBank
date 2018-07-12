const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let NewsSchema = new Schema({
    id: {type: Number, required: true},
    title: {type: String, required: true, max: 100},
    body: {type: String, required: true, max: 10000},
    date: { type: Date, default: Date.now }
});

module.exports = mongoose.model('News', NewsSchema);