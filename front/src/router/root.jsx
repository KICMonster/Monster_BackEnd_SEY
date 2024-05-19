import { createBrowserRouter } from 'react-router-dom';
import Home from '../page/Home';
import LoginPage from '../page/LoginPage';
import LoginRouter from './LoginRouter';
import Join from '../page/Join';
import AdditionalForm from '../component/AdditionalForm';
import TasteAnalysis from '../component/tasteAnalysis';
import AnalysisComplete from '../page/AnalysisComplete';
import TrendNews from '../page/TrendNews';

const root = createBrowserRouter([
  {
    path: '',
    element: <Home />
  },
  {
    path: '/login',
    element: <LoginPage />,
    children: LoginRouter() // LoginRouter()가 객체를 반환하므로 이를 바로 사용
  },
  {
    path: '/join',
    element: <Join />
  },
  {
    path : '/additional',
    element : <AdditionalForm/>
  },
  {
    path : '/taste', // 기호조사. 필요 페이지와 연결할것.지금은 home.jsx에 버튼. 회원가입 페이지와 연결할 경우 로직 수정할 필요.   
    element : <TasteAnalysis/>, // 회원가입 로직과 연결 할 시 프론트 경로작업&비동기 통신전달값 추가 후 백엔드에 문의
    children: [
      {
        path: 'complete', // '/taste'의 하위 경로로 'complete'를 정의
        element: <AnalysisComplete/> // '/taste/complete'에 해당하는 컴포넌트
      }
    ]
  },
  {
    path: '/trendNews',       // 뉴스 경로. 조정 필요. 
    element: <TrendNews/>
  }
]);

export default root;