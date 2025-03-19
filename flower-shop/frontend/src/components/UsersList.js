import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, TextField } from '@mui/material';

function UsersList() {
    const [users, setUsers] = useState([]);
    const [newUser, setNewUser] = useState({
        name: '',
        email: '',
        phoneNumber: '',
        address: '',
        role: 'CLIENT',
    });

    useEffect(() => {
        axios.get('http://localhost:8080/users')
            .then(response => {
                setUsers(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the users!', error);
            });
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewUser({ ...newUser, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (editUser) {
            axios.put(`http://localhost:8080/users/${editUser.id}`, newUser)
                .then(response => {
                    setUsers(users.map(u => (u.id === editUser.id ? response.data : u)));
                    setEditUser(null);
                    setNewUser({ name: '', email: '', phoneNumber: '', address: '', role: 'CLIENT' });
                })
                .catch(error => {
                    console.error('There was an error updating the user!', error);
                });
        } else {
            axios.post('http://localhost:8080/users', newUser)
                .then(response => {
                    setUsers([...users, response.data]);
                    setNewUser({ name: '', email: '', phoneNumber: '', address: '', role: 'CLIENT' });
                })
                .catch(error => {
                    console.error('There was an error adding the user!', error);
                });
        }
    };

    const handleDelete = (id) => {
        axios.delete(`http://localhost:8080/users/${id}`)
            .then(() => {
                setUsers(users.filter(user => user.id !== id));
            })
            .catch(error => {
                console.error('There was an error deleting the user!', error);
            });
    };

    const [editUser, setEditUser] = useState(null);
    const handleEdit = (user) => {
        setEditUser(user);
        setNewUser(user); // Preia datele utilizatorului pentru formular
    };

    return (
        <Container>
            <h1>Lista Utilizatorilor</h1>

            <form onSubmit={handleSubmit}>
                <TextField
                    label="Nume"
                    name="name"
                    value={newUser.name}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Email"
                    name="email"
                    value={newUser.email}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Telefon"
                    name="phoneNumber"
                    value={newUser.phoneNumber}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Adresă"
                    name="address"
                    value={newUser.address}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <Button  sx={{ marginBottom: '30px' }} type="submit" variant="contained" color="primary">Adaugă Utilizator</Button>
            </form>

            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Nume</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Telefon</TableCell>
                            <TableCell>Adresă</TableCell>
                            <TableCell>Rol</TableCell>
                            <TableCell>Acțiuni</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.length > 0 ? (
                            users.map((user) => (
                                <TableRow key={user.id}>
                                    <TableCell>{user.id}</TableCell>
                                    <TableCell>{user.name}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{user.phoneNumber}</TableCell>
                                    <TableCell>{user.address}</TableCell>
                                    <TableCell>{user.role}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="secondary" onClick={() => handleDelete(user.id)}>
                                            Șterge
                                        </Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="primary" onClick={() => handleEdit(user)}>
                                            Editează
                                        </Button>

                                    </TableCell>

                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={7}>No users found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    );
}

export default UsersList;
