package com.assenov.abay.bankdata.service;

import com.assenov.abay.bankdata.message.ResponseMessage;
import com.assenov.abay.bankdata.model.dto.DataDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface DataService {

    ResponseMessage save(MultipartFile file);

    DataDto getDataById(Long id);

    public List<DataDto> getListByDateRange(LocalDateTime start, LocalDateTime end);

    void physicalDeleteById(Long id);
}
