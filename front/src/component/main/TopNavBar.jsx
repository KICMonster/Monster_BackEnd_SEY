import React, { useState } from 'react';
import { Navbar, Nav, Dropdown } from 'react-bootstrap';
import "bootstrap/dist/css/bootstrap.min.css";


function TopNavBar({ isShrunk }) {

  return (
    <Navbar className={isShrunk ? 'navbar shrink' : 'navbar expand'}>
      <Navbar.Brand href={'/'}>
        <div className="logo"></div> {/* 로고 이미지를 사용하는 div */}
      </Navbar.Brand>
      <Nav className="mr-auto">
        <Dropdown>
          <Dropdown.Toggle variant="transparent" >
            칵테일
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item href="/ViewPage">칵테일</Dropdown.Item>
            <Dropdown.Item href="/Ingredient">재료</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Dropdown>
          <Dropdown.Toggle variant="transparent" >
            정보
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item href="/history">역사</Dropdown.Item>
            <Dropdown.Item href="/craft/mensuration">튜토리얼</Dropdown.Item>
            <Dropdown.Item href="/trendNews">뉴스</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Dropdown>
          <Dropdown.Toggle variant="transparent" >
            컨텐츠
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item href="/weather">무작위 칵테일</Dropdown.Item>
            <Dropdown.Item href="/customcocktail">커스텀 칵테일</Dropdown.Item>
            <Dropdown.Item href="/taste">취향 조사</Dropdown.Item>
            <Dropdown.Item href="/mapsearch">칵테일 바 찾기</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Nav>
    </Navbar>

  );
}


export default TopNavBar;
