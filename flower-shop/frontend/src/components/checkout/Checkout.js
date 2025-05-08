import React, { useState } from "react";
import { Container, Typography, TextField, Button, Grid, Paper } from "@mui/material";
import axiosInstance from "../../helper/axios";
import { useNavigate, useLocation } from "react-router-dom"; // Folosește hook-uri

export default function Checkout() {
    const [deliveryData, setDeliveryData] = useState({
        nume: "",
        adresa: "",
        oras: "",
        judet: "",
        codPostal: "",
        telefon: "",
        email: "",
        observatii: ""
    });
    const [cartItems] = useState(useLocation().state?.cartItems || []);
    const navigate = useNavigate(); // Folosește useNavigate pentru a naviga

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setDeliveryData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        const user = JSON.parse(localStorage.getItem("USER_DATA")) || { id: 0 };
        e.preventDefault();

        const requestData = {
            userId: user.id,
            fullName: deliveryData.nume,
            email: deliveryData.email,
            phone: deliveryData.telefon,
            address: deliveryData.adresa,
            oras: deliveryData.oras,
            judet: deliveryData.judet,
            codPostal: deliveryData.codPostal,
            orderDetails: cartItems.map(item => ({
                productId: item.product.id,
                productName: item.product.name,
                quantity: item.quantity,
                price: item.product.price,
                subtotal: item.quantity * item.product.price
            }))
        };

        try {
            const response = await axiosInstance.post("/orders/checkout", requestData);
            console.log("Checkout response", response.data);
            navigate("/order-confirmation", {
                state: { orderId: response.data.id, deliveryData }
            });

        } catch (error) {
            console.error("Checkout error:", error);
            alert("Eroare la trimiterea comenzii: " + (error.response?.data || error.message));
        }
    };

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
                        <form onSubmit={handleSubmit}>
                            <TextField
                                fullWidth
                                label="Nume complet"
                                name="nume"
                                value={deliveryData.nume}
                                onChange={handleInputChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                fullWidth
                                label="Adresă"
                                name="adresa"
                                value={deliveryData.adresa}
                                onChange={handleInputChange}
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
                                        onChange={handleInputChange}
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
                                        onChange={handleInputChange}
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
                                onChange={handleInputChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                fullWidth
                                label="Telefon"
                                name="telefon"
                                value={deliveryData.telefon}
                                onChange={handleInputChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                fullWidth
                                label="Email"
                                name="email"
                                type="email"
                                value={deliveryData.email}
                                onChange={handleInputChange}
                                margin="normal"
                                required
                            />
                            <TextField
                                fullWidth
                                label="Observații (opțional)"
                                name="observatii"
                                value={deliveryData.observatii}
                                onChange={handleInputChange}
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
                            onClick={handleSubmit}
                        >
                            Confirmă comanda
                        </Button>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
}
