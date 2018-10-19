package com.zen.lab.services.program.service;

import com.zen.lab.services.program.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8080/v1/livefeed", name = "feedprocessor")
public interface LiveFeedStoreClient {
    @RequestMapping(method = RequestMethod.POST, value = "/events/{eventId}", consumes = "application/json")
    Event createEvent(@PathVariable("eventId") Long eventId, Event event);
}
