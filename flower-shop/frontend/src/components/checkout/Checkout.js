import React from "react";
import { Container, Typography, TextField, Button, Grid, Paper } from "@mui/material";
import axiosInstance from "../../helper/axios";
import { useLocation, useNavigate } from "react-router-dom";

class Checkout extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            deliveryData: {
                nume: "",
                adresa: "",
                oras: "",
                judet: "",
                codPostal: "",
                telefon: "",
                email: "",
                observatii: ""
            },
            cartItems: props.location?.state?.cartItems//luam produsele din cos

        };
    }

    handleInputChange = (e) => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            deliveryData: {
                ...prevState.deliveryData,
                [name]: value
            }
        }));
    };

    handleSubmit = async (e) => {
        e.preventDefault();

        const userData = JSON.parse(localStorage.getItem("USER_DATA"));
        const { cartItems } = this.state;

        if (!cartItems || cartItems.length === 0) {
            alert("Coșul tău este gol!");
            return;
        }

        try {
            const orderData = { //trimitem comanda la backend
                userId: userData.id,
                fullName: userData.name,
                email: this.state.deliveryData.email,
                phone: userData.phoneNumber,
                address: userData.address,
                orderDetails: cartItems.map(item => ({
                    productId: item.product.id,
                    quantity: item.quantity,
                    price: item.product.price
                }))
            };

            const response = await axiosInstance.post("/order/checkout", orderData);

            if (response.data.success) {
                localStorage.removeItem("cart");
                this.props.navigate("/order-confirmation", {
                    state: {
                        orderId: response.data.orderId,
                        deliveryData: this.state.deliveryData
                    }
                });
            }
        } catch (error) {
            alert(error.response?.data || "Eroare la plasarea comenzii");
        }
    };


    render() {
        const { deliveryData, cartItems } = this.state;
        const total = cartItems.reduce((sum, item) => sum + (item.product.price * item.quantity), 0);

        return (
            <Container maxWidth="md" sx={{ py: 4 }}>
                <Typography variant="h4" gutterBottom align="center">
                    Finalizare comandă
                </Typography>

                <Grid container spacing={4}>
                    <Grid item xs={12} md={6}>
                        <Paper elevation={3} sx={{ p: 3 }}>
                            <Typography variant="h6" gutterBottom>
                                Date de livrare
                            </Typography>
                            <form onSubmit={this.handleSubmit}>
                                <TextField
                                    fullWidth
                                    label="Nume complet"
                                    name="nume"
                                    value={deliveryData.nume}
                                    onChange={this.handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Adresă"
                                    name="adresa"
                                    value={deliveryData.adresa}
                                    onChange={this.handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <Grid container spacing={2}>
                                    <Grid item xs={6}>
                                        <TextField
                                            fullWidth
                                            label="Oraș"
                                            name="oras"
                                            value={deliveryData.oras}
                                            onChange={this.handleInputChange}
                                            margin="normal"
                                            required
                                        />
                                    </Grid>
                                    <Grid item xs={6}>
                                        <TextField
                                            fullWidth
                                            label="Județ"
                                            name="judet"
                                            value={deliveryData.judet}
                                            onChange={this.handleInputChange}
                                            margin="normal"
                                            required
                                        />
                                    </Grid>
                                </Grid>
                                <TextField
                                    fullWidth
                                    label="Cod poștal"
                                    name="codPostal"
                                    value={deliveryData.codPostal}
                                    onChange={this.handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Telefon"
                                    name="telefon"
                                    value={deliveryData.telefon}
                                    onChange={this.handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Email"
                                    name="email"
                                    type="email"
                                    value={deliveryData.email}
                                    onChange={this.handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Observații (opțional)"
                                    name="observatii"
                                    value={deliveryData.observatii}
                                    onChange={this.handleInputChange}
                                    margin="normal"
                                    multiline
                                    rows={3}
                                />
                            </form>
                        </Paper>
                    </Grid>

                    <Grid item xs={12} md={6}>
                        <Paper elevation={3} sx={{ p: 3 }}>
                            <Typography variant="h6" gutterBottom>
                                Detalii comandă
                            </Typography>
                            {cartItems.map(item => (
                                <div key={item.id} style={{ marginBottom: '1rem' }}>
                                    <Typography>
                                        {item.product.name} x {item.quantity}
                                    </Typography>
                                    <Typography color="text.secondary">
                                        {item.product.price * item.quantity} lei
                                    </Typography>
                                </div>
                            ))}
                            <Typography variant="h6" sx={{ mt: 2 }}>
                                Total: {total.toFixed(2)} lei
                            </Typography>
                            <Button
                                fullWidth
                                variant="contained"
                                color="primary"
                                size="large"
                                sx={{ mt: 3 }}
                                onClick={this.handleSubmit}
                            >
                                Confirmă comanda
                            </Button>
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        );
    }
}

export default function CheckoutWrapper(props) {
    const navigate = useNavigate();
    const location = useLocation();
    return <Checkout {...props} navigate={navigate} location={location} />;
}