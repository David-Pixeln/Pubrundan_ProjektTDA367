package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.Lazy;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CompletedPubCrawl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pub_crawl_id")
    private PubCrawl pubcrawl;

    @NonNull
    @OneToMany
    @JoinColumn(name = "completed_pub_id")
    private List<CompletedPub> completedPubList;

    @NonNull
    private LocalDateTime completedAt;

}