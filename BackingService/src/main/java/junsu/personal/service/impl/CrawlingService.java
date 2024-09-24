package junsu.personal.service.impl;

import jakarta.annotation.PostConstruct;
import junsu.personal.dto.object.CrawlingDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.crawling.GetCrawlingResponseDTO;
import junsu.personal.persistance.IMyRedisMapper;
import junsu.personal.service.ICrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlingService implements ICrawlingService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final IMyRedisMapper redisMapper;


    //    @Scheduled(fixedRate = 150 * 60 * 60 * 1000)
//    @Scheduled(fixedRate = 540000000) // 150 시간마다 실행
//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    @Scheduled(fixedRate = 86400000) // 24시간 마다 실행
    public void scheduleCrawling() {
        try {
            log.info("스케줄링 실행됨");
            getCrawling();
        } catch (Exception e) {
            log.error("Error during scheduled crawling", e);
        }
    }

//    @Scheduled(fixedRate = 60 * 3 * 1000)
//    public void scheduleTest(){
//        try{
//            log.info("스케줄링 실행됨");
//            getCrawling();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    public int getCrawling() throws Exception {
        String temp = "http://www.weschoolnews.com/?r=s120810&c=11&p=";
        int page = 1;
        boolean hasMoreData = true;
        List<CrawlingDTO> result = new ArrayList<>();
        int res = 1;
        while (hasMoreData) {
            String crawlingUrl = temp + page;
            Document document = Jsoup.connect(crawlingUrl).get();
            Elements contents = document.select(".default_thumb");

            for (Element content : contents) {
                try {
                    // 기사 제목
                    String subject = content.select("div.st a").text();

                    // 기사 URL
                    String contentUrl = content.select("div.st a").attr("href");

                    // 이미지 URL (background-image 속성에서 추출)
                    String imgStyle = content.select("div.thumb").attr("style");
                    String imgUrl = "";
                    int urlStartIndex = imgStyle.indexOf("url(");
                    int urlEndIndex = imgStyle.indexOf(")");

                    if (urlStartIndex != -1 && urlEndIndex != -1 && urlEndIndex > urlStartIndex) {
                        imgUrl = imgStyle.substring(urlStartIndex + 4, urlEndIndex).replace("'", "");
                    }

                    // 기사 내용 요약
                    String contentText = content.select("div.cont a").text();

                    // 작성자 및 날짜 추출
                    String writeInfo = content.select("div.ninfo").text();
                    String[] infoParts = writeInfo.split(" ");

                    if (infoParts.length > 0) {
                        String date = infoParts[0];
                        if (!date.isEmpty()) {
                            try {
                                LocalDate articleDate = LocalDate.parse(date, formatter);
                                LocalDate today = LocalDate.now();
                                // 7일 이상 지난 기사 제외
                                if (articleDate.isBefore(today.minusDays(7))) {
                                    hasMoreData = false;
                                    break;
                                }
                                log.info("기사 제목 : " + subject + "\n기사 내용 : " + contentText + "\n작성 날짜 : " + date + "\n작성자 : " + infoParts[1] + "이미지 : " + imgUrl + "url : " + contentUrl);
                            } catch (DateTimeParseException e) {
                                log.warn("Invalid date format for article: " + date);
                                continue;  // 날짜 파싱 실패 시 해당 기사를 건너뜀
                            }
                        } else {
                            log.warn("Date is empty for article: " + subject);

                            continue;  // 날짜가 비어있을 때 해당 기사를 건너뜀
                        }

                        String writer = infoParts.length > 1 ? infoParts[infoParts.length - 1] : "Unknown";

                        // CrawlingDTO에 담기
                        CrawlingDTO dto = CrawlingDTO.builder()
                                .subject(subject)
                                .img(imgUrl)
                                .url(contentUrl)
                                .contents(contentText)
                                .date(date)
                                .writer(writer)
                                .build();

                        result.add(dto);
                    } else {
                        log.warn("No writeInfo found for article: " + subject);
                    }
                } catch (Exception e) {
                    log.error("Error processing article on page " + page, e);
                    res = 0;
                }
            }

            page++;
        }

        redisMapper.saveCrawling("crawling", result);
        return res;
    }

    @Override
    public ResponseEntity<? super GetCrawlingResponseDTO> getNewsList() {
        String redisKey = "crawling";
        List<CrawlingDTO> list = new ArrayList<>();
        try {
            list = redisMapper.getCrawling(redisKey);
            if (list == null || list.isEmpty()) {
                log.info("No crawling data found in Redis for key: " + redisKey);
                return GetCrawlingResponseDTO.success(list);
            }
        } catch (Exception e) {
            log.error("Error fetching crawling data from Redis", e);
            return ResponseDTO.databaseError();  // 에러 응답
        }

        return GetCrawlingResponseDTO.success(list);
    }

}
