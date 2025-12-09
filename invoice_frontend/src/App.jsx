import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProductList from './pages/ProductList.jsx';
import EditProduct from './pages/EditProduct.jsx';
import SignUpPage from "./pages/SignUpPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import CustomerList from "./pages/CustomerList.jsx";
import CreateOrderPage from "./pages/CreateOrderPage.jsx";

const App = () => {
    return (
        // route của các trang
        <Router>
            <Routes>
            <Route path="/" element={<LoginPage/>} />
            <Route path="/signup" element={ <SignUpPage/>} />
                <Route path="/products" element={<ProductList />} />
                <Route path="/products/edit/:id" element={<EditProduct />} />
                <Route path="/customers" element={<CustomerList />} />
                <Route path="/orders/create" element={<CreateOrderPage />} />
            </Routes>
        </Router>
    );
};

export default App;