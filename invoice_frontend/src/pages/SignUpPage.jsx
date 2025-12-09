import React, { useEffect, useState} from 'react';
import { useNavigate, Link } from "react-router-dom";
import "../assets/styles/Signup.css";
import {authService} from "../Services/api.jsx";


function SignUpPage() {
    const[username, setUsername] = useState('');
    const[password, setPassword] = useState('');
    const[email, setEmail] = useState('');
    const[confirmPassword, setConfirmPassword] = useState('');
    const[error, setError] = useState('');
    const[loading, setLoading] = useState(false);

    useEffect(() => {
        setUsername("");
        setEmail("");
        setPassword("");
        setError("");
    }, []);

    const navigate = useNavigate();

    const handleSignup = async (e) => {
        e.preventDefault();

        if(loading)     return;

        if(!username || !email || !password || !confirmPassword) {
            setError('Please fill in all fields.');
            return;
        }

        if(password !== confirmPassword) {
            setError('Passwords do not match.');
            return;
        }

        try {
            const response = await authService.signUp({
                username,
                email,
                password
            })
            console.log(response.data);
            setLoading(false);
            navigate("/");
        } catch (error) {
            setLoading(false); // Ensure loading is reset on error
            // Check if the error response exists and has a message
            if (error.response && error.response.data && error.response.data.message) {
                setError(error.response.data.message); // Display backend error message (e.g., "Username already exists")
            } else {
                setError("An unexpected error occurred. Please try again.");
            }
            console.error('Signup failed:', error.response ? error.response.data : error.message);
        }
    };

    return (
        <div className="signup-container">
            <div>
                <h2>Sign Up Page</h2>

                {error &&  <p className="text-danger">
                    {error}
                </p>}

                <form onSubmit={handleSignup}>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">UserName</label>
                        <input
                            type="text"
                            id="username"
                            className="form-control"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email Address</label>
                        <input
                            type="email"
                            id="email"
                            className="form-control"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input
                            type="password"
                            id="password"
                            className="form-control"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="confirmPassword" className="form-label">Confirm Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            className="form-control"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                    </div>

                    <button type="submit" className="btn btn-primary w-100" disabled={loading}>
                        {loading ? "Signing Up..." : "Sign Up"}
                    </button>
                </form>

                <div className="text-center mt-3">
                    <p>Already Register? <Link to="/">Login</Link></p>
                </div>

            </div>
        </div>
    );
}

export default SignUpPage;