// BasicLayout.js

import React, { useState, useEffect } from "react";
//import DynamicHeader from "../component/main/DynamicHeader";


function BasicLayout({ children }) {
  const [isScrolled, setIsScrolled] = useState(false);
  const [searchQuery, setSearchQuery] = useState(""); // 검색어 상태 추가

  // 스크롤 이벤트를 감지하여 헤더 상태 업데이트
  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      if (scrollTop > 0) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <>
      {/* 헤더 영역 */}
      <div className={isScrolled ? "header scrolled" : "header"}>
        {/* <DynamicHeader /> */}
       
        
      </div>

      {/* 정보 영역 */}
      <div className="info">
        {/* 메인 페이지 칵테일 재료 카테고리 */}
        <div className="cocktail-categories">
          {/* 칵테일 재료 목록 */}
          {/* 여기에 칵테일 재료 목록이 들어갈 예정입니다. */}
        </div>

        {/* 카테고리 아래에 있는 자식 컴포넌트 렌더링 */}
        {children}
      </div>

      {/* 푸터 영역 */}
      <footer className="footer">
        <a href="/customer-service">고객센터</a>
      </footer>
    </>
  );
}

export default BasicLayout;