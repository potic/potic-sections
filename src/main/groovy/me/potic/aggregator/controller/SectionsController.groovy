package me.potic.aggregator.controller

import groovy.util.logging.Slf4j
import me.potic.aggregator.domain.Section
import me.potic.aggregator.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

import static groovyx.gpars.GParsPool.executeAsync
import static groovyx.gpars.GParsPool.withPool

@RestController
@Slf4j
class SectionsController {

    @Autowired
    TimedService timedService

    @Autowired
    LatestSectionService latestSectionService

    @Autowired
    ShortSectionService shortSectionService

    @Autowired
    LongSectionService longSectionService

    @Autowired
    UserService userService

    @CrossOrigin
    @GetMapping(path = '/user/me/section')
    @ResponseBody List<Section> userSections(final Principal principal) {
        timedService.timed '/user/me/section request', {
            String pocketSquareId = userService.fetchPocketSquareIdByAuth0Token(principal.token)

            withPool {
                executeAsync(
                        { latestSectionService.fetchSectionHead(pocketSquareId) },
                        { shortSectionService.fetchSectionHead(pocketSquareId) },
                        { longSectionService.fetchSectionHead(pocketSquareId) }
                ).collect { promiseOnSection -> promiseOnSection.get() }
            }
        }
    }
}
