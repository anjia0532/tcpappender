package com.anjia.tcpappender.web.rest;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api")
public class TcpAppenderResource {

    private final Logger log = LoggerFactory.getLogger(TcpAppenderResource.class);
    
    @GetMapping("/tcp")
    public String getActiveProfiles(@RequestParam String str,@RequestParam int size,@RequestParam(required=false) String padStr) {
    	String leftPadStr= StringUtils.leftPad(str, size, StringUtils.defaultIfBlank(padStr, str));
    	log.info(leftPadStr);
        return MessageFormat.format("str:{0},pad size:{1,number},pad str:{2},result str:{3}", str,size,StringUtils.defaultString(padStr, str),leftPadStr);
    }
}
