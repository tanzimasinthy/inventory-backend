package com.inventory.controller;

import com.inventory.constant.HttpHeader;
import com.inventory.dto.ProductCreateDTO;
import com.inventory.dto.ResponseDTO;
import com.inventory.dto.TransactionLogCreateDTO;
import com.inventory.model.dummy.User;
import com.inventory.service.ProductService;
import com.inventory.service.TransactionLogService;
import com.inventory.utills.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionLogController {
    @Autowired
    private TransactionLogService transactionLogService;

    @RequestMapping(value = "/Transaction/create", method = RequestMethod.POST)
    public ResponseDTO create(@RequestBody TransactionLogCreateDTO input, @RequestHeader(value = HttpHeader.REQUESTER) String requesterStr) {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = transactionLogService.create(input, requester);
        return result;
    }

    @RequestMapping(value = "/product/transaction-list", method = RequestMethod.GET)
    public ResponseDTO getList(@RequestHeader(value = HttpHeader.REQUESTER) String requesterStr) {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = transactionLogService.getList();
        return result;
    }



}
