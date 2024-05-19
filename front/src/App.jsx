import { RouterProvider } from 'react-router-dom'
import './App.css'
import root from './router/root'

function App() {


  return (
    <div className='font'> 
    <RouterProvider router={root}/>
    </div>
  )
}

export default App
