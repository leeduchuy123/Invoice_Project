import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/api"; // Proxy sẽ xử lý chuyển hướng đến http://localhost:8080/api

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

//Thêm interceptor để xử lý token nếu cần
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if(token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export const productService = {
    getAll: () => api.get('/products'),
    getById: (id) => api.get(`/products/${id}`),
    create: (data) => api.post('/products', data),
    update: (id, data) => api.put(`/products/${id}`, data),
    delete: (id) => api.delete(`/products/${id}`),
};

export const authService = {
    signIn: (data) => api.post('/login', data),
    signUp: (data) => api.post('/register', data),
};

export default api;