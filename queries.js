/*const Sequelize = require('sequelize');

const sequelize = new Sequelize(connection);


const connection = 'postgres://postgres:1234@localhost:5432/week2db'


// testing connection
sequelize
	.authenticate()
	.then(() => {
		console.log('Connection has been established successfully.');
	})
	.catch(err => {
		console.error('Unable to connect to the database: ', err);
	});*/


//////////////////////////////////////////////////////////////////////////
const Pool = require('pg').Pool
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'week2db',
  password: '1234',
  port: 5432,
})

// GET all users
const getUsers = (req, res) => {
  pool.query('SELECT * FROM contacts ORDER BY id ASC', (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
  })
}

// GET a single user by id
const getUserById = (req, res) => {
  const id = parseInt(req.params.id)

  pool.query('SELECT * FROM contacts WHERE id = $1', [id], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).json(results.rows)
  })
}

//POST a new user
const createUser = (req, res) => {
  const { name, phone_number } = req.body

  pool.query('INSERT INTO contacts (contact_name, phone_number) VALUES ($1, $2)', 
  	[name, phone_number], (error, results) => {
    if (error) {
      throw error
    }
    res.status(201).send(`User added with ID: ${results.insertId}`)
  })
}

// POST existing user
// const loginUser = (req, res) => {
// 	const { name }

// 	pool.query('SELECT password FROM users WHERE ')
// }


//PUT updated data in an existing user

const updateUser = (req, res) => {
  const id = parseInt(req.params.id)
  const { name, phone_number } = req.body

  pool.query(
    'UPDATE contacts SET contact_name = $1, phone_number = $2 WHERE id = $3',
    [name, phone_number, id],
    (error, results) => {
      if (error) {
        throw error
      }
      res.status(200).send(`User modified with ID: ${id}`)
    }
  )
}

//DELETE a user
const deleteUser = (req, res) => {
  const id = parseInt(req.params.id)

  pool.query('DELETE FROM contacts WHERE id = $1', [id], (error, results) => {
    if (error) {
      throw error
    }
    res.status(200).send(`User deleted with ID: ${id}`)
  })
}

module.exports = {
	getUsers,
	getUserById,
	createUser,
	updateUser,
	deleteUser
}
//////////////////////////////////////////////////////////////////////////