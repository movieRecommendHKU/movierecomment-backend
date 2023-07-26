package com.project.movie.domain.flask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Flask {
    public static String USER_SEARCH;
    public static String KEYWORDS_SEARCH;
    public static String SENTENCES_SEARCH;

    @Value("${flask.function.user}")
    public void setUserSearch(String userSearch) {
        USER_SEARCH = userSearch;
    }

    @Value("${flask.function.keywords}")
    public void setKeywordsSearch(String keywordsSearch) {
        KEYWORDS_SEARCH = keywordsSearch;
    }

    @Value("${flask.function.sentences}")
    public void setSentencesSearch(String sentencesSearch) {
        SENTENCES_SEARCH = sentencesSearch;
    }
}
