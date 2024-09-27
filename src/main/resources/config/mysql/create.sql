use goottflix;

DROP TABLE IF EXISTS user;
CREATE TABLE  user  (
                         id 	int auto_increment primary key	NOT NULL,
                         username 	varchar(50)	NULL,
                         email 	varchar(100)	NULL,
                         loginId 	varchar(100)	NULL,
                         password_hash 	varchar(255)	NULL,
                         oauthId 	varchar(255)	NULL,
                         birth 	date	NULL,
                         gender 	ENUM('M','F')	NULL,
                         created_at 	timestamp	NULL	DEFAULT now()	COMMENT 'timestamp는 시간 초까지',
                         last_login 	timestamp	NULL	DEFAULT now(),
                         role 	varchar(50)	NULL	DEFAULT 'ROLE_USER',
                         is_active 	boolean	NULL,
                         preferences 	json	NULL,
                         subscribe 	varchar(50)	NULL	DEFAULT 'free'	COMMENT 'free,sub,expired'
);

DROP TABLE IF EXISTS movies;
CREATE TABLE  movies  (
                          id 	int auto_increment primary key	NOT NULL,
                          title 	varchar(255)	NULL,
                          intro varchar(255) NULL COMMENT '영화 한줄설명',
                          description 	text	NULL,
                          release_date 	date	NULL	COMMENT '(2024-05-06)date형식',
                          rating 	decimal(3,2)	NULL	COMMENT '영화 평균별점 (1.0~5.0)',
                          genre 	varchar(50)	NULL	COMMENT '액션,코미디,드라마 등등',
                          director 	varchar(50)	NULL,
                          poster_url 	varchar(255)	NULL
);

DROP TABLE IF EXISTS notifys;
CREATE TABLE  notifys  (
                           id 	int auto_increment primary key	NOT NULL,
                           user_id 	int	NULL,
                           movie_id 	int	NULL,
                           content 	varchar(255)	NULL,
                           url 	varchar(255)	NULL,
                           is_read 	boolean	default false NULL,
                           notify_type 	enum('movieUpdate','friendAdd')	NULL
);
DROP TABLE IF EXISTS cards;
CREATE TABLE  cards  (
                         id 	int auto_increment primary key	NOT NULL,
                         user_id 	int	NULL,
                         show_time 	timestamp	NULL,
                         room_number 	int	NULL,
                         seat_number 	varchar(20)	NULL,
                         movie_id 	int	NULL
);

DROP TABLE IF EXISTS review;
CREATE TABLE  review  (
                          id 	int auto_increment primary key	NOT NULL,
                          user_id 	int	NULL,
                          movie_id 	int	NULL,
                          rating 	int	NULL,
                          review 	varchar(255)	NULL,
                          recommend 	int	NULL	DEFAULT 0
);

DROP TABLE IF EXISTS subscribe;
CREATE TABLE  subscribe  (
                             id 	int auto_increment primary key	NOT NULL,
                             user_id 	int	NULL,
                             subscribe_start 	timestamp	NULL,
                             subscribe_end 	timestamp	NULL
);

DROP TABLE IF EXISTS ApiMovie;
CREATE TABLE ApiMovie (
     id int auto_increment primary key	NOT NULL,
    movieName varchar(255),
    director varchar(50),
    openDt varchar(50),
    prdtYear varchar(50),
    nationAlt varchar(100),
    movieCd varchar(50),
    genre varchar(100),
    repGenre varchar(50),
    filename varchar(255),
    filepath varchar(255)

);

DROP TABLE IF EXISTS Friends;
CREATE TABLE Friends(
    id int auto_increment primary key NOT NULL,
    user_id int,
    friend_id int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

# /*채팅방 테이블*/
# DROP TABLE IF EXISTS ChatRoom;
# CREATE TABLE ChatRoom(
#     id int auto_increment primary key  NOT NULL ,
#     user1_id int,
#     user2_id int
# );
#
#
# DROP TABLE IF EXISTS ChatMessage;
# CREATE TABLE ChatMessage(
#     id int auto_increment primary key NOT NULL ,
#     room_id int,
#     sender_id int,
#     message text
# )



