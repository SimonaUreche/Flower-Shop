import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import UsersList from './components/UsersList';
import ProductsList from './components/ProductsList';

function App() {
    return (
        <Router>
            <div className="App">
                <nav>
                    <ul>
                        <li><Link to="/users">Utilizatori</Link></li>
                        <li><Link to="/products">Produse</Link></li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/users" element={<UsersList />} />
                    <Route path="/products" element={<ProductsList />} />
                    <Route path="/" element={<h1>Bine ai venit! Alege o pagină.</h1>} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
