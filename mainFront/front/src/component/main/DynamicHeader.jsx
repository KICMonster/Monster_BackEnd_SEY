import React, { useState, useEffect } from 'react';
import TopNavBar from './TopNavBar';
import SearchBar from './SearchBar';
import LoginButton from './LoginButton';

function  DynamicHeader() {
  const [searchTerm, setSearchTerm] = useState('');
  const [isShrunk, setShrunk] = useState(false);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  useEffect(() => {
    const handleScroll = () => {
      setShrunk(window.scrollY > 100);
    };

    // window.addEventListener('scroll', handleScroll);
    // return () => {
    //   window.removeEventListener('scroll', handleScroll);
    // };
    //  동준형 이기능 쓸건지 회의때 얘기해야함 빼자는얘기가 계속나옴

  }, []);

  return (
    <header className='header'>
      <div className="header-content">
        <TopNavBar isShrunk={isShrunk} /> 
        <div className="right-section">
        <SearchBar />
        <LoginButton />
        </div>
      </div>
    </header>
  );
}

export default DynamicHeader;
