import { useState } from "react";
import { CreateUserItem } from "../../../components/CreateUserItems"
import { createUser } from "./api";


export function CompanyPage(props){

    const [name,setName] = useState();
    const [surname,setSurname] = useState();
    const [email,setEmail] = useState();
    const [password,setPassword] = useState();
    const [title,setTitle] = useState();
    const [gsm,setGsm] = useState();

    const { companyName } = props;

    const saveUser = () => {

        createUser({
            name,
            surname,
            email,
            password,
            title,
            gsm
        })
        

    }


    return (
        <main>
        <h1>{companyName}</h1>

        {/* <span>Create User</span>

        <div className="create-user-inputs">
        
        <CreateUserItem id="name" text="Name" onChange={(event)=>setName(event.target.value)} />
        <CreateUserItem id="surname" text="Surname" onChange={(event)=>setSurname(event.target.value)} />
        <CreateUserItem id="gsm" text="Gsm" onChange={(event)=>setGsm(event.target.value)} />
        <CreateUserItem id="email" text="Email" onChange={(event)=>setEmail(event.target.value)} />
        <CreateUserItem id="password" text="Password" onChange={(event)=>setPassword(event.target.value)} />
        <CreateUserItem id="title" text="Title" onChange={(event)=>setTitle(event.target.value)} />

        <button onClick={saveUser}>Save User</button>
        </div> */}


        
        
        </main>
    )
}