import React, { useState, useEffect, useRef } from "react";
import DynamicHeader from "../component/main/DynamicHeader";
import '../component/main/styles/canvas.scss';

function BasicLayout({ children }) {
  const [isScrolled, setIsScrolled] = useState(false);
  const [searchQuery, setSearchQuery] = useState(""); // 검색어 상태 추가

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

  // 150개의 원을 생성 (배경임)
  const circles = Array.from({ length: 150 }, (_, index) => (
    <div key={index} className="circle"></div>
  ));

  const canvasRef = useRef(null);

  useEffect(() => {
    const draw = () => {
      const canvas = canvasRef.current;
      const context = canvas.getContext('2d');
      const stars = 500;
      const colorrange = [0, 60, 240];

      context.canvas.width = window.innerWidth + 300;
      context.canvas.height = window.innerHeight + 300;

      function getRandom(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
      }

      for (let i = 0; i < stars; i++) {
        const x = Math.random() * canvas.offsetWidth;
        const y = Math.random() * canvas.offsetHeight;
        const radius = Math.random() * 1.5;
        const hue = colorrange[getRandom(0, colorrange.length - 1)];
        const sat = getRandom(50, 100);
        context.beginPath();
        context.arc(x, y, radius, 0, 360);
        context.fillStyle = `hsl(${hue}, ${sat}%, 88%)`;
        context.fill();
      }
    };

    draw();
    window.addEventListener('resize', draw);

    return () => {
      window.removeEventListener('resize', draw);
    };
  }, []);

  return (
    <>
      {/* 헤더 영역 */}
      <div className="header">
        <DynamicHeader />
      </div>
      <div className="info">
        {children}
        <div className="canvasBody">
          <div className="wrapper">
            <div className="colorizer1"></div>
            <div className="colorizer2"></div>
            <div className="colorizer3"></div>
            <div className="colorizer4"></div>
            <div className="circles">
              {circles}
              {/* 150개의 원이 여기에 추가됨 */}
            </div>
          </div>
        </div>
      </div>
      <canvas ref={canvasRef} id="canvas"></canvas>

      <footer className="footer">
        <a href="/#">고객센터</a>
      </footer>
    </>
  );
}

export default BasicLayout;
