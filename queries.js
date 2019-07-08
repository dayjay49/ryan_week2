const Pool = require('pg').Pool
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'week2db',
  password: '1234',
  port: 5432,
})

// -------------------------------CONTACT QUERIES-------------------------------------

//POST a new user
const registerUser = (req, res) => {
  const { email, name, password } = req.body

  pool.query('INSERT INTO users (email, name, password) VALUES ($1, $2, $3)', 
    [email, name, password], (error, results) => {
    if (error) {
      throw error
    }
    res.status(201).send(`New user registered with email: ${email}`) //${results.insertId}`
  })
}

// POST login user and load all contacts of the user logged in
const loginUser = (req, res) => {
  const { email, password } = req.body

  pool.query('SELECT * FROM users WHERE email = $1 AND password = $2' , [email, password], (error, results) => {
    if (error) {
      throw error
    }
    if (results.length == 0) {
      console.log('No matching email and password found.')
    }
    else{
      res.status(200).json(results.rows)
      console.log('Login successful.')
    }
  })
}

// GET all contacts for a given user after login verified
const getContactsByUser = (req, res) => {
  const email = req.params.email

  pool.query('SELECT phone_number, contact_name FROM contacts INNER JOIN users_contacts ON contacts.phone_number = users_contacts.contact_number AND users_contacts.user_email = $1',
   [email] , (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
    console.log('Contacts loaded for logged-in user.')
  })
}

// POST a contact for a given user
const addContact = (req, res) => {
  const { phone_number, contact_name } = req.body

  pool.query(
    'INSERT INTO contacts (phone_number, contact_name) VALUES ($1, $2) ON CONFLICT (phone_number) DO NOTHING',
    [phone_number, contact_name], (error, results) => {
      if (error) {
        throw error
      }
      res.status(201).send(`New contact added`)
    })
}

//POST updated data in an existing user
const updateUserContacts = (req, res) => {
  const { email, phone_number } = req.body

  pool.query('INSERT INTO users_contacts (user_email, contact_number) VALUES ($1, $2) ON CONFLICT ON CONSTRAINT users_contacts_pkey DO NOTHING',
    [email, phone_number],
    (error, results) => {
      if (error) {
        throw error
      }
      res.status(200).send("Updated contacts for current user!")
      console.log('New contact added for user')
    }
  )
}

// DELETE a contact for a given user
const deleteContact = (req, res) => {
  const { email, phone_number } = req.body

  pool.query('DELETE FROM users_contacts WHERE user_email = $1 AND contact_number = $2', 
    [email, phone_number], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).send(`Contact with number: ${phone_number}, is removed.`)
    console.log(`Contact with phone number: ${phone_number} deleted.`)
  })
}


// LOAD gallery from server
const loadGallery = (req, res) => {
  const email = req.params.email

  pool.query('SELECT path FROM gallery INNER JOIN users_gallery ON gallery.filename = users_gallery.filename AND users_gallery.user_email = $1',
   [email], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
    console.log('Gallery successfully loaded for logged-in user.')
  })
}

// UPLOAD a photo to the server gallery
const uploadPhoto = (req, res) => {
  console.log(req.file)
  // const user_email = req.body.user_email
  // const image_id = req.body.image_id

  const fieldname= req.file.fieldname
  const originalname= req.file.originalname
  const encoding= req.file.encoding
  const mimetype= req.file.mimetype
  const destination= req.file.destination
  const filename= req.file.filename
  const path= req.file.path
  const size= req.file.size

  pool.query('INSERT INTO gallery (fieldname, originalname, encoding, mimetype, destination, filename, path, size) VALUES ($1, $2, $3, $4, $5, $6, $7, $8) ON CONFLICT (filename) DO NOTHING',
   [ fieldname, originalname, encoding, mimetype, destination, filename, path, size ],
    (error, results) => {
    if (error) {
      throw error
    }
    res.status(201).send(`${filename}`)
  })
}

// POST updated gallery in an existing user
const updateUserGallery = (req, res) => {
  const { email, filename } = req.body

  pool.query('INSERT INTO users_gallery (user_email, filename) VALUES ($1, $2) ON CONFLICT ON CONSTRAINT users_gallery_pkey DO NOTHING',
   [email, filename], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).send('Updated gallery for current user!')
    console.log('Updated gallery for current user!')
  })
}

// DELETE chosen photo from gallery of logged user
const deletePhoto = (req, res) => {
  const { email, filename } = req.body

  pool.query('DELETE FROM users_gallery WHERE user_email = $1 AND filename = $2',
    [email, filename], (error, results) => {
      if (error) {
        throw error
      }
      res.status(200).send(`Deleted ${filename} for logged user.`)
      console.log(`Deleted ${filename} for logged user.`)
    })
}

module.exports = {
	registerUser,
  loginUser,
  getContactsByUser,
  addContact,
  updateUserContacts,
  deleteContact,
  loadGallery,
  uploadPhoto,
  updateUserGallery,
  deletePhoto
}