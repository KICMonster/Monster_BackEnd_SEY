import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import axios from 'axios';
import './TrendNews.css';
import BasicLayout from '../../layouts/BasicLayout';

const API_KEY = import.meta.env.VITE_GOOOLE_API_KEY;
const SEARCH_ENGINE_ID = import.meta.env.VITE_GOOGLE_SEARCH_ENGINE_ID;
const query = '칵테일 트렌드';

function TrendNews() {
    const [articles, setArticles] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const numPerPage = 10;
    const totalResults = 100;

    useEffect(() => {
        const cachedNewsData = localStorage.getItem('newsData');
        const cachedTimestamp = localStorage.getItem('newsDataTimestamp');
        const now = new Date().getTime();

        if (cachedNewsData && cachedTimestamp && (now - cachedTimestamp < 24 * 60 * 60 * 1000)) {
            setArticles(JSON.parse(cachedNewsData));
        } else {
            fetchNews();
        }

        const loadScript = (id, callback) => {
            const script = document.createElement('script');
            script.src = "https://ads-partners.coupang.com/g.js";
            script.async = true;
            document.getElementById(id).appendChild(script);
            script.onload = callback;
        };

        loadScript("ad-left", () => {
            new PartnersCoupang.G({ "id": 780257, "template": "carousel", "trackingCode": "AF0800913", "width": "140", "height": "680", "tsource": "" });
        });

        loadScript("ad-right", () => {
            new PartnersCoupang.G({ "id": 780257, "template": "carousel", "trackingCode": "AF0800913", "width": "140", "height": "680", "tsource": "" });
        });

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
        <BasicLayout>
            <div className="main-container">
                <div id="ad-left" className="ad-sidebar"></div>
                <div className="content">
                    <h1>Cocktail News</h1>
                    {articles.slice((currentPage - 1) * numPerPage, currentPage * numPerPage).map((article, index) => (
                        <div className="article-container" key={index}>
                            {article.image && <img src={article.image} alt={article.title} />}
                            <div className="article-content">
                                <h2>{article.title}</h2>
                                <p>{article.snippet}</p>
                                <a href={article.link} target="_blank" rel="noopener noreferrer">Read more</a>
                            </div>
                        </div>
                    ))}
                    <div>
                        <button onClick={handlePrevPage} disabled={currentPage === 1}>Previous</button>
                        <button onClick={handleNextPage} disabled={currentPage === Math.ceil(totalResults / numPerPage)}>Next</button>
                    </div>
                </div>
                <div id="ad-right" className="ad-sidebar"></div>
            </div>
        </BasicLayout>
    );
}

export default TrendNews;