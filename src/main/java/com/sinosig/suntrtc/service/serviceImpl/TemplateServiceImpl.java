package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private static Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);
}
