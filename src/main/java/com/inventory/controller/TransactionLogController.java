package com.inventory.controller;

import com.inventory.constant.HttpHeader;
import com.inventory.dto.*;
import com.inventory.model.dummy.User;
import com.inventory.service.ProductService;
import com.inventory.service.TransactionLogService;
import com.inventory.utills.Utils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionLogController {
    @Autowired
    private TransactionLogService transactionLogService;

    @RequestMapping(value = "/checkout/create", method = RequestMethod.POST)
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

    @RequestMapping(value = "/transaction/{id}",method = RequestMethod.GET)
    public ResponseDTO get(@PathVariable("id") ObjectId id, @RequestHeader(value = HttpHeader.REQUESTER) String requesterStr)
    {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = transactionLogService.get(id);
        return result;
    }

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.PUT)
    public ResponseDTO update(@PathVariable("id") ObjectId prodId , @RequestBody TransactionLogUpdateDTO input, @RequestHeader(value = HttpHeader.REQUESTER) String requesterStr)
    {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = transactionLogService.update(input, prodId, requester);

        return result;
    }

    @RequestMapping(value = "/checkout/{id}",method = RequestMethod.DELETE)
    public ResponseDTO delete(@PathVariable("id") ObjectId id,@RequestHeader(value = HttpHeader.REQUESTER) String requesterStr)
    {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = transactionLogService.delete(id,requester);
        return result;
    }



}
