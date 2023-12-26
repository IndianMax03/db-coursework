import { IoIosArrowDown } from 'react-icons/io';
import { IoIosArrowUp } from 'react-icons/io';
import { changeKarma } from '../../service/data.service';
import { useState } from 'react';

//   TODO: remove visibility after db karma fix

const Karma = ({ sender, receiver, isMyProfile }) => {
  const [karma, setKarma] = useState(receiver.karma);
  const [visible, setVisible] = useState(true);

  const handleKarmaChange = (increase) => {
    if (increase) {
      changeKarma(sender.id, receiver.id, true);
      setKarma(karma + 1);
    } else {
      setKarma(karma - 1);
      changeKarma(sender.id, receiver.id, false, true);
    }
    setVisible(false);
  };

  return (
    <div className=" text-red-600 font-medium flex space-x-3">
      {!isMyProfile && visible && (
        <button className="hover:text-black" onClick={() => handleKarmaChange(false)}>
          <IoIosArrowDown />
        </button>
      )}
      <div className={visible ? '' : 'ml-9'}>{karma}</div>
      {!isMyProfile && visible && (
        <button className="hover:text-black" onClick={() => handleKarmaChange(true)}>
          <IoIosArrowUp />
        </button>
      )}
    </div>
  );
};

export default Karma;
