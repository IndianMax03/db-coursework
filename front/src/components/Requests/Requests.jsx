import { useState } from 'react';

const Requests = () => {
  const [status, setStatus] = useState('incoming');

  const handleStatusChange = (newStatus) => {
    if (newStatus === status) {
      return;
    }
    setStatus(newStatus);
  };

  return (
    <div>
      <div>Мои заявки</div>
      <div className=" flex space-x-5 justify-center mb-5">
        <button
          className={
            'incoming' === status
              ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
              : 'border-solid border-2 border-slate-500 rounded-lg w-full'
          }
          onClick={() => handleStatusChange('incoming')}
        >
          Входящие
        </button>
        <button
          className={
            'outgoing' === status
              ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
              : 'border-solid border-2 border-slate-500 rounded-lg w-full'
          }
          onClick={() => handleStatusChange('outgoing')}
        >
          Исходящие
        </button>
      </div>
    </div>
  );
};

export default Requests;
