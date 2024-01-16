import { useState } from "react";
import { CreateUserItem } from "../../../components/CreateUserItems"
import { createUser } from "./api";
import './company.css';
import { SignFormItem } from "../../../components/SignFormItem";


export function CompanyPage(props){

    const [name,setName] = useState();
    const [surname,setSurname] = useState();
    const [email,setEmail] = useState();
    const [password,setPassword] = useState();
    const [title,setTitle] = useState();
    const [gsm,setGsm] = useState();

    const { companyName } = props;

    const saveUser = () => {
        console.log("1111")

        createUser({
            name,
            surname,
            email,
            password,
            title,
            gsm
        })
    }

    // const [showForm, setShowForm] = useState(false);

    // const handleButtonClick = () => {
    //     setShowForm(true);
    // };


    return (    
        <main >
            
             <h1>Company</h1>
            <div className="container">
                <h1>Company Name</h1>
                <h2>Descriotion:</h2>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Praesentium odio at provident, distinctio quos iure inventore obcaecati atque, odit ea facere cumque. Sit hic cumque doloremque, nihil quod adipisci suscipit!
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Illum vitae, dolorem eum suscipit neque, vel nihil sunt obcaecati saepe qui fugit repellat nesciunt minus aliquam. Error autem asperiores fugit facere!
                </p>

                <div>
                    <h2>Social Media</h2>
                    <ul>
                    <li>
                        <a href="https://twitter.com/company" target="_blank" rel="noopener noreferrer" className="tw">
                        Twitter
                        </a>
                    </li>
                    <li>
                        <a href="https://www.facebook.com/company" target="_blank" rel="noopener noreferrer" className="tw">
                        Facebook
                        </a>
                    </li>
                    {/* Add more social media links as needed */}
                    </ul>
                </div>
            {/* </div> */}
            {/* <div className="small-container-2"> */}
<div className="company-form">
                <form className="user-form">
                
                <h2>Register Form</h2>

                <label htmlFor="surname">Surname:</label>
                <input type="text" id="surname" name="surname" required />

                <label htmlFor="gsm">GSM:</label>
                <input type="tel" id="gsm" name="gsm" required />

                <label htmlFor="email">Email:</label>
                <input type="email" id="email" name="email" required />
                
                <label htmlFor="password">Password:</label>
                <input type="password" id="password" name="password" required />

                <label htmlFor="title">Title:</label>
                <input type="text" id="title" name="title" required />

                <button type="submit">Submit</button>
                </form>
                </div>
            {/* </div> */}
            </div>
        </main>
    )
}