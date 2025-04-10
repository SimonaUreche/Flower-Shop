import React from "react";
import { Container, Typography, Button, Box } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';

const OrderConfirmation = () => {
    const location = useLocation();
    const { orderId, deliveryData } = location.state || {};

    return (
        <Container maxWidth="md" sx={{ py: 4, textAlign: 'center' }}>
            <CheckCircleIcon color="success" sx={{ fontSize: 80, mb: 2 }} />
            <Typography variant="h4" gutterBottom>
                Comanda a fost plasată cu succes!
            </Typography>
            <Typography variant="h6" sx={{ mb: 4 }}>
                Număr comandă: #{orderId}
            </Typography>

            <Box sx={{ textAlign: 'left', maxWidth: 500, mx: 'auto', mb: 4 }}>
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
        </Container>
    );
};

export default OrderConfirmation;
