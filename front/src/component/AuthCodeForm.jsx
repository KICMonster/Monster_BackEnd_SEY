import React from 'react';

const AuthCodeForm = ({ authCode, setAuthCode, onVerified }) => {

    

    const handleAuthCodeChange = (e) => {
      setAuthCode(e.target.value);
    };
  
    return (
      <div>
        <input
          type="text"
          value={authCode}
          onChange={handleAuthCodeChange}
          required
        />
        <button onClick={onVerified}>인증 완료</button>
      </div>
    );
  };
        
export default AuthCodeForm;