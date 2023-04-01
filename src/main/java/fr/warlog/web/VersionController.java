package fr.warlog.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class VersionController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> version() {
        var versions = new HashMap<String,String>();
        versions.put("version",getClass().getPackage().getImplementationVersion());
        versions.put("timestamp",getClass().getPackage().getSpecificationVersion());
        versions.put("spring.version",RestController.class.getPackage().getImplementationVersion());
        logger.info("Versions:{}" ,versions);
        return versions;
    }
}