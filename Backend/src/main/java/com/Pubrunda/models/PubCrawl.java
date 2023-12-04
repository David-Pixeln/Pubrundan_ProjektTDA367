package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PubCrawl {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    private String name;

    @NonNull
    @OneToMany
    @JoinColumn(name = "pub_id")
    private ArrayList<Pub> pubs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    private static int compareTimesFromNoon(LocalTime time1, LocalTime time2) {
        int minutes1 = time1.getHour() * 60 + time1.getMinute();
        int minutes2 = time2.getHour() * 60 + time2.getMinute();

        return Integer.compare(minutes1, minutes2);
    }

    public LocalTime getEarliestOpeningTime() {
        LocalTime earliest = pubs.get(0).getOpeningTime();

        for (Pub pub : pubs) {
            if (pub.getOpeningTime().isBefore(earliest)) {
                earliest = pub.getOpeningTime();
            }
        }
        return earliest;
    }

    public LocalTime getLatestClosingTime() {
        LocalTime latest = pubs.get(0).getClosingTime();

        for (Pub pub : pubs) {
            LocalTime pubTime = pub.getOpeningTime();
            int result = compareTimesFromNoon(pubTime, latest);
            if (result > 0) {
                latest = pub.getClosingTime();
            }
        }

        return latest;
    }

}