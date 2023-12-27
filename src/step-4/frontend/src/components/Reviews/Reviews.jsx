import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchReviews, selectReviews } from '../../redux/slices/ReviewSlice';
import Review from './Review';
import { sendReview } from '../../service/data.service';
import { selectSelf } from '../../redux/slices/UserSlice';

const Reviews = ({ user, isMyProfile }) => {
  const dispatch = useDispatch();
  const reviews = useSelector(selectReviews);
  const options = [1, 2, 3, 4, 5];
  const [rating, setRating] = useState('1');
  const [content, setContent] = useState('');
  const self = useSelector(selectSelf);

//   TODO: check if review already leaved 

  useEffect(() => {
    dispatch(fetchReviews(user.login));
  }, [content, dispatch, user.login]);

  const handleReviewSend = () => {
    sendReview(self.id, user.id, parseInt(rating), content);
  };

  return (
    <div>
      <div className="mb-10">
        {reviews.length === 0 ? (
          <div> Отзывы отсутствуют! </div>
        ) : (
          reviews.map((review, index) => (
            <Review
              key={index}
              content={review.content}
              date={review.date}
              rating={review.rating}
              name={review.name}
            />
          ))
        )}
      </div>
      {!isMyProfile && (
        <div className="space-y-3 mb-10">
          <div> Оставить отзыв </div>
          <div className="flex">
            <div> Рейтинг:</div>
            <select
              onChange={(e) => {
                setRating(e.target.value);
              }}
              value={rating}
              name="rating"
              id="rating"
            >
              {options.map((option) => (
                <option value={option}>{option}</option>
              ))}
            </select>
          </div>
          <div> Текст отзыва: </div>
          <textarea
            className="flex border-solid border-2 border-slate-500 rounded-lg w-128"
            value={content}
            onChange={(e) => {
              setContent(e.target.value);
            }}
          ></textarea>
          <button
            className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
            onClick={handleReviewSend}
          >
            Отправить
          </button>
          
        </div>
      )}
    </div>
  );
};

export default Reviews;
