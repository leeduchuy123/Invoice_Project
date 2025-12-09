import React, {useState, useEffect} from "react";
import { customerService } from "../Services/api.jsx";
import "../assets/styles/customerList.css";
import Header from "../components/Header.jsx";
import CustomerSearch from "./CustomerSearch.jsx";

const CustomerList = () => {
    const[customers, setCustomers] = useState([]);
    const[loading, setLoading] = useState(true);
    const[error, setError] = useState(null);

    useEffect( () => {
        const fetchCustomers = async () => {
            try {
                const response = await customerService.getAll();
                console.log("Data from API:", response.data);
                setCustomers(response.data);
                setLoading(false);

            } catch (error) {
                setError('Không thể tải danh sách khách hàng');
                setLoading(false);
                console.error(error);
            }
        };
        fetchCustomers();
    }, []);

    //handle delete customer
    const handleDelete = async (id) => {
        try {
            await customerService.delete(id);
            setCustomers((prevCustomers) =>prevCustomers.filter((c) => c.id !== id));
        } catch (error) {
            setError('Cannot delete customer');
                console.error(error);
        }
    }

    //handle add customer
    const [showForm, setShowForm] = useState(false);
    const [newCustomer, setNewCustomer] = useState({ fullname:'', address:'', phoneNumber:'', email:''});
    const handleAddCustomer = async(e) => {
        e.preventDefault();
        try {
            const response = await customerService.create(newCustomer);
            setCustomers(prev => [...prev, response.data]);
            setShowForm(false);
            setNewCustomer({fullname:'', address:'', phoneNumber:'', email:''});
        } catch(err) {
            setError('Cannot create customer');
            console.error(err);
        }
    }

    if (loading) return <div className="text-center py-4">Loading...</div>
    if (error) return <div className="text-red-500 text-center py-4">{error}</div>

    return (
        <>
            <Header />
            <div className="page-container">
                <div className="title-bar">
                    <h2 className="page-title">Customer's List</h2>
                    <div>
                        <CustomerSearch onSearchResult={(result) => setCustomers(result)} />
                        <button className="add-button" onClick={() => setShowForm(true)}>Add Customer</button>
                    </div>
                </div>

                {customers.length === 0 ? (
                    <p>There is no customer</p>
                ) : (
                    <table className="customer-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Fullname</th>
                            <th>Address</th>
                            <th>Phone Number</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {customers.map(customer => (
                            <tr key={customer.id}>
                                <td>{customer.id}</td>
                                <td>{customer.fullname}</td>
                                <td>{customer.address}</td>
                                <td>{customer.phoneNumber}</td>
                                <td>{customer.email}</td>
                                <td>
                                    <button
                                        className="delete-button"
                                        onClick={() => handleDelete(customer.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>

            {showForm && (
                <div className="modal-overlay">
                    <div className="modal-form">
                        <h3>Add New Customer</h3>
                        <form onSubmit={handleAddCustomer}>
                            <input
                                type="text"
                                placeholder="Fullname of customer"
                                value={newCustomer.fullname}
                                onChange={(e) => setNewCustomer({ ...newCustomer, fullname: e.target.value })}
                                required
                            />
                            <input
                                type="text"
                                placeholder="Address of customer"
                                value={newCustomer.address}
                                onChange={(e) => setNewCustomer({ ...newCustomer, address: e.target.value })}
                                required
                            />
                            <input
                                type="text"
                                placeholder="Phone number"
                                value={newCustomer.phoneNumber}
                                onChange={(e) => setNewCustomer({ ...newCustomer, phoneNumber: e.target.value })}
                                required
                            />
                            <input
                                type="text"
                                placeholder="Email"
                                value={newCustomer.email}
                                onChange={(e) => setNewCustomer({ ...newCustomer, email: e.target.value })}
                                required
                            />
                            <div className="form-buttons">
                                <button type="submit">Submit</button>
                                <button type="button" onClick={() => setShowForm(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </>
    );
};

export default CustomerList;