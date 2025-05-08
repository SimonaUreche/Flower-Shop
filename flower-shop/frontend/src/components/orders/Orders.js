import React, { useEffect, useState } from "react";
import axiosInstance from "../../helper/axios";
import OrderItem from "./OrderItem";
import { Container, Typography, CircularProgress, Snackbar, Alert } from "@mui/material";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export default function Orders() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [snackbar, setSnackbar] = useState({ open: false, message: "", severity: "success" });

    const fetchOrders = async () => {
        try {
            const res = await axiosInstance.get("/orders");
            setOrders(res.data);

        } catch (error) {
            console.error("Eroare la încărcarea comenzilor:", error);
        } finally {
            setLoading(false);
        }
    };

    const updateOrderStatus = async (id, status) => {
        try {
            await axiosInstance.patch(`/orders/${id}/status?status=${status}`);
            setOrders((prev) =>
                prev.map((order) => (order.id === id ? { ...order, status } : order))
            );
        } catch (error) {
            console.error("Eroare la actualizarea statusului:", error);
        }
    };

    const handleCloseSnackbar = () => {
        setSnackbar({ ...snackbar, open: false });
    };

    useEffect(() => {
        fetchOrders();
        //conexiunea WebSocket la backend
        const socket = new SockJS("http://localhost:8080/socket");
        const stompClient = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                stompClient.subscribe("/topic/order-status", (message) => {
                    const notification = JSON.parse(message.body);
                    setOrders((prev) =>
                        prev.map((order) =>
                            order.id === notification.orderId
                                ? { ...order, status: notification.status }
                                : order
                        )
                    );
                    setSnackbar({
                        open: true,
                        message: `Comanda #${notification.orderId} a fost actualizată la statusul: ${notification.status}`,
                        severity: "info",
                    });
                });
            },
            debug: (str) => console.log(str),
        });

        stompClient.activate();

        return () => {
            stompClient.deactivate();
        };
    }, []);

    return (
        <Container sx={{ mt: 4 }}>
            <Typography variant="h4" gutterBottom>
                Administrare Comenzi
            </Typography>

            {loading ? (
                <CircularProgress />
            ) : (
                orders.map((order) => (
                    <OrderItem key={order.id} order={order} onStatusChange={updateOrderStatus} />
                ))
            )}

            <Snackbar
                open={snackbar.open}
                autoHideDuration={3000}
                onClose={handleCloseSnackbar}
                anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
            >
                <Alert severity={snackbar.severity} onClose={handleCloseSnackbar} sx={{ width: "100%" }}>
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </Container>
    );
}
