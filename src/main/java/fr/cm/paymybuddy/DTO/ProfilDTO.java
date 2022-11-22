package fr.cm.paymybuddy.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProfilDTO extends UserDTO{

    private List<FriendDTO> listFriends;
}
