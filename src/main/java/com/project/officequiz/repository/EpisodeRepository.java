package com.project.officequiz.repository;

import com.project.officequiz.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode,Integer> {
}
