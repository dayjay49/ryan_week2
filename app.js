const express = require('express')
const bodyParser = require('body-parser')
const multer = require('multer')
const path = require('path')
const app = express()
const db = require('./queries')
const port = 3000
   

// Set Storage Engine
var storage = multer.diskStorage({
	destination: function(req, file, cb) { //isn't this just '/uploads/' ?
		cb(null, './my_uploads/')
	},
	filename: function(req, file, cb) {
		// var originalname = file.originalname
		// var extension = originalname.split(".")
		// filename = Date.now() + '.' + extension[extension.length - 1]
		cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname))
	}
})

//Initialize Upload variable
const upload = multer({
	storage: storage,
	dest: './my_uploads/'
})

// DB Connect String
var connection = "postgres://postgres:1234@localhost/week2db";

// Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.use('./my_uploads/', express.static('./my_uploads/'))


app.get('/', function(req, res) {
	console.log('TEST');
	res.json({info: 'Node.js, Express, and Postgres API'})
});

app.post('/register', db.registerUser)
app.post('/login', db.loginUser)
app.get('/contacts/:email', db.getContactsByUser)
app.post('/contacts', db.addContact)
app.post('/update', db.updateUserContacts)
app.post('/remove', db.deleteContact)
app.post('/gallery/add', upload.single('imageFile'), db.uploadPhoto)

// Server
app.listen(port, () => {
  console.log(`App running on port ${port}.`)
})

