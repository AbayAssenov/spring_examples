package com.assenov.abay.bankdata.service.impl;

import com.assenov.abay.bankdata.exception.DataNotFoundException;
import com.assenov.abay.bankdata.exception.DataPersistException;
import com.assenov.abay.bankdata.message.ResponseMessage;
import com.assenov.abay.bankdata.model.DataEntity;
import com.assenov.abay.bankdata.model.dto.DataDto;
import com.assenov.abay.bankdata.repository.DataRepository;
import com.assenov.abay.bankdata.service.DataService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseMessage save(MultipartFile file) {
        // validate file
        if (file.isEmpty()) {
            throw new DataPersistException("Please select a CSV file to upload.");
        } else {

            // parse CSV file to create a list of Data objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<DataDto> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(DataDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of data
                List<DataDto> dataDtos = csvToBean.parse();

                dataRepository.saveAll(dataDtos.stream().map(this::convertToEntity).collect(Collectors.toList()));

                return new ResponseMessage("Data has been successfully uploaded.");
            } catch (Exception ex) {
                throw new DataPersistException("An error occurred while processing the CSV file.", ex);
            }

        }
    }

    @Override
    public DataDto getDataById(Long id) {

        Optional<DataEntity> optionalDataEntity = dataRepository.findById(id);

        if (optionalDataEntity.isPresent()) {
            return convertToDto(optionalDataEntity.get());
        }

        throw new DataNotFoundException("Data with id " + id + " not found , please check id and try again");
    }

    @Override
    public List<DataDto> getListByDateRange(LocalDateTime start, LocalDateTime end) {

        return dataRepository.findAllByUpdatedTimestampAfterAndUpdatedTimestampBefore(start,end)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void physicalDeleteById(Long id) {
        dataRepository.deleteById(id);
    }

    private DataDto convertToDto(DataEntity entity) {
        DataDto dto = modelMapper.map(entity, DataDto.class);
        return dto;
    }

    private DataEntity convertToEntity(DataDto dto) {
        DataEntity entity = modelMapper.map(dto, DataEntity.class);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        entity.setUpdatedTimestamp(LocalDateTime.parse(dto.getUpdatedTimestamp(), inputFormatter));
        return entity;
    }

}
