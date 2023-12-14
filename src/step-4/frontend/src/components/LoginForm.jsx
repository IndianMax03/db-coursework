import { Link } from 'react-router-dom';

const LoginForm = () => {
  return (
    <div className="w-128 flex justify-center">
      <div className="w-80 space-y-3">
        <div className="flex justify-between space-x-5">
          <div>Логин </div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Пароль </div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-center space-x-5">
          {' '}
          <div> Нет аккаута? </div>
          <Link to={'/register'} className="text-slate-500">
            Зарегистрироваться
          </Link>
        </div>
        <div className="flex justify-center">
          <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            Войти
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
