package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class CompletedPubCrawl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Setter
    @OneToOne
    @JoinColumn(name = "pub_crawl_id")
    private PubCrawl pubcrawl;

    @NonNull
    @Setter
    @OneToMany
    @JoinColumn(name = "completed_pub_id")
    private List<CompletedPub> completedPubList;

    @NonNull
    @Setter
    private LocalDateTime timeStamp;
}
