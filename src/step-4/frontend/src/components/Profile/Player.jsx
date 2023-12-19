import { Link } from 'react-router-dom';
import Character from '../Character';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  fetchCharacters,
  selectCharacters,
  selectError,
  selectLoading
} from '../../redux/slices/CharacterSlice';

const Player = () => {
  const dispatch = useDispatch();
  const characters = useSelector(selectCharacters);
  const loading = useSelector(selectLoading);
  const error = useSelector(selectError);

  useEffect(() => {
    const login = 'indian_max03';
    dispatch(fetchCharacters(login));
    console.log(characters);
  }, [dispatch]);

  if (loading) {
    return <div>Загрузка...</div>;
  } else if (error) {
    return <div>Произошла ошибка!</div>;
  } else {
    return (
      <div className="space-y-3 mb-10">
        <div>Персонажи</div>
        {characters?.map((character) => (
          <Character
            key={character.id}
            name={character.name}
            status={character.currentStatus}
            gameSystemId={character.gameSystemId}
          />
        ))}
        <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg px-2 ">
          <Link to="/character-creation">Создать персонажа</Link>
        </button>
      </div>
    );
  }
};

export default Player;
