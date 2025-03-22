package com.project.officequiz;


import com.project.officequiz.entity.OfficeCharacter;
import com.project.officequiz.entity.Question;
import com.project.officequiz.repository.EpisodeRepository;
import com.project.officequiz.repository.SeasonRepository;
import com.project.officequiz.service.ExcelImportService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DBInitializer implements CommandLineRunner {

    private final RestTemplate restTemplate;

    private final SeasonRepository seasonRepository;

    private final EpisodeRepository episodeRepository;

    private final ExcelImportService<Question> questionImportService;
    private final ExcelImportService<OfficeCharacter> characterImportService;

    public DBInitializer(RestTemplate restTemplate, SeasonRepository seasonRepository, EpisodeRepository episodeRepository,
                         @Qualifier("questionImportService") ExcelImportService<Question> questionImportService,
                         @Qualifier("characterImportService") ExcelImportService<OfficeCharacter> characterImportService) {
        this.restTemplate = restTemplate;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
        this.questionImportService = questionImportService;
        this.characterImportService = characterImportService;
    }

    @Override
    public void run(String... args) throws Exception {
        /*

        setting up db data for season and episodes

        for(short season=1;season<=9;season++) {
            EpisodeDTO[] episodeDTOArr = restTemplate.getForObject("https://officeapi.akashrajpurohit.com/season/"+season, EpisodeDTO[].class);
            List<EpisodeDTO> episodeDTOList = Arrays.asList(episodeDTOArr);
            Set<Episode> episodes = new HashSet<>();
            Date date = episodeDTOList.stream().map(episodeDTO -> episodeDTO.releaseDate()).findFirst().get();
            double avgRating = episodeDTOList.stream().mapToDouble(episodeDTO -> episodeDTO.imdbRating()).average().getAsDouble();
            Season currSeason = ConversionUtil.convertToSeason(season, avgRating, date);
            episodeDTOList.stream().forEach(episodeDTO -> {
                Episode episode = ConversionUtil.convertToEpisode(episodeDTO);
                episodes.add(episode);
                episode.setSeason(currSeason);
            });
            currSeason.setEpisodes(episodes);
            seasonRepository.save(currSeason);
        }

     */

       // setting up db data for character information
        //characterImportService.importData("/db/data/CastList.xlsx");
        //questionImportService.importData("/db/data/office-quotes-dataset.xlsx");

    }
}
