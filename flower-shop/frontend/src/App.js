import './App.css';
import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router-dom";
import Users from './components/users/Users';
import Login from './Login';
import Products from './components/products/Product';
import CheckoutWrapper from "./components/checkout/Checkout";
import CartWrapper from "./components/cart/Cart";
import OrderConfirmation from "./components/checkout/OrderConfirmation";
import Order from "./components/orders/Orders";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" replace />} />
                <Route path="/login" element={<Login />} />
                <Route path="/users" element={<Users />} />
                <Route path="/products" element={<Products />} />
                <Route path="/cart" element={<CartWrapper />} />
                <Route path="/checkout" element={<CheckoutWrapper />} />
                <Route path="/order-confirmation" element={<OrderConfirmation />} />
                <Route path="/orders" element={<Order />} />
            </Routes>
        </Router>
    )
}

export default App;