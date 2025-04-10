import React from "react";
import axiosInstance from '../../helper/axios';
import { Container, Typography, Select, MenuItem, Grid } from "@mui/material";
import UserItem from "./UserItem";
import EditUserForm from "./EditUserForm";

class Users extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            sort: 'asc',
            selectedUser: null,
            isEditOpen: false,
        };
    }

    componentDidMount() {
        this.fetchUsers();
    }

    fetchUsers = () => {
        axiosInstance.get("/users")
            .then(res => {
                const users = res.data || [];
                this.setState({ users }, () => {
                    if (this.state.sort) {
                        this.sortUsers();
                    }
                });
            })
            .catch(error => {
                console.log(error);
            });
    };

    handleDelete = (id) => {
        axiosInstance.delete(`/users/${id}`)
            .then(() => this.fetchUsers())
            .catch(err => console.error("Eroare la ștergere:", err));
    };

    handleEditClick = (user) => {
        this.setState({ selectedUser: user, isEditOpen: true });
    };

    handleEditClose = () => {
        this.setState({ selectedUser: null, isEditOpen: false });
    };

    handleEditSave = (updatedUser) => {
        axiosInstance.put("/users", updatedUser)
            .then(() => {
                this.setState({ isEditOpen: false, selectedUser: null });
                this.fetchUsers();
            })
            .catch(err => console.error("Eroare la actualizare:", err));
    };

    handleSortChange = (e) => {
        const sortDirection = e.target.value;
        this.setState({ sort: sortDirection }, () => {
            if (sortDirection) {
                this.sortUsers();
            } else {
                this.fetchUsers(); //sortare resetata la la default
            }
        });
    };

    sortUsers = () => {
        const sorted = [...this.state.users].sort((a, b) =>
            this.state.sort === "asc"
                ? (a.name || '').localeCompare(b.name || '')
                : (b.name || '').localeCompare(a.name || '')
        );

        this.setState({ users: sorted });
    };



    render() {
        const { users, sort, selectedUser, isEditOpen } = this.state;

        return (
            <Container maxWidth="md" sx={{ py: 4 }}>
                <Typography variant="h4" gutterBottom align="center">
                    Lista Utilizatorilor
                </Typography>

                <Grid container justifyContent="flex-end" sx={{ mb: 2 }}>
                    <Select
                        value={sort}
                        onChange={this.handleSortChange}
                        displayEmpty
                    >
                        <MenuItem value="">Default</MenuItem>
                        <MenuItem value="asc">A-Z</MenuItem>
                        <MenuItem value="desc">Z-A</MenuItem>
                    </Select>
                </Grid>

                {users.map(user => (
                    <UserItem
                        key={user.id}
                        user={user}
                        onDelete={this.handleDelete}
                        onEdit={this.handleEditClick}
                    />
                ))}

                {selectedUser && (
                    <EditUserForm
                        user={selectedUser}
                        open={isEditOpen}
                        onClose={this.handleEditClose}
                        onSave={this.handleEditSave}
                    />
                )}
            </Container>
        );
    }
}

export default Users;
