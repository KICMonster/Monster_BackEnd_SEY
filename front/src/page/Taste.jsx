import React, { useState, useEffect } from 'react';
import options from '../data/questionData.json';
import "../css/taste.css"

/**@todo 필요시 클래스 이름, 폰트 css 수정. 선택지 버튼에 아이콘이나 이미지 추가 필요한 경우 questionData.json 
 * 파일 객체값에 "icon":"아이콘 이름" 형식으로 추가하고 import 후 
 * 옵션 id에 따라 icon또는 이미지값 렌더링 하는 코드 추가할 것. 비동기 통신에는 기존처럼 optionid만 담기도록 주의.
   */
const OptionSelector = ({ onSelectionComplete }) => { // props로 onSelectionComplete 함수 받기
  const [currentOptions, setCurrentOptions] = useState([]);
  const [selectedOptionIds, setSelectedOptionIds] = useState([]);

  useEffect(() => {
    const initialOptions = options.slice(0, 2);
    setCurrentOptions(initialOptions);
  }, []);

  const handleOptionClick = (selectedOptionId) => {
    setSelectedOptionIds(prevIds => {
      const updatedIds = [...prevIds, selectedOptionId];
      
      const selectedOption = options.find(option => option.id === selectedOptionId);
      if(selectedOption.nextOptions) {
        const nextOptionsIds = selectedOption.nextOptions;
        const nextOptions = options.filter(option => nextOptionsIds.includes(option.id));
        setCurrentOptions(nextOptions);
      } else {
        setCurrentOptions([]);
        // 선택이 완료되면 해당 결과를 상위 컴포넌트로 전달
        const tasteString = updatedIds.join('.'); // 배열을 문자열로 변환
        onSelectionComplete(tasteString); // 문자열로 변환된 값을 전달
      }
      
      return updatedIds;
    });
  };

  return (
    <div>
      {currentOptions.map(option => (
        <button className='font' key={option.id} onClick={() => handleOptionClick(option.id)}
         style={{ width: '250px', height: '150px' }}>
          {option.label}
        </button>
      ))}
    </div>
  );
};

export default OptionSelector;