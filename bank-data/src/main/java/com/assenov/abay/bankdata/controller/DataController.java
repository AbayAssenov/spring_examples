package com.assenov.abay.bankdata.controller;

import com.assenov.abay.bankdata.exception.DataNotFoundException;
import com.assenov.abay.bankdata.exception.DataPersistException;
import com.assenov.abay.bankdata.message.ResponseMessage;
import com.assenov.abay.bankdata.model.dto.DataDto;
import com.assenov.abay.bankdata.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * {@code POST  /data/upload} : Upload rows data.
     *
     * @param file the plain text file.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with ResponseMessage, or throw DataPersistException.
     * @throws DataPersistException if the file incorrect.
     */
    @PostMapping("/data/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("Try upload data from file");
        return ResponseEntity.ok().body(dataService.save(file));
    }

    /**
     * {@code GET  /data/:id} : get by "id" the Data.
     *
     * @param id the id of the Data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Data, or with status {@code 404 (Not Found)}.
     * @throws DataNotFoundException if not found by the id.
     */
    @GetMapping("/data/{id}")
    public ResponseEntity getDataById(@PathVariable Long id) {
        log.debug(" request to get Data by id : {}", id);
        try {
            return ResponseEntity.ok().body(dataService.getDataById(id));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }
    }

    /**
     * {@code GET  /data/:start_date/:end_date} : get list Data by two date.
     *
     * @param fromDate begin date.
     * @param toDate   end date.
     * @return the {@link List<DataDto>} with status {@code 200 (OK)} and with body list of Data, or empty list if found nothing.
     */
    @GetMapping("/data/{start_date}/{end_date}")
    public List<DataDto> getDataBetween(@PathVariable(value = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate, @PathVariable(value = "end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        log.debug(" request to get Data by range of two dates : ", fromDate + "/" + toDate);
        return dataService.getListByDateRange(
                LocalDateTime.of(fromDate.getYear(), fromDate.getMonth(), fromDate.getDayOfMonth(), 0, 0),
                LocalDateTime.of(toDate.getYear(), toDate.getMonth(), toDate.getDayOfMonth(), 0, 0));
    }

    /**
     * {@code DELETE  /data/:id} : delete the "id" Data.
     *
     * @param id the id of the Data to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data/{id}")
    public ResponseEntity<Void> deleteDataById(@PathVariable Long id) {
        log.debug(" request to delete Data by id : {}", id);
        dataService.physicalDeleteById(id);
        return ResponseEntity.noContent().build();
    }
}
