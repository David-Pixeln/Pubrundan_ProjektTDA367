package com.Pubrunda.entities.pub;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.pub.dto.request.CreatePubDTO;
import com.Pubrunda.entities.pub.dto.request.PubQueryParams;
import com.Pubrunda.entities.pub.dto.response.PubDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pubs")
@RequiredArgsConstructor
public class PubController {

    private final PubService pubService;

    // READ
    @GetMapping("/{pubId}")
    public PubDTO getPubById(@PathVariable long pubId) {
        return DTOMapper.convertToDto(pubService.getPubById(pubId), PubDTO.class);
    }

    @GetMapping
    public List<PubDTO> getAllPubs(PubQueryParams params) {
        return DTOMapper.convertToDto(pubService.getAllPubs(params), PubDTO.class);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PubDTO createPub(@AuthenticationPrincipal User authenticatedUser, @RequestBody CreatePubDTO newPub) {
        return DTOMapper.convertToDto(pubService.createPub(authenticatedUser, newPub), PubDTO.class);
    }

    // UPDATE
    @PutMapping("/{pubId}")
    public PubDTO updatePub(@AuthenticationPrincipal User authenticatedUser, @RequestBody CreatePubDTO newPub, @PathVariable long pubId) {
        return DTOMapper.convertToDto(pubService.updatePub(authenticatedUser, newPub, pubId), PubDTO.class);
    }

    // DELETE
    @DeleteMapping("/{pubId}")
    public MessageResponse deletePub(@AuthenticationPrincipal User authenticatedUser, @PathVariable long pubId) {
        pubService.deletePub(authenticatedUser, pubId);
        return new MessageResponse("Pub Deleted Successfully");
    }

}
