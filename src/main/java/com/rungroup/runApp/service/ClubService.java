package com.rungroup.runApp.service;

import com.rungroup.runApp.dto.ClubDto;
import com.rungroup.runApp.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();
    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(Long clubId);

    void updateClub(ClubDto club);

    void delete(Long clubId);
    List<ClubDto> searchClubs(String query);
}
