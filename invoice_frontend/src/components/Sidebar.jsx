function Sidebar() {
    return (
        <aside className="h-screen bg-white border-r px-4 py-6">
            <div className="text-xl font-bold text-blue-500 mb-6">Arto<sup>+</sup></div>
            <nav>
                <div>Overview</div>
                <div className="font-semibold text-black">Products</div>
                <div>User</div>
                <div>Customer</div>
                <div>Order</div>
            </nav>
        </aside>
    );
}

export default Sidebar;