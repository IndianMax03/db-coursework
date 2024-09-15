import { useDispatch, useSelector } from 'react-redux';
import Character from './Character';
import { selectSelf } from '../redux/slices/UserSlice';
import {
  fetchCharacters,
  selectError,
  selectFreeCharacters,
  selectLoading
} from '../redux/slices/CharacterSlice';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { sendCharacterLobbyRequest } from '../service/data.service';

const CharacterSelection = () => {
  const { gameId } = useParams();
  const dispatch = useDispatch();
  const user = useSelector(selectSelf);
  const navigate = useNavigate();
  const characters = useSelector(selectFreeCharacters);
  const loading = useSelector(selectLoading);
  const hasError = useSelector(selectError);

  useEffect(() => {
    dispatch(fetchCharacters(user.login));
  }, [dispatch, user.login]);

  const handleSelect = (characterId) => {
    sendCharacterLobbyRequest(gameId, characterId).then(navigate(`/profile/${user.login}`));
  };

  if (loading) {
    return <div>Загрузка... </div>;
  }

  if (hasError) {
    return <div>Ошибка!</div>;
  }

  return (
    <div className="">
      <div className="text-lg mb-3">Выберите персонажа для подачи заявки</div>
      {characters.length === 0 ? (
        <div> Персонажи отсутствуют!</div>
      ) : (
        characters.map((character) => (
          <div className="mb-3">
            <Character
              key={character.id}
              characterId={character.id}
              name={character.name}
              status={character.currentStatus}
              gameSystemId={character.gameSystemId}
              button={
                <button
                  className="mt-10 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
                  onClick={() => handleSelect(character.id)}
                >
                  Выбрать персонажа
                </button>
              }
            />
          </div>
        ))
      )}
    </div>
  );
};

export default CharacterSelection;
