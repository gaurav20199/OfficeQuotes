package com.project.officequiz.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ExcelImportService<T> {

    private final JpaRepository<T, ?> repository;
    private final Function<Row, Optional<T>> rowMapper;
    private final int batchSize = 500;

    public ExcelImportService(JpaRepository<T, ?> repository, Function<Row, Optional<T>> rowMapper) {
        this.repository = repository;
        this.rowMapper = rowMapper;
    }

    @Transactional
    public void importData(String filePath) throws IOException {
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<T> entities = new ArrayList<>();

            for (Row row : sheet) {
                // Skipping headers
                if (row.getRowNum() == 0) continue;

                Optional<T> entity = rowMapper.apply(row);
                if(entity.isEmpty())
                    continue;

                entities.add(entity.get());

                if (entities.size() == batchSize) {
                    repository.saveAll(entities);
                    entities.clear();
                }
            }

            if (!entities.isEmpty()) {
                repository.saveAll(entities);
            }
        }
    }

}
