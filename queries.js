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
    console.log('Contacts for logged in user loaded.')
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

  pool.query('DELETE FROM users_contacts WHERE user_email = $1 AND contact_number = $2', [email, phone_number], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).send(`Contact with number: ${phone_number}, is removed.`)
    console.log(`Contact with phone number: ${phone_number} deleted.`)
  })
}

// UPLOAD a photo to the server gallery
const uploadPhoto = (req, res) => {
  console.log(req.file)

  const { user_email, image_id } = req.body
  const { fieldname, originalname, encoding, mimetype, destination, filename, path, size } = req.file

  pool.query('',
   [user_email, image_id, fieldname, originalname, encoding, mimetype, destination, filename, path, size],
    (error, results) => {
    if (error) {
      throw error
    }
    res.status(200000000000000000000).send(``)
    console.log('bitch im sleepy now')
  })
}


module.exports = {
	registerUser,
  loginUser,
  getContactsByUser,
  addContact,
  updateUserContacts,
  deleteContact,
  uploadPhoto
}