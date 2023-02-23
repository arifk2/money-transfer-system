package com.dxc.mts.api.resource;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.dto.StatementDTO;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.StatementNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.service.StatementService;
import com.dxc.mts.api.util.Utility;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/statement")
public class StatementController {

  @Autowired
  private StatementService statementService;

  @Autowired
  private MessageSource source;

  /**
   * This method is to get the top ten transaction
   * 
   * @param userId
   *          holds the information of the user id
   * @return list of transaction
   */
  @GetMapping("/top10/{userId}")
  @Operation(summary = "Api to get Top ten Transaction")
  public ResponseEntity<?> getToTenTransaction(@PathVariable Long userId) {
    List<StatementDTO> statementResponse = null;
    try {
      statementResponse = statementService.getTopTenTransaction(userId);
      if (statementResponse != null) {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(), source.getMessage("mts.success.message", null, null), statementResponse),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(), null),
            HttpStatus.BAD_REQUEST);
      }
    } catch (StatementNotFoundException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.to.from.account.not.found.message", null, null)), HttpStatus.NOT_FOUND);
    }
  }

  /**
   * This api is created to get the statement based on the date in query param, if not present show current date
   * transaction
   * 
   * @param providedDate
   *          holds the information of provided date "MM-dd-yyyy"
   * @param userId
   *          holds the information of the user id
   * @return list of transaction
   */
  @GetMapping("/month/{userId}")
  @Operation(summary = "Api to get current month or provided month(MM-dd-yyyy) Transaction")
  public ResponseEntity<?> getMonthTransaction(@RequestParam(name = "date", required = false) String providedDate, @PathVariable Long userId) {
    try {
      Date date = null;
      if (providedDate == null)
        date = new Date();
      else
        date = Utility.convertStringToDate(providedDate);

      List<StatementDTO> statementResponse = statementService.getMonthTransaction(date, userId);
      if (statementResponse != null) {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(), source.getMessage("mts.success.message", null, null), statementResponse),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(), null),
            HttpStatus.BAD_REQUEST);
      }
    } catch (StatementNotFoundException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.tx.not.found.message", null, null)), HttpStatus.NOT_FOUND);
    } catch (ParseException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.tx.invalid.request.message", null, null)), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * This api is created to get the statement based on the date in query param, if not present show current date
   * transaction "MM-dd-yyyy"
   * 
   * @param providedDate
   *          holds the information of provided date
   * @param userId
   *          holds the information of the user id
   * @return list of transaction
   */
  @GetMapping("/between/{userId}")
  @Operation(summary = "Api to get Transactions between toDate and fromDate(MM-dd-yyyy)")
  public ResponseEntity<?> getMonthTransaction(@RequestParam(name = "toDate", required = false) String toDateS,
      @RequestParam(name = "fromDate", required = false) String fromDateS, @PathVariable Long userId) {
    try {
      Date toDate = null;
      Date fromDate = null;
      if (toDateS == null)
        toDate = new Date();
      if (fromDateS == null)
        fromDate = new Date();

      if (toDateS != null && fromDateS != null) {
        toDate = Utility.convertStringToDate(toDateS);
        fromDate = Utility.convertStringToDate(fromDateS);
      }

      List<StatementDTO> statementResponse = statementService.getTransactionByDateRange(toDate, fromDate, userId);
      if (statementResponse != null) {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(), source.getMessage("mts.success.message", null, null), statementResponse),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(), null),
            HttpStatus.BAD_REQUEST);
      }
    } catch (StatementNotFoundException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.tx.not.found.message", null, null)), HttpStatus.NOT_FOUND);
    } catch (ParseException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.tx.invalid.request.message", null, null)), HttpStatus.BAD_REQUEST);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.user.not.found.message", null, null)), HttpStatus.NOT_FOUND);
    }
  }

  /**
   * This api is created to get last month Transactions based on provided lastMonth(number of back month)
   * 
   * @param lastMonth
   *          holds the information of the number of back month
   * @param userId
   *          holds the information of the user id
   * @return list of transactions
   */
  @GetMapping("/last/{userId}")
  @Operation(summary = "Api to get last month Transactions based on provided lastMonth(number of back month)")
  public ResponseEntity<?> getLastMonthTransaction(@RequestParam("lastMonth") int lastMonth, @PathVariable("userId") Long userId) {
    try {

      Date currentDate = new Date();
      List<StatementDTO> statementResponse = statementService.getLastMonthTransaction(currentDate, lastMonth, userId);
      if (statementResponse != null) {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(), source.getMessage("mts.success.message", null, null), statementResponse),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(), null),
            HttpStatus.BAD_REQUEST);
      }
    } catch (StatementNotFoundException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.tx.not.found.message", null, null)), HttpStatus.NOT_FOUND);
    } catch (ParseException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.tx.invalid.request.message", null, null)), HttpStatus.BAD_REQUEST);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
          source.getMessage("mts.user.not.found.message", null, null)), HttpStatus.NOT_FOUND);
    }
  }

}
