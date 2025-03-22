package com.project.officequiz.utils.mappers;

import com.project.officequiz.entity.OfficeCharacter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class CharacterMapper implements Function<Row, Optional<OfficeCharacter>> {

    @Override
    public Optional<OfficeCharacter> apply(Row row) {
        String realName = row.getCell(0).getStringCellValue();
        String characterName = row.getCell(1).getStringCellValue();
        short numberOfEpisodes = (short) row.getCell(2).getNumericCellValue();
        String year = row.getCell(3).getStringCellValue();
        return Optional.of(OfficeCharacter.builder().
                characterName(characterName).
                realName(realName).
                yearRange(year).
                numberOfEpisodes(numberOfEpisodes).
                build());
    }
}
