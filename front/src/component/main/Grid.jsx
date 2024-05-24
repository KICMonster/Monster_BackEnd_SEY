import React, { useEffect, useState } from 'react';
import './styles/Grid.css'; // 추가된 CSS 파일을 import 합니다.
import Mo from "../../img/mo.jpg";
import Ma from "../../img/ma.jpg";
import Sang from "../../img/sang.jpg";
import RecommendedList from './RecommendedList';
import SideMenuMainPage from './SideMenuMainPage';
import { Link } from 'react-router-dom';

// 임의의 칵테일 이름과 설명
const cocktail = [
  { name: '모히또', description: '라임과 민트 향이 물씬 나는 상쾌한 칵테일' },
  { name: '마티니', description: '진과 버몬트, 오렌지 비터의 감각적인 조합' },
  { name: '카페리누이', description: '카푸치노와 맥주의 완벽한 만남' },
  // 추가적인 칵테일들을 필요에 따라 여기에 추가할 수 있습니다.
];

function Grid() {
  const [cocktails, setCocktails] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);

  const fetchCocktails = () => {
    const dummyData = [
      { id: 1, name: "마티니", ingredients: ["진", "마티니 버무스", "올리브"], image: Ma },
      { id: 2, name: "모히또", ingredients: ["럼", "라임 주스", "민트", "설탕", "탄산수"], image: Mo },
      { id: 3, name: "상그리아", ingredients: ["재료1", "재료2", "재료3"], image: Sang },
    ];

    setCocktails(dummyData);
  };

  useEffect(() => {
    fetchCocktails();
  }, []);

  const handleRandomClick = () => {
    const randomIndex = Math.floor(Math.random() * cocktails.length);
    setCurrentIndex(randomIndex);
  };


  return (
    <div className="container">
      <div className="image-container">
        <div className="image-text">
          <h1>오늘의 칵테일</h1>
        </div>
        {cocktails.length > 0 && (
          <div>
            <img src={cocktails[currentIndex].image} alt="Random Cocktail" />
          </div>
        )}
      </div>


      <div className="description" style={{marginBottom:'0px', paddingBottom: '50px'}}>
        <div>
          <h1>{cocktail[Math.floor(Math.random() * cocktails.length)].name}</h1>
          <p>{cocktail[Math.floor(Math.random() * cocktails.length)].description}</p>
        </div>
        <p className='recipe'>
          칵테일은 술과 여러 종류의 음료, 첨가물 등을 섞어 만든 혼합주를 일컫는다.
          다만, 무알콜 칵테일도 있으며 이들은 목테일(Mocktail, Mock과 Cocktail의 합성어)
          이라고 부른다. 사람의 기호와 취향에 맞추어 독특한 맛과 빛깔을 낼 수 있다.
          명칭의 유래에 대해서는 여러 가지 설이 있지만, 1795년쯤 미국 루이지애나주.
        </p>
      </div>

      <div className="operation" style={{marginTop:'0px'}}>
        <h1>사이드</h1>
        <div className="side-menu">
          <SideMenuMainPage />
        </div>
        <RecommendedList />
      </div>

      <div className="content" style={{marginTop:'0px'}}>
        <h1>컨텐츠</h1>
        <div className="button-container">
          <Link to={"/mapsearch"}><button className="random-button">GIS</button></Link>
          <button onClick={handleRandomClick} className="random-button">
            다른칵테일
          </button>
        </div>
      </div>

      <div className="footer-ad">
        <img />
      </div>
    </div>
  );
}

export default Grid;
