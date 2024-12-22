import pfp from '../../../public/pfp.jpg';

const Review = ({ rating, name, content, date, picture }) => {
  const checkedStars = Array.from({ length: rating }, (_, index) => index + 1);
  const uncheckedStars = Array.from({ length: 5 - rating }, (_, index) => index + 1);

  return (
    <div className="flex border-solid border-b-2  border-slate-500">
      <div className="flex space-x-10 w-128 p-3">
        <div>
          {picture !== null ? (
            <img
              src={`data:image/png;base64,${picture}`}
              alt="profile"
              className=" rounded-full"
            ></img>
          ) : (
            <img src={pfp} alt="profile" className="  rounded-full"></img>
          )}
          <div>{name}</div>
        </div>
        <div className="grid">
          <div className="flex space-x-10 mb-3">
            <div>{date}</div>
            <div>
              <div className="flex">
                {checkedStars.map((star) => (
                  <svg
                    key={star}
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                  >
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path
                      fill="orange"
                      d="M12 2l2.4 7.2H21l-6 4.8 2.4 7.2L12 16.8l-6 4.8 2.4-7.2-6-4.8h7.6L12 2z"
                    />
                  </svg>
                ))}
                {uncheckedStars.map((star) => (
                  <svg
                    key={star}
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                  >
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path
                      fill="grey"
                      d="M12 2l2.4 7.2H21l-6 4.8 2.4 7.2L12 16.8l-6 4.8 2.4-7.2-6-4.8h7.6L12 2z"
                    />
                  </svg>
                ))}
              </div>
            </div>
          </div>
          <div>{content}</div>
        </div>
      </div>
    </div>
  );
};

export default Review;
