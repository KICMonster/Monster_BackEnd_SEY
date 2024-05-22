import React, { useState } from 'react';
import axios from 'axios';
import IconRenderer from './renderIcon'; // 아이콘 렌더링 컴포넌트 import

const WeatherButton = () => {
  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchWeather = async (lat, lon) => {
    setLoading(true);
    try {
      const response = await axios.get('https://localhost:9092/weather/api/today', {
        params: { lat, lon },
      });
      setWeather(response.data);
      console.log(response.data);
    } catch (error) {
      console.error('Error fetching weather data:', error);
      setError('Error fetching weather data.');
    }
    setLoading(false);
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

  return (
    <div>
      <button onClick={handleGetCurrentLocation} disabled={loading}>
        {loading ? 'Loading...' : 'Get Weather for Current Location'}
      </button>
      {error && <p>{error}</p>}
      {weather && (
        <div>
          <h3>Weather Data:</h3>
          <p>{`Weather: ${weather.weather}`}</p>
          <p>{`Temperature: ${weather.temperature}`}</p>
          {/* 아이콘 렌더링 */}
          {console.log(weather.icon)}
          {weather.weather == "흐림" && (
          <IconRenderer iconName="TiWeatherPartlySunny" />
          )}
        </div>
      )}
    </div>
  );
};

export default WeatherButton;