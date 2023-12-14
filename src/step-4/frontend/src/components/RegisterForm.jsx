import { Link } from 'react-router-dom';
const RegisterForm = () => {
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
        <div className="flex justify-between space-x-5">
          <div>Имя </div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Часовой пояс </div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="text-slate-500 text-sm "> От Московского времени</div>
        <div className="flex justify-between space-x-5">
          <div>Телеграм (тег) </div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="text-slate-500 text-sm "> В формате @yourtag</div>
        <div className="flex justify-between space-x-5">
          <div>Вконтакте (id) </div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить аватарку</div>
          <input type='file' accept='image/png, image/jpeg'></input>
        </div>
        <div className="flex justify-center space-x-5">
          <div> У меня уже есть аккаунт </div>
          <Link to={'/login'} className="text-slate-500">
            Войти
          </Link>
        </div>
        <div className="flex justify-center">
          <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            Зарегистрироваться
          </button>
        </div>
      </div>
    </div>
  );
};

export default RegisterForm;
