import { BrowserRouter } from 'react-router-dom';
import AppRouter from './router/AppRouter';
import Navbar from './components/Navbar';

function App() {
  return (
    <BrowserRouter>
      <div className="flex mt-10 justify-center space-x-32">
        <Navbar />
        <AppRouter />
      </div>
    </BrowserRouter>
  );
}

export default App;
