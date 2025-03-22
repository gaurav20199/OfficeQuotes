package com.project.officequiz.service;

import com.project.officequiz.entity.Episode;
import com.project.officequiz.repository.CharacterRepository;
import com.project.officequiz.repository.EpisodeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final CharacterRepository characterRepository;
    private final Map<String, Episode> episodeCache = new HashMap<>();

    private final Set<String> characterInfo = new HashSet<>();

    public EpisodeService(EpisodeRepository episodeRepository,CharacterRepository characterRepository) {
        this.episodeRepository = episodeRepository;
        this.characterRepository = characterRepository;
    }

    @PostConstruct
    private void loadData() {
        episodeRepository.findAll().forEach(episode -> {
            short seasonNum = episode.getSeason().getNumber();
            String key = seasonNum+"-"+episode.getNumber();
            episodeCache.put(key, episode);
        });
        characterInfo.addAll(characterRepository.findAllCharacterNames());
    }

    public Episode getEpisode(short seasonNumber, short episodeNumber) {
        return episodeCache.get(seasonNumber + "-" + episodeNumber);
    }

    public Set<String> fetchAllCharacters() {
        return this.characterInfo;
    }
}
