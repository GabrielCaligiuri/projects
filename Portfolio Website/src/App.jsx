// src/App.jsx
//Gabriel Caligiuri
import Navbar from './Navbar.jsx';
import Home from './home.jsx';
import Background from './Background.jsx';
import Projects from './Projects.jsx';
import {Routes, Route} from 'react-router-dom';
import MagnetLines from './MagnetLines';

import './App.css';


export default function App() {
    return (
        <>
        <Navbar />
        <MagnetLines
        rows={20}
        columns={20}
        containerSize="100vmax"
        lineColor="rgb(210, 225, 255)"
        lineWidth="0.8vmin"
        lineHeight="4vmin"
        baseAngle={0}
        style={{ position: 'fixed', inset: 0, zIndex: 0, pointerEvents: 'none'}}
        />
        <main>
            <Routes>
                <Route path="/" element={<Home/>} />
                <Route path="/background" element={<Background/>} />
                <Route path="/projects" element={<Projects/>} />
            </Routes>
        </main>
        </>
    )
}