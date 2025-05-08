import React from "react";
import {
    Container, Typography, Grid, Button, Paper, Box, IconButton
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import { Link, useNavigate } from "react-router-dom";
import axiosInstance from '../../helper/axios';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';

class Cart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cartItems: []
        };
    }

    componentDidMount() {
        this.fetchCartItems();
    }

    fetchCartItems = async () => {
        try {
            const response = await axiosInstance.get("/cartItem");
            this.setState({ cartItems: response.data });
        } catch (error) {
            console.error("Error fetching cart:", error);
        }
    };
    updateQuantity = async (item, change) => {
        const newQuantity = item.quantity + change;
        if (!item.product || newQuantity < 1 || newQuantity > item.product.stock) return;

        try {
            const user = JSON.parse(localStorage.getItem("USER_DATA")) || { id: 0 };
            const updatedItemDTO = {
                id: item.id,
                userId: user?.id || 0,
                product : item.product,
                quantity: newQuantity
            };


            await axiosInstance.put("/cartItem", updatedItemDTO);
            this.fetchCartItems();
        } catch (error) {
            console.error("Error updating quantity:", error);
        }
    };


    removeFromCart = async (id) => {
        try {
            await axiosInstance.delete(`/cartItem/${id}`);
            this.fetchCartItems();

            const event = new Event('cartUpdated');
            window.dispatchEvent(event);
        } catch (error) {
            console.error("Error removing item:", error);
        }
    };

    finalizeazaComanda = () => {
        const userData = JSON.parse(localStorage.getItem("USER_DATA"));
        if (!userData) {
            alert("Trebuie să te loghezi pentru a finaliza comanda.");
            this.props.navigate("/login");
            return;
        }

        this.props.navigate("/checkout", {
            state: { cartItems: this.state.cartItems },
        });
    };

    render() {
        const { cartItems } = this.state;
        const total = cartItems.reduce((sum, item) => {
            const price = item.product?.price || 0;
            return sum + price * item.quantity;
        }, 0).toFixed(2);

        return (
            <Container maxWidth="lg" sx={{ py: 4 }}>
                <Typography variant="h4" gutterBottom align="center">
                    Coșul tău de cumpărături
                </Typography>

                {cartItems.length === 0 ? (
                    <Box textAlign="center">
                        <Typography variant="h6" sx={{ mb: 2 }}>
                            Coșul tău este gol
                        </Typography>
                        <Button variant="contained" color="primary" component={Link} to="/products">
                            Continuă cumpărăturile
                        </Button>
                    </Box>
                ) : (
                    <>
                        <Grid container spacing={3}>
                            {cartItems.map((item) => (
                                <Grid item xs={12} key={item.id}>
                                    <Paper elevation={3} sx={{ p: 2, display: 'flex', alignItems: 'center' }}>
                                        {item.product ? (
                                            <img
                                                src={`http://localhost:8080/${item.product.image}`}
                                                alt={item.product.name}
                                                style={{
                                                    width: 80,
                                                    height: 80,
                                                    objectFit: 'cover',
                                                    borderRadius: 8,
                                                    marginRight: 16
                                                }}
                                            />
                                        ) : (
                                            <Box
                                                sx={{
                                                    width: 80,
                                                    height: 80,
                                                    backgroundColor: '#eee',
                                                    borderRadius: 2,
                                                    marginRight: 2,
                                                    display: 'flex',
                                                    alignItems: 'center',
                                                    justifyContent: 'center'
                                                }}
                                            >
                                                <Typography variant="caption" color="textSecondary">
                                                    Fără imagine
                                                </Typography>
                                            </Box>
                                        )}

                                        <Box sx={{ flexGrow: 1 }}>
                                            <Typography variant="h6">
                                                {item.product?.name || "Produs necunoscut"}
                                            </Typography>
                                            <Typography>
                                                {item.product?.price || 0} lei x {item.quantity}
                                            </Typography>
                                            {item.product && (
                                                <Box sx={{ display: 'flex', alignItems: 'center', mt: 1 }}>
                                                    <IconButton onClick={() => this.updateQuantity(item, -1)} disabled={item.quantity <= 1}>
                                                        <RemoveIcon />
                                                    </IconButton>
                                                    <Typography sx={{ mx: 2 }}>{item.quantity}</Typography>
                                                    <IconButton onClick={() => this.updateQuantity(item, 1)} disabled={item.quantity >= item.product.stock}>
                                                        <AddIcon />
                                                    </IconButton>
                                                    <Typography variant="body2" color="text.secondary" sx={{ ml: 2 }}>
                                                        În stoc: {item.product.stock}
                                                    </Typography>
                                                </Box>
                                            )}
                                        </Box>

                                        <IconButton color="error" onClick={() => this.removeFromCart(item.id)}>
                                            <DeleteIcon />
                                        </IconButton>
                                    </Paper>
                                </Grid>
                            ))}
                        </Grid>

                        <Box sx={{ mt: 4, textAlign: 'right' }}>
                            <Typography variant="h5" sx={{ mb: 2 }}>
                                Total: {total} lei
                            </Typography>
                            <Button variant="contained" color="primary" size="large" onClick={this.finalizeazaComanda}>
                                Finalizează comanda
                            </Button>
                        </Box>
                    </>
                )}
            </Container>
        );
    }
}

export default function CartWrapper(props) {
    const navigate = useNavigate();
    return <Cart {...props} navigate={navigate} />;
}
