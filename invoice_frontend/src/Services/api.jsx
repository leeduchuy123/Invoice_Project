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

// Response interceptor for better error handling
api.interceptors.response.use(
    (response) => response,
    (error) => {
        console.error('API error:', error.response?.data || error.message);
        if (error.response?.status === 401) {
            localStorage.removeItem('token');
            alert('Your session has expired. Please log in again.');

            // Optionally redirect to login page or clear token
            window.location.href = '/';
        } else if (error.response?.status === 403) {
            console.warn('Forbidden: You do not have the required permissions.');
            alert('Forbidden: You do not have the required permissions.')
        }
        return Promise.reject(error);
    }
);

export const productService = {
    getAll: () => api.get('/products'),
    getById: (id) => api.get(`/products/${id}`),
    create: (data) => api.post('/products', data),
    update: (id, data) => api.put(`/products/${id}`, data),
    delete: (id) => api.delete(`/products/${id}`),
};

export const authService = {
    signIn: async (data) => {
        const response = await api.post('/login', data);
        const token = response.data.accessToken; // Adjust based on backend respons
        if (token) {
            localStorage.setItem('token', token);
        } else {
            throw new Error('No access token received from server');
        }
        return response.data;
    },
    signUp: (data) => api.post('/register', data),
};

export const customerService = {
    create: (data) => api.post('/customers', data),
    update: (id, data) => api.put(`/customers/${id}`, data),
    delete: (id) => api.delete(`/customers/${id}`),
    getAll: () => api.get('/customers'),
    findByToken:(string) => api.get(`/customers/${string}`),
};

export default api;