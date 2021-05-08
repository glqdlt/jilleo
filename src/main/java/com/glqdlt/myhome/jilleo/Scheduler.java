package com.glqdlt.myhome.jilleo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author glqdlt
 */
@Component
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private ParserLogEntityRepo parserLogEntityRepo;

    @Autowired
    private UrlEntityRepo urlEntityRepo;

    @Autowired
    private List<Crawller> crawllerList;

    @Autowired
    private LowestPriceQueueEntityRepo lowestPriceQueueEntityRepo;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Scheduled(fixedDelay = 120000)
    public void schemedule(){
        LOGGER.debug("JOB START");
        List<UrlEntity> entry = urlEntityRepo.findAll();

        for(UrlEntity u : entry){
            Optional<Crawller> zxc = crawllerList.stream().filter(x -> x.isSupported(u)).findFirst();
            zxc.ifPresent(value -> executorService.submit(() -> {
                Log log = zxc.get().crawlling(u);
                ParserLogEntity logEntity = log.convertEntity(u);
                ParserLogEntity s = parserLogEntityRepo.saveAndFlush(logEntity);
                Page<ParserLogEntity> source = parserLogEntityRepo.findAllByUrlEntity_IdAndSeqLessThanOrderBySeqDesc(s.getUrlEntity().getId(), s.getSeq(), PageRequest.of(0, 1));
                List<LowestPriceQueueEntity> report = source.getContent().stream().map(x -> {
                    LowestPriceQueueEntity l = new LowestPriceQueueEntity();
                    try {
                        s.comapreToUpdated(x);
                    } catch (UpdatePriceException e) {
                        l.setSummary(e.getMessage());
                        l.setCursor(e.getCursor());
                        l.setSource(e.getSource());
                        l.setRegDate(LocalDateTime.now());
                        l.setSubmit(false);
                        l.setUrlEntity(logEntity.getUrlEntity());
                    }
                    return l;
                }).filter(x -> x.getSubmit() != null)
                        .collect(Collectors.toList());

                if(report.size() > 0){
                    lowestPriceQueueEntityRepo.saveAll(report);
                }

            }));
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(new Random().nextInt(20)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
        LOGGER.debug("JOB END");
    }
}
