package com.Pubrunda.entities.friendList;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.friendList.dto.request.FriendListQueryParams;
import com.Pubrunda.entities.friendList.dto.response.FriendListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/friendLists")
@RequiredArgsConstructor
public class FriendListController {

    private final FriendListService friendListService;

    // READ
    @GetMapping("/{friendListId}")
    public FriendListDTO getFriendListById(@PathVariable long friendListId) {
        return DTOMapper.convertToDto(friendListService.getFriendList(friendListId), FriendListDTO.class);
    }

    @GetMapping
    public List<FriendListDTO> getAllFriendLists(FriendListQueryParams params) {
        return DTOMapper.convertToDto(friendListService.getAllFriendLists(params), FriendListDTO.class);
    }

}
