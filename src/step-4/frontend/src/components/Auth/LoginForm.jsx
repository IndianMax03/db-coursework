import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { login as enter } from '../../auth/auth.service';
import { useDispatch, useSelector } from 'react-redux';
import { fetchSelf, selectSelf } from '../../redux/slices/UserSlice';

const LoginForm = () => {
  const [login, setLogin] = useState('');
  const dispatch = useDispatch();
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const self = useSelector(selectSelf);

  const handleLogin = () => {
    console.log('login')
    dispatch(fetchSelf(login, password));
    console.log(self);
    navigate(`profile/${login}`);
  };


  return (
    <div className="w-128 flex justify-center">
      <div className="w-80 space-y-3">
        <div className="flex justify-between space-x-5">
          <div>Логин </div>
          <input onChange={(e) => setLogin(e.target.value)} className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Пароль </div>
          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
            className="border-2 rounded-lg"
          ></input>
        </div>
        <div className="flex justify-center space-x-5">
          <div> Нет аккаута? </div>
          <Link to={'/register'} className="text-slate-500">
            Зарегистрироваться
          </Link>
        </div>
        <div className="flex justify-center">
          <button
            className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
            onClick={handleLogin}
          >
            Войти
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
