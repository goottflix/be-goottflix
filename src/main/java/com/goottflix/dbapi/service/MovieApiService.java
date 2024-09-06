package com.goottflix.dbapi.service;

import com.goottflix.dbapi.entity.ApiMovie;
import com.goottflix.dbapi.entity.repository.ApiMovieRepository;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieApiService {

    private static final String API_KEY = "babbcac0f7d4494cb545e8933ecbf621";

    private final ApiMovieRepository movieRepository;

    public void fetchMoviesUsingMap() {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);

        try {
            // 파라미터 Map 생성
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("curPage", "1");
            paramMap.put("itemPerPage", "10");
            paramMap.put("movieNm", ""); // 영화 이름 검색 필터
            paramMap.put("directorNm", ""); // 감독 이름 검색 필터
            paramMap.put("openStartDt", "20230101"); // 개봉 시작일 < 개봉 시작일 ~ 종료일 지정하면 해당 영화들 가져오는거임 >
            paramMap.put("openEndDt", "20230131"); // 개봉 종료일
            paramMap.put("prdtStartYear", ""); // 제작 시작 연도
            paramMap.put("prdtEndYear", ""); // 제작 종료 연도
            paramMap.put("repNationCd", ""); // 국가 코드

            // API 호출 (JSON 형식으로 요청)
            String movieList = service.getMovieList(true, paramMap);

            // 출력
            System.out.println(movieList); // JSON 형식의 영화 목록 출력

        } catch (OpenAPIFault e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchAndSaveMovies() {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("curPage", "1");
            paramMap.put("itemPerPage", "100");
            paramMap.put("movieNm", ""); // 영화 이름 검색 필터
            paramMap.put("directorNm", ""); // 감독 이름 검색 필터
            paramMap.put("openStartDt", "2023"); // 개봉 시작일
            paramMap.put("openEndDt", "2023"); // 개봉 종료일
            paramMap.put("prdtStartYear", ""); // 제작 시작 연도
            paramMap.put("prdtEndYear", ""); // 제작 종료 연도
            paramMap.put("repNationCd", ""); // 국가 코드

            // API 호출 (JSON 형식으로 요청)
            String response = service.getMovieList(true, paramMap);

            // 응답을 로그로 출력하여 확인
            System.out.println("API Response: " + response);

            // JSON 파싱 - movieListResult가 있는지 먼저 확인
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.has("movieListResult")) {
                JSONObject movieListResult = jsonResponse.getJSONObject("movieListResult");

                // movieList가 있는지 확인 후 처리
                if (movieListResult.has("movieList")) {
                    JSONArray movieList = movieListResult.getJSONArray("movieList");

                    // 각 영화 데이터를 DB에 저장
                    for (int i = 0; i < movieList.length(); i++) {
                        JSONObject movieJson = movieList.getJSONObject(i);

                        // Movie 엔티티 생성
                        ApiMovie movie = new ApiMovie();
                        movie.setMovieName(movieJson.getString("movieNm")); // 영화 이름
                        movie.setDirector(movieJson.has("directors") && movieJson.getJSONArray("directors").length() > 0
                                ? movieJson.getJSONArray("directors").getJSONObject(0).getString("peopleNm")
                                : ""); // 감독 이름 (없을 경우 공백 처리)
                        movie.setOpenDt(movieJson.getString("openDt")); // 개봉일
                        movie.setPrdtYear(movieJson.getString("prdtYear")); // 제작 연도
                        movie.setNationAlt(movieJson.getString("nationAlt")); // 국가 정보
                        movie.setMovieCd(movieJson.getString("movieCd"));
                        movie.setGenre(movieJson.getString("genreAlt"));
                        movie.setRepGenre(movieJson.getString("repGenreNm"));

                        // DB에 저장
                        movieRepository.saveApiMovies(movie);
                    }
                } else {
                    System.out.println("movieList가 응답에 없습니다.");
                }
            } else {
                System.out.println("movieListResult가 응답에 없습니다.");
            }

        } catch (OpenAPIFault e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchAndSaveWeeklyBoxOffice() {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);

        try {
            // 주간 박스오피스 API 호출에 필요한 파라미터 설정
            String targetDt = "20230801"; // 조회할 기준 날짜 (YYYYMMDD)
            String itemPerPage = "10";    // 페이지 당 항목 수
            String weekGb = "0";          // 0: 주간, 1: 주말(금~일), 2: 주중(월~목)
            String multiMovieYn = "Y";    // 복수 영화 여부
            String repNationCd = "";      // 국가 코드
            String wideAreaCd = "";       // 광역 지역 코드

            // API 호출
            String response = service.getWeeklyBoxOffice(true, targetDt, itemPerPage, weekGb, multiMovieYn, repNationCd, wideAreaCd);

            // 응답을 로그로 출력하여 확인
            System.out.println("API Response: " + response);

            // JSON 파싱 - boxOfficeResult가 있는지 먼저 확인
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.has("boxOfficeResult")) {
                JSONObject boxOfficeResult = jsonResponse.getJSONObject("boxOfficeResult");

                // weeklyBoxOfficeList가 있는지 확인 후 처리
                if (boxOfficeResult.has("weeklyBoxOfficeList")) {
                    JSONArray boxOfficeList = boxOfficeResult.getJSONArray("weeklyBoxOfficeList");

                    // 각 박스오피스 데이터를 DB에 저장
                    for (int i = 0; i < boxOfficeList.length(); i++) {
                        JSONObject movieJson = boxOfficeList.getJSONObject(i);

                        // Movie 엔티티 생성
                        ApiMovie movie = new ApiMovie();
                        movie.setMovieName(movieJson.getString("movieNm")); // 영화 이름
                        movie.setDirector(movieJson.has("directors") && movieJson.getJSONArray("directors").length() > 0
                                ? movieJson.getJSONArray("directors").getJSONObject(0).getString("peopleNm")
                                : ""); // 감독 이름 (없을 경우 공백 처리)
                        movie.setOpenDt(movieJson.getString("openDt")); // 개봉일
                        movie.setMovieCd(movieJson.getString("movieCd")); // 영화코드

                        // DB에 저장

                        movieRepository.saveApiMovies(movie);
                    }
                } else {
                    System.out.println("weeklyBoxOfficeList가 응답에 없습니다.");
                }
            } else {
                System.out.println("boxOfficeResult가 응답에 없습니다.");
            }

        } catch (OpenAPIFault e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchAndSaveWeeklyBoxOffice2(String targetDt) {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);

        try {
            // 주간 박스오피스 API 호출에 필요한 파라미터 설정
            String itemPerPage = "3";    // 페이지 당 항목 수 ( 날짜기준으로 top 3개만 가져옴 )
            String weekGb = "0";          // 0: 주간, 1: 주말(금~일), 2: 주중(월~목)
            String multiMovieYn = "Y";    // 복수 영화 여부
            String repNationCd = "";      // 국가 코드
            String wideAreaCd = "";       // 광역 지역 코드
            System.out.println("targetDt = " + targetDt);

            // 주간 박스오피스 API 호출
            String boxOfficeResponse = service.getWeeklyBoxOffice(true, targetDt, itemPerPage, weekGb, multiMovieYn, repNationCd, wideAreaCd);
            System.out.println("Box Office API Response: " + boxOfficeResponse);

            // JSON 파싱 - boxOfficeResult가 있는지 확인
            JSONObject boxOfficeJson = new JSONObject(boxOfficeResponse);
            if (!boxOfficeJson.has("boxOfficeResult")) {
                System.out.println("boxOfficeResult가 응답에 없습니다.");
                return;
            }

            JSONObject boxOfficeResult = boxOfficeJson.getJSONObject("boxOfficeResult");
            if (!boxOfficeResult.has("weeklyBoxOfficeList")) {
                System.out.println("weeklyBoxOfficeList가 응답에 없습니다.");
                return;
            }

            // 2023년 개봉한 영화 목록 가져오기
            String movieListResponse = fetch2023Movies(service);
            if (movieListResponse == null) {
                System.out.println("2023년 개봉한 영화 목록을 가져오는 데 실패했습니다.");
                return;
            }

            // 2023년 개봉 영화 목록 파싱
            List<ApiMovie> movies2023 = parseMovieList(movieListResponse);

            // 주간 박스오피스 데이터 파싱
            JSONArray boxOfficeList = boxOfficeResult.getJSONArray("weeklyBoxOfficeList");
            System.out.println("boxOfficeList = " + boxOfficeList);

// 주간 박스오피스와 2023년 개봉 영화 목록 비교 및 저장
            for (int i = 0; i < boxOfficeList.length(); i++) {
                JSONObject boxOfficeMovie = boxOfficeList.getJSONObject(i);
                String boxOfficeMovieCd = boxOfficeMovie.getString("movieCd").trim();

                // 영화 목록에서 MovieCd와 매칭되는 영화 찾기 (디버깅용 출력 포함)
                ApiMovie matchedMovie = null;
                for (ApiMovie movie : movies2023) {
                    System.out.println("Comparing BoxOffice MovieCd: " + boxOfficeMovieCd + " with 2023 MovieCd: " + movie.getMovieCd().trim());
                    if (movie.getMovieCd().trim().equalsIgnoreCase(boxOfficeMovieCd)) {
                        System.out.println("Match found for MovieCd: " + boxOfficeMovieCd);
                        matchedMovie = movie;
                        break;
                    }
                }

                if (matchedMovie != null) {
                    // 주간 박스오피스 데이터로 추가 정보를 채워 저장
                    matchedMovie.setMovieName(boxOfficeMovie.getString("movieNm"));
                    matchedMovie.setOpenDt(boxOfficeMovie.getString("openDt"));
                    movieRepository.saveApiMovies(matchedMovie);
                    System.out.println("저장된 영화: " + matchedMovie.getMovieName());
                } else {
                    System.out.println("No match found for MovieCd: " + boxOfficeMovieCd);
                }
            }

        } catch (OpenAPIFault e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2023년 개봉 영화 목록을 가져오는 메서드
    private String fetch2023Movies(KobisOpenAPIRestService service) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("curPage", "1");
            paramMap.put("itemPerPage", "10000");
            paramMap.put("openStartDt", "2023");
            paramMap.put("openEndDt", "2023");

            // API 호출
            return service.getMovieList(true, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2023년 개봉 영화 목록을 파싱하는 메서드
    private List<ApiMovie> parseMovieList(String response) {
        List<ApiMovie> movieList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if (!jsonResponse.has("movieListResult")) {
                return movieList;
            }

            JSONObject movieListResult = jsonResponse.getJSONObject("movieListResult");
            if (!movieListResult.has("movieList")) {
                return movieList;
            }

            JSONArray movies = movieListResult.getJSONArray("movieList");
            for (int i = 0; i < movies.length(); i++) {
                JSONObject movieJson = movies.getJSONObject(i);
                ApiMovie movie = new ApiMovie();
                movie.setMovieName(movieJson.getString("movieNm"));
                movie.setDirector(movieJson.has("directors") && movieJson.getJSONArray("directors").length() > 0
                        ? movieJson.getJSONArray("directors").getJSONObject(0).getString("peopleNm")
                        : "");
                movie.setOpenDt(movieJson.getString("openDt"));
                movie.setPrdtYear(movieJson.getString("prdtYear"));
                movie.setNationAlt(movieJson.getString("nationAlt"));
                movie.setMovieCd(movieJson.getString("movieCd"));
                movie.setGenre(movieJson.getString("genreAlt"));
                movie.setRepGenre(movieJson.getString("repGenreNm"));

                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList;
    }

}



