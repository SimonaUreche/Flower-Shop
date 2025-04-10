import React from "react";
import axiosInstance from '../../helper/axios';
import { Container, Typography, Grid, IconButton, Badge, AppBar, Toolbar} from "@mui/material";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ProductItem from "./ProductItem";
import { Link } from "react-router-dom";

class Product extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            products: [],
            cartItems: JSON.parse(localStorage.getItem('cart')) || [],
            cartCount: JSON.parse(localStorage.getItem('cart'))?.length || 0
        };
    }

    componentDidMount() {
        this.fetchProducts();
        this.updateCartCount();
        window.addEventListener('cartUpdated', this.updateCartCount);
    }

    componentWillUnmount() {
        window.removeEventListener('cartUpdated', this.updateCartCount);
    }


    fetchProducts = () => {
        axiosInstance.get("/products")
            .then(res => {
                const products = res.data || [];
                this.setState({ products });
            })
            .catch(error => {
                console.log(error);
            });
    };
    updateCartCount = async () => {
        try {
            const response = await axiosInstance.get("/cartItem");
            this.setState({
                cartCount: response.data.length,
                cartItems: response.data
            });
            localStorage.setItem('cart', JSON.stringify(response.data));
        } catch (error) {
            console.error("Error updating cart:", error);
        }
    };

    addToCart = async (product) => {
        try {

            const cartItem = {
                product: product,
                quantity: 1,
                price: product.price,
                user: { id: 1 } // sau orice ID de user test folosești
            };

            await axiosInstance.post("/cartItem", cartItem);

            const updatedCartResponse = await axiosInstance.get("/cartItem");
            const updatedCart = updatedCartResponse.data;

            localStorage.setItem('cart', JSON.stringify(updatedCart));
            this.setState({
                cartCount: updatedCart.length
            });
        } catch (error) {
            console.error("Error adding to cart:", error);
        }
    };


    render() {
        const { products, cartCount } = this.state;

        return (
            <>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                            Magazinul Meu
                        </Typography>
                        <IconButton
                            color="inherit"
                            component={Link}
                            to="/cart"
                            aria-label="cart"
                        >
                            <Badge badgeContent={cartCount} color="error">
                                <ShoppingCartIcon />
                            </Badge>
                        </IconButton>
                    </Toolbar>
                </AppBar>

                <Container maxWidth="lg" sx={{ py: 4 }}>
                    <Typography variant="h4" gutterBottom align="center" sx={{ mb: 4 }}>
                        Lista produse
                    </Typography>

                    <Grid
                        container
                        spacing={4}
                        justifyContent="center"
                        sx={{
                            margin: '0 auto',
                            maxWidth: '1200px'
                        }}
                    >
                        {products.map((product) => (
                            <Grid
                                item
                                xs={12}
                                sm={5}
                                md={5}
                                lg={4}
                                key={product.id}
                                sx={{
                                    display: 'flex',
                                    justifyContent: 'center'
                                }}
                            >
                                <ProductItem
                                    product={product}
                                    onAddToCart={() => this.addToCart(product)}
                                    sx={{ width: '100%' }}
                                />
                            </Grid>
                        ))}
                    </Grid>
                </Container>
            </>
        );
    }
}

export default Product;