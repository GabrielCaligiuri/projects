//src Home.jsx
//Gabriel Caligiuri
import './background.css';

export default function Background() {
    return (
        <>
        <div className='section_block'>
            <h2 className='education__title'> Education </h2>
            <ul className='list__education'>
                <p className='coursework'> Relevant coursework: Data structures, Computer Architecture and Assembly, Python Programming, Programming Fundementals I and II.</p>
                <li>
                    <h3 className='cc__subcategory'> Palomar Community College: </h3>
                    <p className='cc__information'>
                        Bachelor of Science, Computer Science, Sep 2022 - May 2025
                    </p>
                </li>
                <li>
                    <h3 className='asu__subcategory'> Arizona State University</h3>
                    <p className='asu__information'>
                        Bachelor of Science, Computer Science, Sep 2025 - Present
                    </p>
                </li>
            </ul>
        </div>
        <div className='section_block'>
            <h2 className='skills'> Skills </h2>
            <ul className='list__skills'>
                <li>
                    <h3 className='languages'> Programming Languages </h3>
                    <p className='languages__info'>
                        Python, Java, C, Assembly, SQL, Bash Scripting
                    </p>
                </li>
                <li>
                    <h3 className='web'> Web Development </h3>
                    <p className='web__info'>
                        HTML, CSS, JavaScript, TypeScript, Git, Github, React
                    </p>
                </li>
                <li>
                    <h3 className='core'> Core Competencies </h3>
                    <p className='core__info'>
                        Fast Learner, Technical Literacy, Problem Solving, Attention to Detail
                    </p>
                </li>
            </ul>
        </div>
        <div className='section_block'>
            <ul className='list__work'>
                <li>
                    <h3 className='jojos'> Jojos Creamery </h3>
                    <p className='jojos__info'>
                        Managed store operations, including inventory tracking and cash handling, overseeing a team of 4 employees
                        in a collaborative work environment.
                    </p>
                </li>
            </ul>
        </div>
        </>
    )
};






