import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useState } from 'react';
import { fetchSelf } from '../../redux/slices/UserSlice';

const RegisterForm = () => {
  const dispatch = useDispatch();
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [timezone, setTimezone] = useState(null);
  const [telegramTag, setTelegramTag] = useState(null);
  const [vkTag, setVkTag] = useState(null);
  const [img, setSelectedImage] = useState(null);
  const navigate = useNavigate();

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    setSelectedImage(file);
  };

  const handleRegister = () => {
    const isLoggingIn = false;
    dispatch(
      fetchSelf({ login, isLoggingIn, img, name, password, timezone, telegramTag, vkTag })
    ).then(navigate(`/profile/${login}`));
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
        <div className="flex justify-between space-x-5">
          <div>Имя </div>
          <input onChange={(e) => setName(e.target.value)} className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Часовой пояс </div>
          <input
            onChange={(e) => setTimezone(e.target.value)}
            className="border-2 rounded-lg"
          ></input>
        </div>
        <div className="text-slate-500 text-sm "> От Московского времени</div>
        <div className="flex justify-between space-x-5">
          <div>Телеграм (тег) </div>
          <input
            onChange={(e) => setTelegramTag(e.target.value)}
            className="border-2 rounded-lg"
          ></input>
        </div>
        <div className="text-slate-500 text-sm "> В формате @yourtag</div>
        <div className="flex justify-between space-x-5">
          <div>Вконтакте (id) </div>
          <input onChange={(e) => setVkTag(e.target.value)} className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить аватарку</div>
          <input type="file" onChange={handleImageChange} />
        </div>
        <div className="flex justify-center space-x-5">
          <div> У меня уже есть аккаунт </div>
          <Link to={'/login'} className="text-slate-500">
            Войти
          </Link>
        </div>
        <div className="flex justify-center">
          <button
            onClick={handleRegister}
            className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
          >
            Зарегистрироваться
          </button>
        </div>
      </div>
    </div>
  );
};

export default RegisterForm;
