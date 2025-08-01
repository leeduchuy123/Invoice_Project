import React, { useState } from "react";
import axios from 'axios';
import {useNavigate, Link} from "react-router-dom";
import '../assets/styles/Login.css';

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();


    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            if(!username || !password) {
                setError('Please fill in all fields');
                return;
            }

            const response = await axios.post('http://localhost:8080/api/login', {
                username,
                password
            });

            console.log(response.data);
            navigate('/products')
        } catch(error) {
            console.error('Login failed:', error.response ? error.response.data : error.message);
            setError(error.response ? error.response.data : error.message);
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>Login Page</h2>

                {error && (
                    <p className="error-message">
                        {error.message || error.detail || 'Something went wrong'}
                    </p>
                )}

                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <button type="submit" className="login-button">
                        Sign In
                    </button>
                </form>

                <div className="signup-link">
                    <p>Not a member? <a href="/signup">Register</a></p>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;