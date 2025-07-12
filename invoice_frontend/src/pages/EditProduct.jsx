import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import ProductService from "../Services/ProductService.jsx";

const EditProduct = () => {
    const navigate = useNavigate();
    const { id } = useParams();  //Lay id tu URL
    const [product, setProduct] = useState({
        name: '',
        price: '',
        unit: ''
        }
    );

    useEffect(() => {
        loadProduct();
    }, [id]);

    const loadProduct = async () => {
        try {
            const response = await ProductService.getProductById(id);
            setProduct(response.data);
        } catch (error) {
            // if (error.code === 'ERR_NETWORK') {
            //     alert('Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng hoặc thử lại sau.');
            // } else {
            //     alert('Đã có lỗi xảy ra loadProduct: ' + error.message);
            // }
            // navigate('/products');
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await ProductService.updateProduct(id, product);
            console.log('Product updated successfully: ', response.data);
            navigate('/products'); // Chuyển về trang danh sách sau khi cập nhật thành công
        } catch (error) {
            if (error.code === 'ERR_NETWORK') {
                alert('Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng hoặc thử lại sau.');
            } else {
                alert('Đã có lỗi xảy ra: ' + error.message);
            }
        }
    };

    return (
        <div className="container mt-4">
            <h2>Edit Product</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
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
                    <label htmlFor="unit" className="form-label">Unit</label>
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
                    className="btn btn-primary"
                >Save Changes</button>
                <button
                    type="button"
                    className="btn btn-secondary ms-2"
                    onClick={() => navigate('/products')}
                >Cancel</button>
            </form>
        </div>
    );
};

export default EditProduct;

