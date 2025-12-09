import Header from "../components/Header.jsx";

const CreateOrderPage = () => {

    return (
        <>
            <Header />
            <div>
                <h2>Invoice Generator</h2>
                <div>
                    <form>
                        <input
                            type="text"
                            placeholder="The employee who create this invoice"
                        />
                        <input
                            type="text"
                            placeholder="The customer id"
                        />
                    </form>
                </div>
                <div>
                    <form>
                        <input
                            type="text"
                            placeholder="The id of the product"
                        />
                        <input
                            type="text"
                            placeholder="The name of the product - which was selected automatively"
                        />
                        <input
                            type="number"
                            placeholder="the quantity of the purchased product"
                        />
                        <div>
                            <button type="submit">Add item</button>
                        </div>
                    </form>
                </div>
            </div>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>Item name</th>
                        <th>Item quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                        //The code will be here
                    </tbody>
                </table>
                <div>
                    Grand Total:
                </div>
            </div>
        </>
    );
};

export default CreateOrderPage;