import React, { useState } from 'react';
import {useNavigate} from 'react-router-dom';
import ProductService from "../Services/ProductService.jsx";

const AddProduct = () => {
    const navigate = useNavigate();
    const [product, setProduct] = useState({
        name: '',
        price: '',
        unit: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault(); // Ngăn chặn form submit mặc định
        try {
            const response = await ProductService.addProduct(product);
            console.log('Product added successfully: ', response.data);
            navigate('/products'); // Chuyển hướng về trang products sau khi thêm thành công
        } catch (error) {
            if (error.code === 'ERR_NETWORK') {
                alert('Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng hoặc thử lại sau.');
            } else {
                alert('Đã có lỗi xảy ra: ' + error.message);
            }
        }
    }

    return (
        <div className="container mt-4">
            <h2>Add New Product</h2>
            <form onSubmit = {handleSubmit}>
                <div className={"mb-3"}>
                    <label htmlFor="name" className="form-label">Product Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">Price</label>
                    <input
                        type="number"
                        className="form-control"
                        id="price"
                        name="price"
                        value={product.price}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="description" className="form-label">unit</label>
                    <textarea
                        className="form-control"
                        id="unit"
                        name="unit"
                        value={product.unit}
                        onChange={handleChange}
                        rows="3"
                    />
                </div>
                <button
                    type="submit"
                    className={"btn btn-primary"}
                >Save Product</button>
                <button
                    type="button"
                    className={"btn btn-secondary ms-2"}
                    onClick={() => navigate('/products')}
                >Cancel</button>
            </form>
        </div>
    );
};

export default AddProduct;