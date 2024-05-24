import React, { useState, useEffect } from "react";
import BasicLayout from "../../layouts/BasicLayout";
import '../../component/main/styles/snackpage.css'

function Snackpage() {
    // 스낵 데이터를 저장할 상태를 선언
    const [snack, setSnack] = useState({});

    // useEffect 훅을 사용하여 컴포넌트가 마운트될 때와 윈도우 주소가 변경될 때마다 데이터를 가져옴
    useEffect(() => {
        // 윈도우 주소에서 ID 추출
        const pathname = window.location.pathname;
        const id = pathname.split('/').pop();

        // 스낵 데이터를 비동기적으로 가져오는 함수 선언
        const fetchSnackData = async () => {
            try {
                // API 엔드포인트 설정 (URL 파라미터 사용)
                const snackEndpoint = `https://localhost:9092/api/ingredient/${id}`;
                // API 호출
                const snackResponse = await fetch(snackEndpoint);
                // 응답 데이터를 JSON 형식으로 변환
                const snackData = await snackResponse.json();
                // 스낵 데이터를 상태에 저장
                setSnack(snackData);
            } catch (error) {
                console.error('Error fetching snack detail:', error);
            }
        };

        // 컴포넌트가 마운트될 때와 윈도우 주소가 변경될 때마다 데이터를 가져오는 함수 호출
        fetchSnackData();
    }, []); // 컴포넌트가 마운트될 때 한 번만 실행

    // 스낵 데이터를 화면에 렌더링
    return (
        <BasicLayout>
            <div className="snackBody">
                <div className="snackImageContainer">
                    <img src={snack.image} alt={snack.name} />
                </div>
                <div className="snackDescription">
                    <div className="snackTitle">
                        <h1>{snack.name}</h1>
                        <p>{snack.description}</p>
                    </div>
                </div>
            </div>
        </BasicLayout>
    );
}

export default Snackpage;