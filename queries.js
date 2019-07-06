const Pool = require('pg').Pool
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'week2db',
  password: '1234',
  port: 5432,
})



// -------------------------------USER QUERIES-------------------------------------

// GET all users
const getUsers = (req, res) => {
  pool.query('SELECT * FROM users', (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
  })
}

// GET a single user by email for log in
const getUserByEmail = (req, res) => {
  const email = req.params.email

  pool.query('SELECT password FROM users WHERE email = $1', [email], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
  })
}

//PUT updated data in an existing user
const updateUser = (req, res) => {
  const { email, name, password } = req.body

  pool.query(
    'UPDATE users SET name = $1, password = $2 WHERE email = $3',
    [name, password, email],
    (error, results) => {
      if (error) {
        throw error
      }
      res.status(200).send(`User modified with email: ${email}`)
    }
  )
}

//DELETE a user
const deleteUser = (req, res) => {
  const email = req.params.email

  pool.query('DELETE FROM users WHERE email = $1', [email], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).send(`User deleted with email: ${email}`)
  })
}

// -------------------------------USER QUERIES-------------------------------------


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

  pool.query('SELECT email FROM users WHERE email = $1 AND password = $2' , [email, password], (error, results) => {
    if (error) {
      throw error
    }
    else if (results.length == 0) {
      res.status(400).send(`Wrong email and/or password. Please enter valid email and password.`)
    }
    else{
      res.status(200).json(results.rows)
    }
  })
}

// GET all contacts for a given user after login verified
const getContactsByUser = (req, res) => {
  const email = req.params.email

  pool.query('SELECT * FROM contacts INNER JOIN users ON contacts.phone_number = users_contacts.contact_number AND users_contacts.user_email = $1',
   [email] , (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
  })
}

// POST a contact for a given user
const addContact = (req, res) => {
  const { phone_number, contact_name, email } = req.body

  pool.query('INSERT INTO contacts (phone_number, contact_name) VALUES ($1, $2) ON CONFLICT (phone_number) DO NOTHING; INSERT INTO users_contacts (user_email, contact_number) VALUES ($3, $1) ON CONFLICT ON CONSTRAINT users_contacts_pkey DO NOTHING',
    [phone_number, contact_name, email], (error, results) => {
      if (error) {
        throw error
      }
      res.status(200).json(results.rows)
    })
}

// DELETE a contact for a given user
const deleteContact = (req, res) => {
  const phone_number = req.params.phone_number

  pool.query('DELETE FROM contacts WHERE phone_number = $1', [phone_number], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).send(`User deleted with phone number: ${phone_number}`)
  })
}

module.exports = {
	getUsers,
	getUserByEmail,
	registerUser,
	updateUser,
	deleteUser,
  getContactsByUser,
  addContact,
  deleteContact
}