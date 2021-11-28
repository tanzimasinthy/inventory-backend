package com.inventory.controller;

import com.inventory.constant.HttpHeader;
import com.inventory.dto.ResponseDTO;
import com.inventory.dto.TransactionLogCreateDTO;
import com.inventory.dto.UserCreateDTO;
import com.inventory.model.dummy.User;
import com.inventory.service.TransactionLogService;
import com.inventory.service.UserService;
import com.inventory.utills.Utils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseDTO create(@RequestBody UserCreateDTO input, @RequestHeader(value = HttpHeader.REQUESTER) String requesterStr) {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = userService.create(input, requester);
        return result;
    }

    @RequestMapping(value = "/users/users-list", method = RequestMethod.GET)
    public ResponseDTO getList(@RequestHeader(value = HttpHeader.REQUESTER) String requesterStr) {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = userService.getList();
        return result;
    }

    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    public ResponseDTO get(@PathVariable("id") ObjectId id, @RequestHeader(value = HttpHeader.REQUESTER) String requesterStr)
    {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = userService.get(id);
        return result;
    }

    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    public ResponseDTO delete(@PathVariable("id") ObjectId id,@RequestHeader(value = HttpHeader.REQUESTER) String requesterStr)
    {
        User requester = Utils.generateUserFromJsonStr(requesterStr);
        ResponseDTO result = userService.delete(id,requester);
        return result;
    }



}
