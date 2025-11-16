//src Navbar.jsx
//Gabriel Caligiuri
import { Link } from 'react-router-dom';
import './navbar.css';

const items = [
  { label: 'Projects', to: '/projects', className: 'navbar__projects' },
  { label: 'Background', to: '/background', className: 'navbar__background'},
];

const logo = {label: <img src="/GCweblogo.png" alt="Gabe Caligiuri" className='navbar__home' />, href: '/'};


export default function Navbar() {
    return (        
        <header className="navbar">
            <nav className="navbar__container">
                <a href={logo.href} className={logo.className}>
                    {logo.label}
                </a>
                <div className='navbar__links'>
                    {items.map((item, index) => (
                        <Link 
                        key={index}
                        to={item.to}
                        className={item.className}>
                            {item.label}
                        </Link>
                    ))}
                </div>
            </nav>
        </header>
    )
};