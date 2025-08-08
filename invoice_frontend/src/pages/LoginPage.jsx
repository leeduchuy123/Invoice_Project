import React, { useState } from "react";
import {useNavigate, Link} from "react-router-dom";
import '../assets/styles/Login.css';
import {authService} from "../Services/api.jsx";

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();


    const handleLogin = async (e) => {
        e.preventDefault();

        //Validate inputs:
        if (!username || !password) {
            setError('Please fill in all fields');
            return;
        }

        try {
            //Use AuthService.signIn to handle
            const response = await authService.signIn({username, password});
            console.log('Login response:', response);

            //Verify token exits before navigating
            if(response.accessToken) {
                navigate('/products');
            } else {
                setError('Login successful, but no token received. Please try again.');
            }
        } catch (error) {
            console.error('Login failed:', error.response?.data || error.message);
            // Handle specific error cases
            const errorMessage =
                error.response?.status === 401
                    ? 'Invalid username or password'
                    : error.response?.data?.message ||
                    error.response?.data?.detail ||
                    'Something went wrong';
            setError(errorMessage);
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