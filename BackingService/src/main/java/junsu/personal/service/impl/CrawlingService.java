package junsu.personal.service.impl;

import jakarta.annotation.PostConstruct;
import junsu.personal.dto.object.CrawlingDTO;
import junsu.personal.persistance.IMyRedisMapper;
import junsu.personal.service.ICrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlingService implements ICrawlingService {
    private static final String CONTENTS = "#content-box";
    private static final String NEWS_BOX = ".default_thumb";
    private static final String SUBJECT = "div.desc > div.st";
    private static final String IMG = "div.thumb-box > div.thumb";
    private static final String CONTENT = "div.desc > div.cont > a";
    private static final String WRITE_INFO = "div.desc > div.ninfo";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private  final IMyRedisMapper redisMapper;
    
    @PostConstruct // 서버 실행 시 최초 1회
    public void awake(){
        try{
            getCrawling();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 540000000) // 150 시간마다 실행
    public void scheduleCrawling(){
        try{
            getCrawling();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public int getCrawling() throws Exception {
        String temp = "http://www.weschoolnews.com/?r=s120810&c=11&p=";
        int page = 1;
        boolean hasMoreData = true;
        List<CrawlingDTO> result = new ArrayList<>();
        int res = 1;
        while (hasMoreData){
            String crawlingUrl = temp + page;
            Document document = Jsoup.connect(crawlingUrl).get();
            Elements contents = document.select(CONTENTS);

            for(Element content : contents){
                try{
                    // 기사 제목
                    String subject = content.select(SUBJECT).text();

                    // 이미지 URL
                    String imgStyle = content.select(IMG).attr("style");
                    String imgUrl = imgStyle.substring(imgStyle.indexOf("url(")+4, imgStyle.indexOf(")")).replace("'","");

                    // 기사 URL
                    String contentUrl = content.select(SUBJECT).attr("href");

                    // 내용
                    String contentText = content.select(CONTENT).text();

                    // 작성자, 작성 날짜 추출
                    String writeInfo = content.select(WRITE_INFO).text();
                    String[] infoParts = writeInfo.split(" ");
                    String date = infoParts[0];
                    String writer = infoParts[infoParts.length - 1];

                    LocalDate articleDate = LocalDate.parse(date, formatter);

                    LocalDate today = LocalDate.now();
                    if(articleDate.isBefore(today.minusDays(7))){
                        hasMoreData = false;
                        break;
                    }

                    CrawlingDTO dto = CrawlingDTO.builder()
                            .subject(subject)
                            .img(imgUrl)
                            .url(contentUrl)
                            .contents(contentText)
                            .date(date).writer(writer)
                            .build();
                    result.add(dto);
                }catch(Exception e){
                    e.printStackTrace();
                    res = 0;
                }
            }
            page++;
        }

        redisMapper.saveCrawling("crawling", result);
        return res;
    }
}
