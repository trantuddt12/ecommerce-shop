package com.ttl.base;

import com.ttl.base.sample.SampleData;
import jakarta.annotation.Resource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitialDataSetup {

    @Resource
    private List<SampleData> sampleDataCreators;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent(ApplicationReadyEvent pEvent) {
//        if (!CollectionUtils.isEmpty(sampleDataCreators)) {
//            sampleDataCreators.forEach((service) -> {
//                service.create
//            });
//        }
    }
}
