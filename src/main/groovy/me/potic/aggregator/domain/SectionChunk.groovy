package me.potic.aggregator.domain

import groovy.transform.builder.Builder

@Builder
class SectionChunk {

    String id

    List<Article> articles

    String nextChunkId
}