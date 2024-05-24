import React, { useState, useEffect } from 'react';
import MapContainer from '../component/detail/MapContainer';
import BasicLayout from '../layouts/BasicLayout';
import '../component/main/styles/GisPage.css';
import { Link } from 'react-router-dom';

function GisPage() {
  const [places, setPlaces] = useState([]);

  useEffect(() => {
    const fetchPlaces = async (latitude, longitude) => {
      try {
        const response = await fetch(`https://localhost:9092/api/gis/get?latitude=${latitude}&longitude=${longitude}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setPlaces(data.documents || []);
      } catch (error) {
        console.error('Failed to fetch places:', error);
        setPlaces([]);
      }
    };

    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          position => {
            const { latitude, longitude } = position.coords;
            fetchPlaces(latitude, longitude);
          },
          error => {
            console.error('Geolocation error:', error);
            setPlaces([]);
          }
        );
      } else {
        console.error('Geolocation is not supported by this browser.');
        setPlaces([]);
      }
    };

    getCurrentLocation();
  }, []);

  return (
    <BasicLayout>
      <div className="GisContainer">
        <div className="GisSidebar">
          <ul>
            {places && places.length > 0 ? (
              places
                .sort((a, b) => a.distance - b.distance) // place.distance를 기준으로 오름차순 정렬
                .map((place, index) => (
                  <Link to={place.place_url} key={index}>
                    <li className="GisPlaceItem">
                      <p className="GisPlaceName">{place.place_name}</p>
                      <p className="GisAddressName">{place.address_name}</p>
                      <p className="GisPhone">{place.distance}m</p>
                      <p className="GisPhone">{place.phone}</p>
                    </li>
                  </Link>
                ))
            ) : (
              <div className="GisPlaceEmpty">
                <p>정보가 없습니다.</p>
              </div>
            )}
          </ul>
        </div>
        <div className="GisMapContainer">
          <MapContainer places={places} />
        </div>
      </div>
    </BasicLayout>
  );
}

export default GisPage;

