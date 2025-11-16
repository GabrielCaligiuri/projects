//src Home.jsx
//Gabriel Caligiuri
import './home.css'
import { useState } from 'react'

import ProfileCard from './ProfileCard'

const links = [
  { label: 'LinkedIn', href: 'https://www.linkedin.com/in/gabe-caligiuri-32901a20a/', className: "home__github"},
  { label: 'GitHub', href: 'https://github.com/GabrielCaligiuri', className: "home__linked"},
];

export default function home() {
    const [showSplash, setShowSplash] = useState(true);

    return (
        <>
        <section className="hero-section">
            <ProfileCard
                name="Gabe Caligiuri"
                title="Computer Science Student"
                handle='Gabe C'
                avatarUrl="/pfp.png"
                contactText='Contact Me'
                showUserInfo={true}
                enableTilt={true}
                enableMobileTilt={false}
                onContactClick={() => window.open("https://mail.google.com/mail/?view=cm&fs=1&to=gabecaligiuri@gmail.com", "_blank")}
            />
            <div className="home__links">
                {links.map((item, index) => (
                    <a
                    key={index}
                    href = {item.href}
                    className={item.className}
                    >
                        {item.label}
                    </a>
                ))}
            </div>
        </section>
        </>
        )
}