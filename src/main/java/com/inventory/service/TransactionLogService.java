package com.inventory.service;

import com.inventory.dto.ProductCreateDTO;
import com.inventory.dto.ResponseDTO;
import com.inventory.dto.TransactionLogCreateDTO;
import com.inventory.enums.Authority;
import com.inventory.model.Product;
import com.inventory.model.TransactionLog;
import com.inventory.model.dummy.User;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionLogService {
    private ResponseDTO output = new ResponseDTO();
    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseDTO create(TransactionLogCreateDTO input, User requester) {
        TransactionLog transactionLog = new TransactionLog();

        if (requester.hasAuthority(Authority.ROLE_ADMIN)) {
            transactionLog = transactionRepository.findByNameAndStatus(input.getName(), "V");
            if (transactionLog == null) {
                transactionLog = new TransactionLog();
                transactionLog.setName(input.getName());
                transactionLog.setBillNo(input.getBillNo());
                transactionLog.setId(new ObjectId());
                transactionLog.setItems(input.getItems());
                transactionLog.setMobileNumber(input.getMobileNumber());
                transactionLog.setTotal(input.getTotal());
                transactionLog.setDate(input.getDate());
                transactionLog.setStatus("V");
                transactionRepository.save(transactionLog);
            } else {
                return output.generateErrorResponse("Already exist");
            }

        }
        return output.generateSuccessResponse(transactionLog, "Success");
    }

    public ResponseDTO getList() {
        List<TransactionLog> transactionLogs = transactionRepository.findAllByStatus("V");
        if (transactionLogs == null) {
            return output.generateErrorResponse("No data found");
        } else {
            return output.generateSuccessResponse(transactionLogs, "Success!");
        }
    }
}
