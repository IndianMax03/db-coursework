import axios from 'axios';
import Cookies from 'js-cookie';

const API_BASE_URL = 'http://localhost:8081/';

export const register = async (login, name, password, timezone, telegramTag, vkTag) => {
  try {
    const response = await axios.post(API_BASE_URL + 'enter/register', {
      login,
      name,
      password,
      timezone,
      telegramTag,
      vkTag
    });
    Cookies.set('token', response.headers.get('token'));
    axios.defaults.headers.common['token'] = Cookies.get('token');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const login = async (login, password) => {
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
