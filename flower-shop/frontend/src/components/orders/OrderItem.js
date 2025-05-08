import React, { useState, useEffect } from "react";
import { Card, CardContent, Typography, MenuItem, Select, Grid } from "@mui/material";

const STATUS_OPTIONS = [
    { value: "Procesata", label: "Procesata" },
    { value: "In livrare", label: "In livrare" },
    { value: "Finalizata", label: "Finalizata" },
    { value: "Anulata", label: "Anulata" },
];

export default function OrderItem({ order, onStatusChange }) {
    const [selectedStatus, setSelectedStatus] = useState("");

    useEffect(() => {
        const matched = STATUS_OPTIONS.find(
            (opt) => opt.label === order.status || opt.value === order.status
        );
        setSelectedStatus(matched ? matched.value : "");
    }, [order.status]);

    const handleChange = (event) => {
        const newStatus = event.target.value;
        setSelectedStatus(newStatus);
        onStatusChange(order.id, newStatus);
    };

    return (
        <Card sx={{ mb: 2 }}>
            <CardContent>
                <Typography variant="h6">
                    Comanda #{order.id} - {order.fullName}
                </Typography>
                {/*<Typography variant="body2">Email: {order.email}</Typography>*/}
                {/*<Typography variant="body2">Telefon: {order.phone}</Typography>*/}
                {/*<Typography variant="body2">*/}
                {/*    Adresă: {order.address}, {order.oras}, {order.judet}, {order.codPostal}*/}
                {/*</Typography>*/}

                <Typography variant="body2" sx={{ mt: 1 }}>
                    Status:{" "}
                    <Select value={selectedStatus} onChange={handleChange} size="small">
                        {STATUS_OPTIONS.map((option) => (
                            <MenuItem key={option.value} value={option.value}>
                                {option.label}
                            </MenuItem>
                        ))}
                    </Select>
                </Typography>

                <Grid container spacing={1} sx={{ mt: 2 }}>
                    {order.orderDetails.map((detail, index) => (
                        <Grid item xs={12} key={index}>
                            <Typography variant="body2">
                                {detail.productName} x {detail.quantity} – {detail.subtotal} lei
                            </Typography>
                        </Grid>
                    ))}
                </Grid>

                <Typography variant="subtitle1" sx={{ mt: 1 }}>
                    Total:{" "}
                    {order.orderDetails
                        .reduce((acc, curr) => acc + curr.subtotal, 0)
                        .toFixed(2)}{" "}
                    lei
                </Typography>
            </CardContent>
        </Card>
    );
}
