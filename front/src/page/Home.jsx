import React, { useEffect, useState } from 'react';

function Home() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('jwt');
    const loginLink = document.querySelector('a[href="/login"]');
    const joinLink = document.querySelector('a[href="/join"]');
    
    if (token) {
      setIsLoggedIn(true);
      if (loginLink) {
        // JWT 토큰이 있으면 로그아웃 링크로 변경합니다.
        loginLink.textContent = 'Logout';
        loginLink.setAttribute('href', '/logout');
        loginLink.addEventListener('click', (e) => {
          e.preventDefault();
          // 로컬 스토리지에서 JWT 토큰을 제거합니다.
          localStorage.removeItem('jwt');
          // 상태를 업데이트합니다.
          setIsLoggedIn(false);
          // 로그인 페이지로 리다이렉트합니다.
          window.location.href = '/login';
        });
      }
      // 조인 링크를 숨깁니다.
      if (joinLink) {
        joinLink.style.display = 'none';
      }
    } else {
      // 로그인하지 않은 상태에서 로그인 링크와 조인 링크를 보여줍니다.
      if (loginLink && joinLink) {
        loginLink.textContent = 'Login';
        loginLink.setAttribute('href', '/login');
        joinLink.style.display = '';
      }
    }
  }, [isLoggedIn]);
    
      return (
        <div>
          <h1>Welcome to our website!</h1>
          <p>
            Please <a href="/login">login</a><a href="/join">join</a> us!
          </p>
              {/* 기능테스트를 위해 만든 경로이므로 참고. 실제페이지 적용X */}
            <p><a href="/trendNews">트렌드 뉴스</a></p> 
          <p><a href="/taste">취향 분석 질문</a></p>
        </div>
      );
    }
export default Home;