import { Link } from 'react-router-dom';
import { useState } from 'react';
import { login as enter } from '../../auth/auth.service';

const LoginForm = () => {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    enter(login, password);
    console.log('login success!');
    console.log(login, password);
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
