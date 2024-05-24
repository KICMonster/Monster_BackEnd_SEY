import { useMutation, useQueryClient } from 'react-query';
import axios from 'axios';

// // 칵테일을 클릭할 때 호출되는 핸들러 함수
// const handleCocktailClick = (id) => {
//     // 클릭된 칵테일의 id와 타임스탬프 생성
//     const timestamp = new Date().toISOString();
//     // 커스텀 훅을 사용하여 칵테일 조회수 업데이트 요청
//     updateViews({ id, timestamp });
//   };
// 칵테일 조회수를 업데이트하는 비동기 함수
const updateCocktailViews = async ({ id, timestamp }) => {
  // 칵테일 조회수 업데이트를 위한 PUT 요청
  await axios.put(`https://localhost:9092/view/api/cocktails/${id}`, { timestamp });
};

// 칵테일 조회수 업데이트를 위한 커스텀 훅
export const useUpdateCocktailViews = () => {
    const queryClient = useQueryClient();
  
    return useMutation(updateCocktailViews, {
      onSuccess: (data, variables) => {
        // 서버에서 반환된 업데이트된 조회수를 사용하여 칵테일 정보 업데이트
        queryClient.setQueryData('cocktails', oldData => {
          return oldData.map(cocktail =>
            cocktail.id === variables.id
              ? { ...cocktail, viewCount: data } // 서버에서 반환한 조회수로 업데이트
              : cocktail
          );
        });
      },
    });
  };

// 다른 커스텀 훅을 추가할 수 있습니다.
//export const useOtherCustomHook = () => {
  // 여기에 다른 조회수 커스텀 훅 로직을 추가하세요.
//};