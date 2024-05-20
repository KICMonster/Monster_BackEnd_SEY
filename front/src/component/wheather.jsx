import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Weather() {
  const [weather, setWeather] = useState(null);
  const [location, setLocation] = useState({ lat: null, lon: null });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 현재 위치를 얻기 위한 Geolocation API 사용
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        const { latitude, longitude } = position.coords;
        setLocation({ lat: latitude, lon: longitude });
        fetchWeather(latitude, longitude);
      });
    } else {
      alert('Geolocation is not supported by this browser.');
    }
  }, []);

  const fetchWeather = async (lat, lon) => {
    try {
      const serviceKey = 'vaPRH1XIvl6Z8A4rW3qIonrlxY5ExU3gSZgxL98cggj9/f6jTdNftyXbzBffsj1hBe9rn8ECJhfw7dAkoj9d0w=='; // 기상청 API 키
      const base_date = new Date().toISOString().slice(0, 10).replace(/-/g, '');
      const base_time = '0630'; // 기상청 API 문서에 따라 수정 필요
      const { nx, ny } = latLonToGrid(lat, lon); // 좌표 변환

      const response = await axios.get(`https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst`, {
        params: {
          serviceKey,
          numOfRows: 10,
          pageNo: 1,
          dataType: 'JSON',
          base_date,
          base_time,
          nx,
          ny,
        },
      });

      console.log(response.data); // 응답 데이터 로그 출력

      if (response.data && response.data.response && response.data.response.body) {
        setWeather(response.data.response.body.items.item);
      } else {
        console.error('Unexpected response format:', response.data);
      }
      setLoading(false);
    } catch (error) {
      console.error('Error fetching the weather data:', error.message);
      setLoading(false);
    }
  };

  const latLonToGrid = (lat, lon) => {
    // 기상청 격자 좌표 변환 공식 (단순화된 예시)
    const RE = 6371.00877; // 지구 반경 (km)
    const GRID = 5.0; // 격자 간격 (km)
    const SLAT1 = 30.0; // 표준 위도 1 (degree)
    const SLAT2 = 60.0; // 표준 위도 2 (degree)
    const OLON = 126.0; // 기준점 경도 (degree)
    const OLAT = 38.0; // 기준점 위도 (degree)
    const XO = 43; // 기준점 X좌표 (격자 기준)
    const YO = 136; // 기준점 Y좌표 (격자 기준)

    const DEGRAD = Math.PI / 180.0;
    const re = RE / GRID;
    const slat1 = SLAT1 * DEGRAD;
    const slat2 = SLAT2 * DEGRAD;
    const olon = OLON * DEGRAD;
    const olat = OLAT * DEGRAD;

    let sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
    let sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sf = (sf ** sn * Math.cos(slat1)) / sn;
    let ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
    ro = (re * sf) / (ro ** sn);

    const rs = {};
    let ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
    ra = (re * sf) / (ra ** sn);
    let theta = lon * DEGRAD - olon;
    if (theta > Math.PI) theta -= 2.0 * Math.PI;
    if (theta < -Math.PI) theta += 2.0 * Math.PI;
    theta *= sn;
    rs['nx'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
    rs['ny'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

    return rs;
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="App">
      {weather ? (
        <div>
          <h1>Current Weather</h1>
          {weather.map((item, index) => (
            <div key={index}>
              <p>{item.category}: {item.fcstValue}</p>
            </div>
          ))}
        </div>
      ) : (
        <p>No weather data available</p>
      )}
    </div>
  );
}

export default Weather;