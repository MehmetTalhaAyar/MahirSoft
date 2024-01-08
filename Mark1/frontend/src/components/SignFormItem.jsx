

export function SignFormItem(props){

    const { error,name,onChange,inputId,errorType,errorMessage,type } = props;

    return (
        <>
            <div className="input-box">
              <span className="icon"></span>
              <input type={type} id={inputId} required className={`${error ? errorType : "" }`}  onChange={onChange}/>
              <label htmlFor={inputId}>{name}</label>
              {error && <span className={errorType}> {errorMessage} </span> }
            </div>
        </>
    )
}