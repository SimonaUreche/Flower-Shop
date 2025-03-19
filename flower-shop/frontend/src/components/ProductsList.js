import React, {useEffect, useState} from "react";
import axios from "axios";
import {
    Button,
    Container,
    Paper,
    Table, TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@mui/material";

function ProductsList() {
    const [products, setProducts] = useState([]);
    const [newProducts, setNewProducts] = useState({
        name: '',
        price: '',
        description: '',
        imageUrl: '',
        category: '',
        stock: '',
    });

    useEffect(() => {
        axios.get('http://localhost:8080/product')
            .then(response => {
                console.log("Products received:", response.data);  //Debugging
                setProducts(response.data);
            })
            .catch(error => {
                console.error("Error fetching products:", error);
            });
    }, []);


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewProducts({ ...newProducts, [name]: value });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (editProduct) {
            axios.put(`http://localhost:8080/products/${editProduct.id}`, newProducts)
                .then(response => {
                    setProducts(products.map(p => (p.id === editProduct.id ? response.data : p)));
                    setEditProduct(null);
                    setNewProducts({ name: '', price: '', description: '', imageUrl: '', category: '', stock: '' });
                })
                .catch(error => {
                    console.error('There was an error updating the product!', error);
                });
        } else {
            axios.post('http://localhost:8080/products', newProducts)
                .then(response => {
                    setProducts([...products, response.data]);
                    setNewProducts({ name: '', price: '', description: '', imageUrl: '', category: '', stock: '' });
                })
                .catch(error => {
                    console.error('There was an error adding the product!', error);
                });
        }
    };

    const handleDelete = (id) => {
        axios.delete(`http://localhost:8080/products/${id}`)
            .then(() => {
                setProducts(products.filter(product => product.id !== id));
            })
            .catch(error => {
                console.error('There was an error deleting the product!', error);
            });
    };

    const [editProduct, setEditProduct] = useState(null);
    const handleEdit = (product) => {
        setEditProduct(product);
        setNewProducts(product);
    };

    return (
        <Container>
            <h1>Lista Produselor</h1>

            <form onSubmit={handleSubmit}>
                <TextField
                    label="Nume"
                    name="name"
                    value={newProducts.name}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Price"
                    name="price"
                    value={newProducts.price}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Description"
                    name="description"
                    value={newProducts.description}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Imagine"
                    name="imageUrl"
                    value={newProducts.imageUrl}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Category"
                    name="category"
                    value={newProducts.category}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Stock"
                    name="stock"
                    value={newProducts.stock}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                />
                <Button  sx={{ marginBottom: '30px' }} type="submit" variant="contained" color="primary">Adaugă Produs</Button>
            </form>

            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Nume</TableCell>
                            <TableCell>Pret</TableCell>
                            <TableCell>Descriere</TableCell>
                            <TableCell>Imagine</TableCell>
                            <TableCell>Categorie</TableCell>
                            <TableCell>Stoc</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {products.length > 0 ? (
                            products.map((product) => (
                                <TableRow key={product.id}>
                                    <TableCell>{product.id}</TableCell>
                                    <TableCell>{product.name}</TableCell>
                                    <TableCell>{product.price}</TableCell>
                                    <TableCell>{product.description}</TableCell>
                                    <TableCell>
                                        <img src={product.imageUrl} alt={product.name} width="50" />
                                    </TableCell>
                                    <TableCell>{product.category}</TableCell>
                                    <TableCell>{product.stock}</TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="secondary" onClick={() => handleDelete(product.id)}>

                                        Șterge
                                        </Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button variant="contained" color="primary" onClick={() => handleEdit(product)}>
                                            Editează
                                        </Button>

                                    </TableCell>

                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={7}>No products found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    );
}

export default ProductsList;
