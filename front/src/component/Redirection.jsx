
// Axios 라이브러리 import
import axios from 'axios';

// 현재 URL에서 코드 값을 파싱하는 함수
function getCodeFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get('code');
}

// 코드 값 가져오기
const code = getCodeFromUrl();

// 서버로 코드 값을 전송하기 위한 Axios POST 요청
if (code) {
  const postData = { code: code };

  axios.post('https://localhost:9092/api/authenticate', postData)
    .then(response => {
      // 요청 성공 시 처리할 내용
      console.log('백엔드 응답:', response.data);
    })
    .catch(error => {
      // 요청 실패 시 처리할 내용
      console.error('에러 발생:', error);
    });
}