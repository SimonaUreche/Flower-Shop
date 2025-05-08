import React from "react";
import axiosInstance from '../../helper/axios';
import { Container, Typography, Grid, IconButton, Badge, AppBar, Toolbar } from "@mui/material";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ProductItem from "./ProductItem";
import { Link } from "react-router-dom";

class Product extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            products: [],
            cartItems: [],
            cartCount: 0
        };
        this.updateCartCount = this.updateCartCount.bind(this);
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
                this.setState({ products: res.data || [] });
            })
            .catch(error => {
                console.error("Error fetching products:", error);
            });
    };

    updateCartCount = async () => {
        try {
            const response = await axiosInstance.get("/cartItem");
            const cart = response.data || [];
            this.setState({
                cartItems: cart,
                cartCount: cart.length
            });
            localStorage.setItem('cart', JSON.stringify(cart));
        } catch (error) {
            console.error("Error updating cart:", error);
        }
    };

    addToCart = async (product) => {
        try {
            const user = JSON.parse(localStorage.getItem("USER_DATA")) || { id: 0 };
            const cartItemDTO = {
                //id,
                userId: user?.id || 0,
                product: {
                    id: product.id
                },
                quantity: 1
            };
            await axiosInstance.post("/cartItem", cartItemDTO);
            this.updateCartCount();

            const event = new Event('cartUpdated');
            window.dispatchEvent(event);
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
                        <Typography variant="h6" sx={{ flexGrow: 1 }}>
                            Magazinul Meu
                        </Typography>
                        <IconButton color="inherit" component={Link} to="/cart" aria-label="cart">
                            <Badge badgeContent={cartCount} color="error">
                                <ShoppingCartIcon />
                            </Badge>
                        </IconButton>
                    </Toolbar>
                </AppBar>

                <Container maxWidth="lg" sx={{ py: 4 }}>
                    <Typography variant="h4" gutterBottom align="center">
                        Lista produse
                    </Typography>

                    <Grid container spacing={4} justifyContent="center">
                        {products.map((product) => (
                            <Grid item xs={12} sm={6} md={4} key={product.id}>
                                <ProductItem
                                    product={product}
                                    onAddToCart={() => this.addToCart(product)}
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
