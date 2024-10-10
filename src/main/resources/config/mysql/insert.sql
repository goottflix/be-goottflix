use goottflix;

INSERT INTO user
(id, username, email, password_hash, oauthId, birth, gender, created_at, last_login, role, is_active, preferences)
 VALUES (null, '유환주','yoohwanjoo@nate.com','1234','1234','1994-04-02','M',null,null,null,true,null);

INSERT INTO user
(id, username, email, password_hash, oauthId, birth, gender, created_at, last_login, role, is_active, preferences)
 VALUES (null, '김민지','goottflix@gmail.com','1234','1234','1998-12-12','F',null,null,null,true,null);

INSERT INTO user
(id, username, email, password_hash, oauthId, birth, gender, created_at, last_login, role, is_active, preferences)
VALUES (null, '나건웅','gallawer@gmail.com','1234','1234','1994-10-12','F',null,null,null,true,null);


INSERT INTO subscribe
(id, user_id, subscribe_start, subscribe_end)
 VALUES (null, 1, now(), now()+month(1));

INSERT INTO subscribe
(id, user_id, subscribe_start, subscribe_end)
 VALUES (null, 2, now(), now()+month(1));

INSERT INTO review
(id, user_id, movie_id, rating, review, recommend)
 VALUES (null, 1, 1, 4.2, '감동적이었어요.', 1);

INSERT INTO review
(id, user_id, movie_id, rating, review, recommend)
VALUES (null, 1, 2, 1.5, '개노잼.', 1);

INSERT INTO review
(id, user_id, movie_id, rating, review, recommend)
VALUES (null, 2, 1, 4.5, '최고의 영화', 0);

INSERT INTO review
(id, user_id, movie_id, rating, review, recommend)
VALUES (null, 2, 2, 3.5, '그냥 그랬어요.', 0);

INSERT INTO notifys
(id, user_id, movie_id, content, url, is_read, notify_type)
 VALUES (null, 1, null, '친구신청왔습니다','add/friend',false, 'friendAdd');

INSERT INTO notifys
(id, user_id, movie_id, content, url, is_read, notify_type)
 VALUES (null, 1, 1, '새로나온 영화가 있습니다.','new/movie',false, 'movieUpdate');

INSERT INTO notifys
(id, user_id, movie_id, content, url, is_read, notify_type)
 VALUES (null, 2, 2, '새로나온 영화가 있습니다.','new/movie',false, 'movieUpdate');

INSERT INTO movies
(id, title, description, release_date, rating, genre, director, poster_url, video_url)
 VALUES (null, '어벤져스', '마블 영화 어벤져스!', '2024-04-05',4.2,'액션','몰라','/files/5801039d-8a2d-45d0-9d5e-1a62c7bf61f4_어벤져스.gif', 'https://www.youtube.com/watch?v=mck3JCl2uwQ');

INSERT INTO cards
(id, user_id, show_time, room_number, seat_number, movie_id)
 VALUES (null, 1, '2024-09-30 14:30:00', 1,'F01',1);