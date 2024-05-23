import { RouterProvider } from 'react-router-dom'
import './App.css'
import { QueryClient, QueryClientProvider } from 'react-query';
import root from './router/root'


const queryClient = new QueryClient();

function App() {
  return (
    <div className='font'>
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={root} />
      </QueryClientProvider>
    </div>
  );
}

export default App;
