import ReactDOM from 'react-dom/client';
import { HashRouter } from "react-router-dom";
import App from './App.jsx';
import { StrictMode } from 'react';

ReactDOM.createRoot(document.getElementById('root')).render(
    <StrictMode>
        <HashRouter>
            <App />
        </HashRouter>
    </StrictMode>
);

