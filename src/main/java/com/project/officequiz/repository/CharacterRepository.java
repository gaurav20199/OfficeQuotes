package com.project.officequiz.repository;

import com.project.officequiz.entity.OfficeCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CharacterRepository extends JpaRepository<OfficeCharacter,Integer> {

    @Query("SELECT c.characterName FROM OfficeCharacter c")
    List<String> findAllCharacterNames();

}
