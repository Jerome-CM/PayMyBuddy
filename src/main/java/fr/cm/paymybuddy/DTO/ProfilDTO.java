package fr.cm.paymybuddy.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProfilDTO {

    private String mail;
    private double accountBalance;
    private List<FriendDTO> listFriends;
}
