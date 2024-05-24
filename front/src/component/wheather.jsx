import React, { useState, useEffect } from 'react';
import axios from 'axios';
import IconRenderer from './renderIcon'; // 아이콘 렌더링 컴포넌트 import

const WeatherButton = () => {
  const [weather, setWeather] = useState(null);
  const [cocktails, setCocktails] = useState([]);
  const [randomCocktail, setRandomCocktail] = useState(null);
  const [loadingWeather, setLoadingWeather] = useState(false);
  const [loadingRandomCocktail, setLoadingRandomCocktail] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    // 페이지가 처음 로드될 때 현재 위치의 날씨 정보를 가져옴
    handleGetCurrentLocation();
  }, []);

  const fetchWeather = async (lat, lon) => {
    setLoadingWeather(true);
    try {
      const response = await axios.get('https://localhost:9092/weather/api/today', {
        params: { lat, lon },
      });
      setWeather(response.data.weatherInfo);
      setCocktails(response.data.recommendedCocktails);
      // 페이지 로드 시 랜덤 칵테일 선택
      selectRandomCocktail();
    } catch (error) {
      console.error('Error fetching weather data:', error);
      setError('Error fetching weather data.');
    }
    setLoadingWeather(false);
  };

  const handleGetCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          fetchWeather(latitude, longitude);
        },
        (error) => {
          console.error('Error getting current location:', error);
          setError('Error getting current location.');
        }
      );
    } else {
      setError('Geolocation is not supported by this browser.');
    }
  };

  const handleGetRandomCocktail = async () => {
    if (!loadingRandomCocktail && cocktails.length > 0) {
      setLoadingRandomCocktail(true);
      // 랜덤 칵테일 선택
      const randomIndex = Math.floor(Math.random() * cocktails.length);
      setRandomCocktail(cocktails[randomIndex]);
      setLoadingRandomCocktail(false);
    }
  };

  const selectRandomCocktail = () => {
    if (cocktails.length > 0) {
      const randomIndex = Math.floor(Math.random() * cocktails.length);
      setRandomCocktail(cocktails[randomIndex]);
    }
  };

  return (
    <div>
      <button onClick={handleGetCurrentLocation} disabled={loadingWeather}>
        {loadingWeather ? 'Loading...' : 'Get Weather for Current Location'}
      </button>
      {error && <p>{error}</p>}
      {weather && (
        <div>
          <h3>Weather Data:</h3>
          <p>{`Weather: ${weather.weather}`}</p>
          <p>{`Temperature: ${weather.temperature}`}</p>
          {/* 아이콘 렌더링 */}
          {weather.weather === "흐림" && (
            <IconRenderer iconName="TiWeatherPartlySunny" />
          )}
        </div>
      )}
      {randomCocktail && (
        <div>
          <h3>Random Cocktail:</h3>
          <p>{`Cocktail name: ${randomCocktail.name}`}</p>
          <p>{`Ingredient: ${randomCocktail.ingredient1}`}</p>
        </div>
      )}
      <button onClick={handleGetRandomCocktail} disabled={loadingRandomCocktail || !cocktails.length}>
        {loadingRandomCocktail ? 'Loading...' : 'Get Random Cocktail'}
      </button>
    </div>
  );
};

export default WeatherButton;