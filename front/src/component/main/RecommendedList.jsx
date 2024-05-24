import React, { useState } from 'react';
import defaultImage from '../../img/main1.png';
import newImage from '../../img/main2.jpg';

// 임의의 칵테일 이름과 설명
const cocktails = [
    { name: '모히또', description: '라임과 민트 향이 물씬 나는 상쾌한 칵테일' },
    { name: '마티니', description: '진과 버몬트, 오렌지 비터의 감각적인 조합' },
    { name: '카페리누이', description: '카푸치노와 맥주의 완벽한 만남' },
    // 추가적인 칵테일들을 필요에 따라 여기에 추가할 수 있습니다.
];

function RecommendedList() {
    const images = [defaultImage, newImage];
    const [currentIndex, setCurrentIndex] = useState(0);


    return (
        <div style={{ position: 'relative', maxWidth: '1000px', margin: 'auto' }}>
           
            <div >
                
                <button onClick={() => window.location.href = '/ViewPage'} style={{ marginTop: '10px', marginRight: '10px' }}>마시러 가기</button>
            </div>
        </div>
    );
}

export default RecommendedList;
