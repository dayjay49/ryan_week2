const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const db = require('./queries')
const port = 3000
   

// DB Connect String
var connection = "postgres://postgres:1234@localhost/week2db";

// Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));


app.get('/', function(req, res) {
	console.log('TEST');
	res.json({info: 'Node.js, Express, and Postgres API'})
});


app.get('/users', db.getUsers)
app.get('/users/:email', db.getUserByEmail)
app.post('/users', db.registerUser)
app.put('/users/:email', db.updateUser)
app.delete('/users/:email', db.deleteUser)

app.get('/contacts/:email', db.getContactsByUser)
app.post('/contacts', db.addContact)
app.delete('/contacts/:phone_number', db.deleteContact)

// Server
app.listen(port, () => {
  console.log(`App running on port ${port}.`)
})

