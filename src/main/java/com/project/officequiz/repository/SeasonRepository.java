package com.project.officequiz.repository;

import com.project.officequiz.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season,Short> {
}
