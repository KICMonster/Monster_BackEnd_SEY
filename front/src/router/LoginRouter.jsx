import React from 'react';
import Home from '../page/Home';




const LoginRouter = () => {
  return [{
    path : 'auth/kakao/callback',
    element : <Home />
  },
  {
    path : 'auth/google/callback',
    element : <Home />,
  },
  {
    path : 'auth/naver/callback',
    element : <Home />,
  }
];
};

export default LoginRouter;