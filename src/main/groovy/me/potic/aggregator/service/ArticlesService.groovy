package me.potic.aggregator.service

import groovy.util.logging.Slf4j
import groovyx.net.http.HttpBuilder
import me.potic.aggregator.domain.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class ArticlesService {

    @Autowired
    HttpBuilder articlesRest

    List<Article> retrieveUnreadArticlesOfUser(String userId, String cursorId, int count) {
        log.info "requesting $count articles from cursor $cursorId for user $userId"

        def query = [ count: count ]
        if (cursorId != null) {
            query['cursorId'] = cursorId
        }

        articlesRest.get {
            request.uri.path = "/article/byUserId/${userId}/unread"
            request.uri.query = query
        }
    }

    List<Article> retrieveUnreadLongArticlesOfUser(String userId, Integer minLength, String cursorId, int count) {
        log.info "requesting $count articles longer than $minLength from cursor $cursorId for user $userId"

        def query = [ count: count, minLength: minLength ]
        if (cursorId != null) {
            query['cursorId'] = cursorId
        }

        articlesRest.get {
            request.uri.path = "/article/byUserId/${userId}/unread"
            request.uri.query = query
        }
    }

    List<Article> retrieveUnreadShortArticlesOfUser(String userId, Integer maxLength, String cursorId, int count) {
        log.info "requesting $count articles longer than $maxLength from cursor $cursorId for user $userId"

        def query = [ count: count, maxLength: maxLength ]
        if (cursorId != null) {
            query['cursorId'] = cursorId
        }

        articlesRest.get {
            request.uri.path = "/article/byUserId/${userId}/unread"
            request.uri.query = query
        }
    }
}
