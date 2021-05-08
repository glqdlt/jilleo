package com.glqdlt.myhome.jilleo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Scheduled(fixedDelay = 120000)
    public void schemedule(){
        LOGGER.debug("JOB START");
        List<UrlEntity> entry = urlEntityRepo.findAll();

        for(UrlEntity u : entry){
            Optional<Crawller> zxc = crawllerList.stream().filter(x -> x.isSupported(u)).findFirst();
            zxc.ifPresent(value -> executorService.submit(() -> {
                Log log = zxc.get().crawlling(u);
                ParserLogEntity logEntity = log.convertEntity(u);
                parserLogEntityRepo.saveAndFlush(logEntity);
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
