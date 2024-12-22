import axios from 'axios';
import Cookies from 'js-cookie';

const API_BASE_URL = 'http://localhost:8081/';

export const register = async (
  login: string,
  name: string,
  img: any,
  password: string,
  timezone: any,
  telegramTag: string,
  vkTag: string
) => {
  try {
    const formData = new FormData();
    formData.append('login', login);
    formData.append('name', name);
    formData.append('password', password);
    formData.append('timezone', timezone);
    formData.append('telegramTag', telegramTag);
    formData.append('vkTag', vkTag);
    formData.append('img', img);

    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    const response = await axios.post(API_BASE_URL + 'enter/register', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    Cookies.set('token', response.headers.get('token'));
    axios.defaults.headers.common['token'] = Cookies.get('token');
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const login = async (login: string, password: string) => {
  try {
    const response = await axios.post(API_BASE_URL + `enter/login`, {
      login,
      password
    });

    Cookies.set('token', response.headers.get('token'));
    axios.defaults.headers.common['token'] = Cookies.get('token');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const cleanCookieToken = () => {
  Cookies.remove('token');
  axios.defaults.headers.common['token'] = undefined;
};

export const isLoggedIn = () => {
  return Cookies.get('token') !== undefined;
};
