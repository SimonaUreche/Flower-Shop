import React from "react";
import { Card, CardContent, Typography, Avatar, Button, Box, Chip } from "@mui/material";

class ProductItem extends React.Component {
    render() {
        const { product, onAddToCart } = this.props;

        return (
            <Card sx={{
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                borderRadius: 2,
                boxShadow: 3,
                transition: 'transform 0.3s',
                '&:hover': {
                    transform: 'scale(1.02)',
                    boxShadow: 6
                }
            }}>
                <Box sx={{ p: 20}}>
                    <Box sx={{ display: 'flex', mb: 7}}>
                        <Avatar
                            src={`http://localhost:8080/${product.image}`} //in backend am stocata doar calea relativa la imagine, ceea ce nu e destul
                            variant="square"   // => ii dam url ul complet
                            sx={{
                                width: 180,
                                height: 180,
                                mr: 10
                            }}
                        />
                        <Box sx={{ flex: 1 }}>
                            <Typography variant="h6" sx={{ fontWeight: 700, mb: 1 }}>
                                {product.name}
                            </Typography>
                            <Typography
                                variant="body2"
                                color="text.secondary"
                                sx={{ mb: 2 }}
                            >
                                {product.description}
                            </Typography>
                        </Box>
                    </Box>

                    <Box sx={{
                        display: 'flex',
                        justifyContent: 'space-between',
                        alignItems: 'center',
                        mt: 'auto'
                    }}>
                        <Box>
                            <Typography variant="h6" color="primary" sx={{ fontWeight: 700 }}>
                                {product.price} lei
                            </Typography>
                            <Chip
                                label={`Stoc: ${product.stock}`}
                                size="medium"
                                color={product.stock > 0 ? "success" : "error"}
                            />
                        </Box>
                    </Box>
                </Box>

                <Button
                    fullWidth
                    variant="contained"
                    color="primary"
                    size="large"
                    sx={{
                        borderRadius: '0 0 8px 8px',
                        py: 1.5,
                        fontWeight: 600
                    }}
                    onClick={() => onAddToCart(product)}
                >
                    Adaugă în coș
                </Button>
            </Card>
        );
    }
}

export default ProductItem;