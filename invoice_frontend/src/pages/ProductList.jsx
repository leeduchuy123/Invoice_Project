import React, { useState, useEffect } from 'react';
import { productService } from '../Services/api.jsx';
import "../assets/styles/productList.css";
import Header from "../components/Header.jsx";

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await productService.getAll();
                console.log("Data from API:", response.data);
                setProducts(response.data);
                setLoading(false);
            } catch (error) {
                setError('Không thể tải danh sách sản phẩm');
                setLoading(false);
                console.error(error);
            }
        };
        fetchProducts();
    }, []);

    //handle delete product
    const handleDelete = async(id) => {
        try {
            await productService.delete(id);
            setProducts((prevProducts) => prevProducts.filter((p) => p.id !== id));
        } catch(err) {
            setError('Không thể xóa sản phẩm');
            console.error(err);
        }
    }

    //handle add product
    const [showForm, setShowForm] = useState(false);
    const [newProduct, setNewProduct] = useState({ name: '', price: '', unit: '' });
    const handleAddProduct = async(e) => {
        e.preventDefault();
        try {
            const response = await productService.create(newProduct);
            setProducts(prev => [...prev, response.data]);      //Update product's list
            setShowForm(false);
            setNewProduct({name: '', price: '', unit: ''});
        } catch(err) {
            setError('Không thể thêm sản phẩm');
            console.error(err);
        }
    }

    if (loading) return <div className="text-center py-4">Loading...</div>
    if (error)   return <div className="text-red-500 text-center py-4">{error}</div>


    return (
        <>
            <Header />
            <div className="page-container">
                <div className="title-bar">
                    <h2 className="page-title">Product's List</h2>
                    <button className="add-button" onClick={() => setShowForm(true)}>
                        Add Product
                    </button>
                </div>
                <div>
                    {
                        products.length === 0 ? (
                            <p className="no-product-text">There is no product exits</p>
                        ) : (
                            <div className="table-wrapper">
                                <table className="product-table">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Product's name</th>
                                        <th>Price (vnđ)</th>
                                        <th>Unit</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {products.map((product) => (
                                        <tr key={product.id}>
                                            <td>{product.id}</td>
                                            <td>{product.name}</td>
                                            <td>{product.price}</td>
                                            <td>{product.unit}</td>
                                            <td>
                                                <button className="delete-button" onClick={() => handleDelete(product.id)}>Delete</button>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        )
                    }
                </div>
            </div>
            {/* Modal Form */}
            {showForm && (
                <div className="modal-overlay">
                    <div className="modal-form">
                        <h3> Add New Product</h3>
                        <form onSubmit={handleAddProduct}>
                            <input
                                type="text"
                                placeholder="Name of the product"
                                value={newProduct.name}
                                onChange={(e) => setNewProduct({ ...newProduct, name:e.target.value})}
                                required
                            />
                            <input
                                type="number"
                                placeholder="Price of the product"
                                value={newProduct.price}
                                onChange={(e) => setNewProduct({ ...newProduct, price:e.target.value})}
                                required
                            />
                            <input
                                type="text"
                                placeholder="Unit"
                                value={newProduct.unit}
                                onChange={(e) => setNewProduct({ ...newProduct, unit: e.target.value })}
                                required
                            />
                            <div className="form-buttons">
                                <button type="submit">Submit</button>
                                <button type="button" onClick={() => setShowForm(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </>
    );
};

export default ProductList;