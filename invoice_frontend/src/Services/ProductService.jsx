import axios from 'axios';
const Product_base_url = 'http://localhost:8080/api/products';
class ProductService {
    //Lấy danh sách tất cả sản phẩm
    getAllProducts() {
        return axios.get(Product_base_url);
    }

    //Thêm sản phẩm mới
    addProduct(product) {
        return axios.post(Product_base_url, product, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    // Lấy thông tin sản phẩm theo ID
    getProductById(productId) {
        return axios.get(`${Product_base_url}/${productId}`);
    }

    // Cập nhật thông tin sản phẩm
    updateProduct(productId, product) {
        return axios.put(`${Product_base_url}/${productId}`, product, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    // Xóa sản phẩm
    deleteProduct(productId) {
        return axios.delete(`${Product_base_url}/${productId}`);
    }
}
export default new ProductService();