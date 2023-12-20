import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/';

export const register = async (login, name, password, timezone, telegramTag, vkTag) => {
  await axios.post(API_BASE_URL + 'enter/register', {
    login,
    name,
    password,
    timezone,
    telegramTag,
    vkTag
  });
};

export const login = async (login, password) => {
  try{
   const response = await axios.post(API_BASE_URL + 'enter/login', {
      login,
      password
    });
    return response.data;
  }
  catch(error){
    throw error;
  }
 
};
