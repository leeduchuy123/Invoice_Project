import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {jwtDecode} from "jwt-decode";

export function useAutoLogout() {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        if(!token) return;

        const { exp } = jwtDecode(token);
        const expiryTime = exp * 1000;
        const currentTime = Date.now();
        const timeUntilExpiry = expiryTime - currentTime;

        if(timeUntilExpiry <= 0) {
            //Token already expired
            localStorage.removeItem("token");
            navigate("/");
            return;
        }

        const timeout = setTimeout(() => {
            localStorage.removeItem("token");
            navigate("/");
        }, timeUntilExpiry);

        return () => clearTimeout(timeout); //cleanup on unmount
    }, []);
}