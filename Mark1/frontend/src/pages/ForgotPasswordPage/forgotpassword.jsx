import { useNavigate } from 'react-router-dom'
import '../ForgotPasswordPage/forgotpassword.css'
import { useAuthState } from '../../state/context'

export function ForgotPassword() {
    const navigate = useNavigate()
    const authState = useAuthState();

    if(authState.userId > 0){
        navigate("/home")
    }

    return (
        <div className="password_page">
            <div className='container'>
                <h1 className='title-field'>
                    Forgot Password
                </h1>

                <div className='paragraf-field'>
                    <p>
                        Reset your password
                    </p>
                </div>

                <div className='email-field'>
                    <input id="email" required className='email-field-value' />
                    <label htmlFor='email'>Email</label>
                </div>

                <div className='continue-buttton-field'>
                    <button className='continue-buttton-field-value'>
                        Continue
                    </button>
                </div>
            </div>
        </div>
    )
}

