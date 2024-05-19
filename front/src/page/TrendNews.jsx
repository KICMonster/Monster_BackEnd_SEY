import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import axios from 'axios';


// 뉴스 데이터. 폰트, 각 기사 크기적용 등 CSS 처리 필요. 페이징 예시로 10개씩 10페이까지 지정했으나 필요에따라 조정.
const API_KEY = import.meta.env.VITE_GOOOLE_API_KEY;
const SEARCH_ENGINE_ID = import.meta.env.VITE_GOOGLE_SEARCH_ENGINE_ID;
const query = '칵테일 트렌드';  // 검색어 설정

function TrendNews() {
    const [articles, setArticles] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const numPerPage = 10; // 한 페이지당 가져올 결과 수
    const totalResults = 100; // 예시로 총 결과 수를 100으로 가정

    useEffect(() => {
        // 로컬 스토리지에서 뉴스 데이터 가져오기
        const cachedNewsData = localStorage.getItem('newsData');
        const cachedTimestamp = localStorage.getItem('newsDataTimestamp');
        const now = new Date().getTime();
        
        // 24시간마다 데이터를 갱신하도록 설정
        if (cachedNewsData && cachedTimestamp && (now - cachedTimestamp < 24 * 60 * 60 * 1000)) {
            console.log("저장된 데이터를 사용합니다.");
            setArticles(JSON.parse(cachedNewsData));
        } else {
            // 캐시된 데이터가 없거나 24시간이 지났을 경우에만 새로운 데이터를 가져옴
            fetchNews();
        }
    }, []);
    const fetchNews = async () => {
        const totalPages = Math.ceil(totalResults / numPerPage);
        const allArticles = [];

        try {
            for (let i = 0; i < totalPages; i++) {
                const startIndex = i * numPerPage + 1;
                    const url = `https://www.googleapis.com/customsearch/v1?q=${encodeURIComponent(query)}&cx=${SEARCH_ENGINE_ID}&key=${API_KEY}&num=${numPerPage}&start=${startIndex}&sort=date`;
                    
                    const response = await axios.get(url);
                    const articles = response.data.items.map(item => ({
                        title: item.title,
                        snippet: item.snippet,
                        link: item.link,
                        image: item.pagemap?.cse_image?.[0]?.src
                    }));

                    allArticles.push(...articles);
                }

                setArticles(allArticles);
            // 전체 데이터를 로컬 스토리지에 저장
            localStorage.setItem('newsData', JSON.stringify(allArticles));
            localStorage.setItem('newsDataTimestamp', new Date().getTime());
        } catch (error) {
            console.error('Error fetching news:', error);
        }
    };

    const handleNextPage = () => {
        setCurrentPage(prevPage => prevPage + 1);
    };

    const handlePrevPage = () => {
        setCurrentPage(prevPage => prevPage - 1);
    };

    return (
        <>
            <Link to="/">홈</Link>
            <h1>Cocktail News</h1>
            {articles.slice((currentPage - 1) * numPerPage, currentPage * numPerPage).map((article, index) => (
                <div key={index}>
                    <h2>{article.title}</h2>
                    {article.image && <img src={article.image} alt={article.title} />}
                    <p>{article.snippet}</p>
                    <a href={article.link} target="_blank" rel="noopener noreferrer">Read more</a>
                </div>
            ))}
            <div>
                <button onClick={handlePrevPage} disabled={currentPage === 1}>Previous</button>
                <button onClick={handleNextPage} disabled={currentPage === Math.ceil(totalResults / numPerPage)}>Next</button>
            </div>
        </>
    );
}

export default TrendNews;