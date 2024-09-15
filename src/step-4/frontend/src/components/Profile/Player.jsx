import { Link } from 'react-router-dom';
import Character from '../Character';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  fetchCharacters,
  selectCharacters,
  selectError,
  selectLoading
} from '../../redux/slices/CharacterSlice';

const Player = ({ user, isMyProfile }) => {
  const dispatch = useDispatch();
  const characters = useSelector(selectCharacters);
  const loading = useSelector(selectLoading);
  const error = useSelector(selectError);

  useEffect(() => {
    dispatch(fetchCharacters(user.login));
  }, [dispatch, user.login]);

  if (loading) {
    return <div>Загрузка...</div>;
  } else if (error) {
    return <div>Произошла ошибка!</div>;
  } else {
    return (
      <div className="space-y-3 mb-10">
        <div>Персонажи</div>
        {characters.length === 0 ? (
          <div> Персонажи отсутствуют!</div>
        ) : (
          characters?.map((character) => (
            <Character
              key={character.id}
              characterId={character.id}
              name={character.name}
              status={character.currentStatus}
              gameSystemId={character.gameSystemId}
            />
          ))
        )}
        {isMyProfile && (
          <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg px-2 ">
            <Link to="/character-creation">Создать персонажа</Link>
          </button>
        )}
      </div>
    );
  }
};

export default Player;
