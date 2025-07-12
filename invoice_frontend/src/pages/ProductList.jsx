import React, { useState, useEffect } from 'react';
import { Button, ButtonGroup, Container, Table } from "reactstrap";
import ProductService from "../Services/ProductService.jsx";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate} from 'react-router-dom';
import AddProduct from "./AddProduct.jsx";


const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();


    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        ProductService.getAllProducts()
            .then(response => {
                setProducts(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                setIsLoading(false);
                if (error.code === 'ERR_NETWORK') {
                    // Hiển thị thông báo lỗi người dùng
                    alert('Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng hoặc thử lại sau.');
                } else {
                    alert('Đã có lỗi xảy ra: ' + error.message);
                }
            });
    };
    // Xử lý xóa sản phẩm
    const handleDelete = (productId) => {
        ProductService.deleteProduct(productId)
            .then(response => {
                console.log('Product deleted successfully: ', response.data);
                // Lọc ra danh sách sản phẩm mới, loại bỏ sản phẩm vừa xóa
                setProducts(products.filter(product => product.id !== productId));
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    // Xử lý chỉnh sửa sản phẩm
    const handleEdit = (productId) => {
        navigate(`/products/edit/${productId}`);
    };

    // Xử lý xem chi tiết sản phẩm
    const handleView = (productId) => {
        navigate(`/view-product/${productId}`);
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            {/*Header */}
            <Container className="mt4">
                <div className="d-flex justify-content-between align-items-center mb-4">
                    <h1>Product Management</h1>
                    <button className="btn btn-success"
                            onClick={() => navigate('/products/add')}>
                        Add New Product
                    </button>
                </div>
                <Table hover bordered responsive>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Unit</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {products.map((product) => (
                        <tr key={product.id}>
                            <td>{product.name}</td>
                            <td>{product.price}</td>
                            <td>{product.unit}</td>
                            <td>
                                <button className="btn btn-primary me-2" onClick={() => handleEdit(product.id)}>Edit</button>
                                <button className="btn btn-danger" onClick={() => handleDelete(product.id)}>Delete</button>
                                <button className="btn btn-info" onClick={() => handleView(product.id)}>View</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default ProductList;