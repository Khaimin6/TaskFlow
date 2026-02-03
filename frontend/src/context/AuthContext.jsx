import { createContext, useState, useEffect, useContext } from 'react';
import api from '../api/axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('token');
        const username = localStorage.getItem('username');
        if (token && username) {
            setUser({ username });
        }
        setLoading(false);
    }, []);

    const login = async (username, password) => {
        try {
            const response = await api.post('/auth/login', { username, password });
            const { token, username: userField } = response.data;
            localStorage.setItem('token', token);
            localStorage.setItem('username', userField);
            setUser({ username: userField });
            return true;
        } catch (error) {
            console.error('Login failed', error);
            return false;
        }
    };

    const register = async (username, email, password) => {
        try {
            await api.post('/auth/register', { username, email, password });
            return true;
        } catch (error) {
            console.error('Registration failed', error);
            return false;
        }
    }

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, logout, register, loading }}>
            {!loading && children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
