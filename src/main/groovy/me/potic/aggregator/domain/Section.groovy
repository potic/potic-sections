package me.potic.aggregator.domain

import groovy.transform.builder.Builder

@Builder
class Section {

    String name

    List<Article> articles
}