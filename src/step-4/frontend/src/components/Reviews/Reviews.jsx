import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchReviews, selectReviews } from '../../redux/slices/ReviewSlice';
import Review from './Review';

const Reviews = ({ user }) => {
  const dispatch = useDispatch();
  const reviews = useSelector(selectReviews);

  useEffect(() => {
    dispatch(fetchReviews(user.login));
  }, [dispatch, user.login]);

  return (
    <div>
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
  );
};

export default Reviews;
