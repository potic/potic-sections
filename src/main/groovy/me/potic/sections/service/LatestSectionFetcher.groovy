package me.potic.sections.service

import groovy.util.logging.Slf4j
import me.potic.sections.SectionFetcher
import me.potic.sections.domain.Article
import me.potic.sections.domain.Section
import me.potic.sections.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class LatestSectionFetcher implements SectionFetcher {

    static final Section SECTION = new Section(id: 'latest', title: 'latest', priority: 2)

    @Autowired
    ArticlesService articlesService

    @Override
    Section section() {
        SECTION
    }

    @Override
    List<Article> fetch(User user, Map fetchCardsRequest) {
        log.debug "fetching cards with latest articles for user ${user} with request ${fetchCardsRequest}"

        try {
            List<String> skipIds = fetchCardsRequest.skipIds
            Integer count = fetchCardsRequest.count
            List<Article> articles = articlesService.getLatestUnreadArticles(user, skipIds, count)
            return articles
        } catch (e) {
            log.error "fetching cards with latest articles for user ${user} with request ${fetchCardsRequest} failed: $e.message", e
            throw new RuntimeException("fetching cards with latest articles for user ${user} with request ${fetchCardsRequest} failed", e)
        }
    }
}
