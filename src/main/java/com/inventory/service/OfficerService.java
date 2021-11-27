package com.inventory.service;


import com.inventory.dto.OfficerCreateDTO;
import com.inventory.dto.OfficerUpdateDTO;
import com.inventory.dto.ResponseDTO;
import com.inventory.enums.Authority;
import com.inventory.model.Officer;
import com.inventory.model.dummy.User;
import com.inventory.repository.OfficerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class OfficerService {
    private ResponseDTO output = new ResponseDTO();
    @Autowired
    private OfficerRepository officerRepository;

    public ResponseDTO create(OfficerCreateDTO input, User requester) {
        Officer officer = new Officer();
        if (requester.hasAuthority(Authority.ROLE_ADMIN)) {
            officer = officerRepository.findByEmailAndStatus(input.getEmail(), "V");
            if (officer == null) {
                officer = new Officer();
                officer.setFirstName(input.getFirstName());
                officer.setLastName(input.getLastName());
                officer.setPhoneNo(input.getPhoneNo());
                officer.setPassword(encodePassword(input.getPassword()));
                officer.setEmail(input.getEmail());
                officer.setDesignation(input.getDesignation());
                officer.setAuthority(input.getAuthority());
                officer.setStatus("V");
                officerRepository.save(officer);
            } else {
                return output.generateErrorResponse(" Already exist !!");

            }
        }
        return output.generateSuccessResponse(officer, "Successfully created");
    }

    public ResponseDTO getList() {
        List<Officer> officers = officerRepository.findAllByStatus("V");
        if (officers == null) {
            return output.generateErrorResponse("No data found");
        } else {
            return output.generateSuccessResponse(officers, "Success!");
        }
    }

    public ResponseDTO get(ObjectId id) {
        Officer officer = officerRepository.findByIdAndStatus(id, "V");
        if (officer == null) {
            return output.generateErrorResponse("Data not found");
        } else {
            return output.generateSuccessResponse(officer, "Success");
        }
    }

    public ResponseDTO update(OfficerUpdateDTO input, User requester)
    {
        Officer officer;
        if (requester.hasAuthority(Authority.ROLE_ADMIN)) {
            officer = officerRepository.findByEmailAndStatus(input.getEmail(), "V");
            if (officer != null) {
                officer.setLastName(input.getLastName());
                officer.setPhoneNo(input.getPhoneNo());
                officer.setEmail(input.getEmail());
                officer.setDesignation(input.getDesignation());
                officer.setAuthority(input.getAuthority());
                officerRepository.save(officer);
                return output.generateSuccessResponse(officer, "successfully updated");
            } else {
                return output.generateErrorResponse("Data Not Found!!");
            }
        }

        else {
            return output.generateErrorResponse("Permission Denied!!");
        }
    }



    public ResponseDTO delete(ObjectId id,User requester)
    {
        if (requester.hasAuthority(Authority.ROLE_ADMIN))
        {
            Officer officer = officerRepository.findByIdAndStatus(id, "V");
            if (officer == null) {
                return output.generateErrorResponse("You have entered a wrong id");
            } else {
                officer.setStatus("D");
                officerRepository.save(officer);
                return output.generateSuccessResponse(officer, "success");
            }

        }else
        {
            return output.generateErrorResponse("Permission Denied!!");
        }

    }

    public String encodePassword(String password)
    {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        return encodedPassword;
    }
    public String decodePassword(String input)
    {
        byte[] decodedPassword = Base64.getDecoder().decode(input);
        return  new String(decodedPassword);
    }
}
