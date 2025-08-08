import "../assets/styles/header.css"
import { Link } from "react-router-dom";

function Header() {
    return (
        <header>
            <div className="logo">Invoice Simple</div>
            <nav className="nav-link">
                <Link to="/">Invoices</Link>
                <Link to="/estimate">Estimate</Link>
                <Link to="/customers">Customer</Link>
                <Link to="/products">Product</Link>
            </nav>
            <div className="nav-auth">
                <Link to="/">Login</Link>
                <Link to="/signup">Sign up</Link>
            </div>
        </header>
    );
}

export default Header;