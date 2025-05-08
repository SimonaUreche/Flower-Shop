import React, { useEffect, useState } from "react";
import { Container, Typography, Button, Box, Grid } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import axiosInstance from "../../helper/axios";
import ProductItem from "../products/ProductItem";

const OrderConfirmation = () => {
    const location = useLocation();
    const { orderId, deliveryData } = location.state || {};
    const [recommended, setRecommended] = useState([]);

    const addToCart = async (product) => {
        try {
            const user = JSON.parse(localStorage.getItem("USER_DATA")) || { id: 0 };
            const cartItemDTO = {
                userId: user?.id || 0,
                product: { id: product.id },
                quantity: 1
            };
            await axiosInstance.post("/cartItem", cartItemDTO);

            const event = new Event('cartUpdated');
            window.dispatchEvent(event);
        } catch (error) {
            console.error("Error adding to cart:", error);
        }
    };

    useEffect(() => {
        axiosInstance.get("/products")
            .then(res => {
                const allProducts = res.data || [];
                const shuffled = allProducts.sort(() => 0.5 - Math.random());
                setRecommended(shuffled.slice(0, 2));
            })
            .catch(err => console.error("Failed to fetch products for recommendation", err));
    }, []);

    return (
        <Container maxWidth="md" sx={{ py: 4, textAlign: "center" }}>
            <CheckCircleIcon color="success" sx={{ fontSize: 80, mb: 2 }} />
            <Typography variant="h4" gutterBottom>
                Comanda a fost plasată cu succes!
            </Typography>
            <Typography variant="h6" sx={{ mb: 4 }}>
                Număr comandă: #{orderId}
            </Typography>

            <Box sx={{ textAlign: "left", maxWidth: 500, mx: "auto", mb: 4 }}>
                <Typography>
                    Am trimis un email de confirmare la adresa <strong>{deliveryData?.email}</strong> cu toate detaliile.
                </Typography>
                <Typography sx={{ mt: 2 }}>
                    Vă vom contacta în scurt timp pentru confirmarea livrării.
                </Typography>
            </Box>

            <Button
                variant="contained"
                component={Link}
                to="/products"
                size="large"
            >
                Continuă cumpărăturile
            </Button>

            {recommended.length > 0 && (
                <Box sx={{ mt: 4, p: 3, bgcolor: "#f5f5f5", borderRadius: 2 }}>
                    <Typography variant="h5" gutterBottom sx={{ mt: 6, color: 'primary.main' }}>
                        Alte produse pe care le vei iubi...
                    </Typography>
                    <Grid container spacing={2} justifyContent="center">
                        {recommended.map((prod) => (
                            <Grid item xs={12} sm={6} md={4} key={prod.id}>
                                <ProductItem product={prod} onAddToCart={() => addToCart(prod)} />
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            )}
        </Container>
    );
};

export default OrderConfirmation;
