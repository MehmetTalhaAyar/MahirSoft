export function CreateUserItem(props){
    const {id,text,onChange} = props;
    return (
        <>
            <div className="input-Item">
                <input id={id} type="text" onChange={onChange} />
                <label htmlFor={id}>{text}</label>
            </div>
        </>
    )
}