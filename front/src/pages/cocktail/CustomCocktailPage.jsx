import React, { useState, useEffect } from "react";
import BasicLayout from "../../layouts/BasicLayout";
import { Link } from "react-router-dom";
import "../../pages/cocktail/CustomCocktailPage.css";

function CustomCocktailPage() {
  const [cocktails, setCocktails] = useState([]);
  const [baseFilter, setBaseFilter] = useState('');
  const [alcoholFilter, setAlcoholFilter] = useState('');
  const [uniqueIngredients, setUniqueIngredients] = useState([]);

  const fetchAllCocktails = async () => {
    try {
      // API 호출을 주석 처리하고 더미 데이터를 사용
      // const endpoint = 'https://localhost:9092/api/custom-cocktail'; // API 엔드포인트 수정
      // const response = await fetch(endpoint);
      // const data = await response.json();

      // 더미 데이터
      const data = [
        { id: 1, name: "Cocktail A", image: "https://via.placeholder.com/150", recommendCount: 5, ingredient1: "Vodka", alcoholic: "Yes" },
        { id: 2, name: "Cocktail B", image: "https://via.placeholder.com/150", recommendCount: 15, ingredient1: "Gin", alcoholic: "Yes" },
        { id: 3, name: "Cocktail C", image: "https://via.placeholder.com/150", recommendCount: 25, ingredient1: "Rum", alcoholic: "No" },
        { id: 4, name: "Cocktail D", image: "https://via.placeholder.com/150", recommendCount: 35, ingredient1: "Tequila", alcoholic: "Yes" },
        { id: 5, name: "Cocktail E", image: "https://via.placeholder.com/150", recommendCount: 20, ingredient1: "Whiskey", alcoholic: "No" }
      ];
      setCocktails(data);

      // 모든 고유한 리큐르 추출
      const uniqueIngredients = [...new Set(data.map(cocktail => cocktail.ingredient1))];
      setUniqueIngredients(uniqueIngredients);
    } catch (error) {
      console.error('Error fetching cocktails:', error);
    }
  };

  useEffect(() => {
    fetchAllCocktails();
  }, []);

  // 필터링된 칵테일 목록을 계산
  const filteredCocktails = cocktails.filter(cocktail => {
    const baseMatch = baseFilter === '' || cocktail.ingredient1 === baseFilter;
    const alcoholMatch = alcoholFilter === '' || (alcoholFilter === 'Yes' && cocktail.alcoholic === 'Yes') || (alcoholFilter === 'No' && cocktail.alcoholic === 'No');
    return baseMatch && alcoholMatch;
  });

  // 베이스주 필터 설정 함수
  const handleBaseFilterChange = (event) => {
    setBaseFilter(event.target.value);
  };

  // 알콜/논알콜 필터 설정 함수
  const handleAlcoholFilterChange = (event) => {
    setAlcoholFilter(event.target.value);
  };

  // 필터 초기화 함수
  const handleResetFilter = () => {
    setBaseFilter('');
    setAlcoholFilter('');
  };

  // 추천 수에 따른 아이콘 선택 함수
  const getRecommendationIconClass = (recommendCount) => {
    if (recommendCount >= 30) return 'star-icon'; // 별
    if (recommendCount >= 20) return 'heart-icon'; // 하트
    if (recommendCount >= 10) return 'triangle-icon'; // 세모
    return ''; // 기본 아이콘
  };

  const getRecommendationIcon = (recommendCount) => {
    if (recommendCount >= 30) return '⭐'; // 별
    if (recommendCount >= 20) return '❤️'; // 하트
    if (recommendCount >= 10) return '▲'; // 세모
    return '⬤'; // 기본 아이콘
  };

  return (
    <BasicLayout>
      <div className="filter-dropdowns">
        <select onChange={handleBaseFilterChange} value={baseFilter}>
          <option value="">리큐르</option>
          {uniqueIngredients.map((ingredient, index) => (
            <option key={index} value={ingredient}>{ingredient}</option>
          ))}
        </select>
        <select onChange={handleAlcoholFilterChange} value={alcoholFilter}>
          <option value="">알코올 여부</option>
          <option value="Yes">알코올</option>
          <option value="No">논알코올</option>
        </select>
        <button onClick={handleResetFilter}>Reset Filter</button>
      </div>
      <div className="cocktail-list">
        {filteredCocktails.map(cocktail => (
          <Link key={cocktail.id} to={`/custom-cocktail/${cocktail.id}`} className="cocktail-link">
            <div className="cocktail-item">
              <div className="image-box">
                <img src={cocktail.image || 'default-image-url.jpg'} alt={cocktail.name} className="cocktail-image" />
                <div className={`recommendation-icon ${getRecommendationIconClass(cocktail.recommendCount)}`}>
                  {getRecommendationIcon(cocktail.recommendCount)}
                </div>
              </div>
              <h2 className="cocktail-name">{cocktail.name}</h2>
            </div>
          </Link>
        ))}
      </div>
    </BasicLayout>
  );
}

export default CustomCocktailPage;
