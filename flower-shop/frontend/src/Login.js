import React from "react";
import {Button,Container, Paper, Typography,  TextField } from "@mui/material";
import axiosInstance from "./helper/axios";
import history from './helper/history';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            loginSuccess: {
                id: 0,
            }
        };
    }

    handleInput = event => {
        const { value, name } = event.target;
        this.setState({
            [name]: value
        });

        console.log(this.state);
    };

    onSubmitFunction = event => {
        event.preventDefault();

        if (!this.state.username || !this.state.password) {
            this.setState({ error: "Username și parolă sunt obligatorii" });
            return;
        }

        let credentials = {
            username: this.state.username,
            password: this.state.password
        }

        axiosInstance.post("/login", credentials)
            .then(
                res => {
                    const val = res.data;
                    this.setState({
                        loginSuccess: val
                    });
                    if (val.id !== 0) {
                        localStorage.setItem("USER_DATA", JSON.stringify({
                            id: val.id,
                            name: val.name,
                            email: val.email,
                            password: val.password, // ⚠️ Doar pentru dezvoltare!
                            phoneNumber: val.phoneNumber,
                            address: val.address,
                            role: val.role
                        }));
                        history.push("/products");
                        window.location.reload();
                    }
                }
            )
            .catch(error => {
                console.log(error);
                alert("Invalid Credentials");
            })
    }



    render() {
        return (
            <div style={{
                background: "#f2f2f2",
                height: "100vh",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                fontFamily: "Arial, sans-serif"
            }}>
                <Container maxWidth="sm">
                    <Paper elevation={3} style={{ padding: "30px", borderRadius: "12px", background: "#ffffff" }}>
                        <Typography variant="h5" align="center" gutterBottom style={{ fontWeight: "bold" }}>
                            Login
                        </Typography>
                        <form onSubmit={this.onSubmitFunction}>
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                onChange={this.handleInput}
                                autoFocus
                            />
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                onChange={this.handleInput}
                            />
                            {this.state.error && (
                                <Typography color="error" align="center" style={{ marginTop: "10px" }}>
                                    {this.state.error}
                                </Typography>
                            )}
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="secondary"
                                style={{ marginTop: "20px" }}
                            >
                                Sign In
                            </Button>
                        </form>
                    </Paper>
                </Container>
            </div>
        );
    }
}

export default Login;