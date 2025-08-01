import "../assets/styles/header.css"
function Header() {
    return (
        <header>
            <div class="logo">Invoice Simple</div>
            <nav class="nav-link">
                <a href="#">Invoices</a>
                <a href="#">Estimates</a>
                <a href="#">Expense</a>
                <a href="#">Reports</a>
            </nav>
            <div class="nav-auth">
                <a href="#">Login</a>
                <a href="#">Sign up</a>
            </div>
        </header>
    );
}

export default Header;