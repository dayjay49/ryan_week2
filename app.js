const express = require('express')
const bodyParser = require('body-parser')
const multer = require('multer')
const path = require('path')
const fs = require('fs')
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
// var connection = "postgres://postgres:1234@localhost/week2db";

// Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

// app.use('./my_uploads/', express.static('./my_uploads/'))
//express.static(__dirname + '/my_uploads/')

app.get('/my_uploads/:name', function (req, res) {
  var filename = req.params.name;
  fs.exists(__dirname+'/my_uploads/'+filename, function (exists) {
    if (exists) {
      fs.readFile(__dirname+'/my_uploads/'+filename, function (err, data) {
        res.end(data);
      });
    } else {
      res.end('file does not exists');
    }
  })
});


app.get('/', function(req, res) {
	console.log('TEST');
	res.json({info: 'Node.js, Express, and Postgres API'})
});

app.post('/register', db.registerUser)
app.post('/login', db.loginUser)
app.get('/contacts/:email', db.loadContactsByUser)

app.post('/contacts', db.addContact)
// app.get('contacts/:phone_number', db.getContact)
app.post('/contacts/update', db.updateUserContacts)

app.post('/contacts/remove', db.deleteContact)

app.get('/gallery/:email', db.loadGallery)

app.post('/gallery/add', upload.single('imageFile'), db.uploadPhoto)
app.post('/gallery/update', db.updateUserGallery)

app.post('/gallery/remove', db.deletePhoto)

// Server
app.listen(port, () => {
  console.log(`App running on port ${port}.`)
})

