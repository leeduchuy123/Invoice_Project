import { useState } from "react";
import { customerService } from "../Services/api.jsx";
import "../assets/styles/customerList.css";

const CustomerSearch = ({ onSearchResult }) => {
    const [query, setQuery] = useState("");

    const handleSearch = async (e) => {
        e.preventDefault();

        try {
            const response = await customerService.findByToken(query);
            onSearchResult(response.data);
        } catch {
            const errorMessage = "Không tìm thấy khách hàng với token này";
            alert(errorMessage);
            console.error(errorMessage);
        }
    };

    return (
        <form className="search-form" onSubmit={handleSearch}>
            <input
                type="text"
                placeholder="Fullname / Phone / Email"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                className="search-input"
            />
            <button type="submit" className="search-button">🔍</button>
        </form>
    );
};

export default CustomerSearch;