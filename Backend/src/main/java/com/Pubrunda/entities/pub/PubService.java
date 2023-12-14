package com.Pubrunda.entities.pub;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.entities.pub.dto.request.CreatePubDTO;
import com.Pubrunda.entities.pub.dto.request.PubQueryParams;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.exception.AuthorizationException;
import com.Pubrunda.exception.MissingRequiredAttributeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PubService {

    private final PubRepository pubRepository;

    public Pub getPubById(long pubId) {
        return pubRepository.findById(pubId).orElseThrow();
    }

    public List<Pub> getAllPubs() {
        return pubRepository.findAll();
    }

    public List<Pub> getAllPubs(PubQueryParams params) {
        PubSpecifications pubSpecifications = new PubSpecifications(params);
        return pubRepository.findAll(pubSpecifications);
    }

    public Pub createPub(User authenticatedUser, CreatePubDTO newPub) {
        try {
            Pub pub = Pub.builder()
                    .name(newPub.getName())
                    .openingTime(newPub.getOpeningTime())
                    .closingTime(newPub.getClosingTime())
                    .lastUpdatedTime(LocalDateTime.now())
                    .build();

            return pubRepository.save(pub);
        } catch(NullPointerException e) {
            throw new MissingRequiredAttributeException();
        }
    }

    public Pub updatePub(User authenticatedUser, CreatePubDTO newPub, long pubId) {
        Pub existingPub = pubRepository.findById(pubId).orElseThrow();

        existingPub.setName(newPub.getName());
        existingPub.setOpeningTime(newPub.getOpeningTime());
        existingPub.setClosingTime(newPub.getClosingTime());
        existingPub.setLastUpdatedTime(LocalDateTime.now());

        return pubRepository.save(existingPub);
    }


    public void deletePub(User authenticatedUser, long pubId) {
        User existingOwner = pubRepository.findById(pubId).orElseThrow().getOwner();

        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, existingOwner)) {
            throw new AuthorizationException("You are not allowed to delete this pub");
        }

        pubRepository.deleteById(pubId);
    }
}
