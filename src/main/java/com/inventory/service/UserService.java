package com.inventory.service;

import com.inventory.dto.ProductCreateDTO;
import com.inventory.dto.ResponseDTO;
import com.inventory.dto.UserCreateDTO;
import com.inventory.enums.Authority;
import com.inventory.model.Product;
import com.inventory.model.User;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private ResponseDTO output = new ResponseDTO();
    @Autowired
    private UserRepository userRepository;

    public ResponseDTO create(UserCreateDTO input, com.inventory.model.dummy.User requester) {
        User user = new User();

        if (requester.hasAuthority(Authority.ROLE_ADMIN)) {
            user = userRepository.findByUsernameAndStatus(input.getUsername(), "V");
            if (user == null) {
                user = new User();
                user.setUsername(input.getUsername());
                user.setAdminPassword(input.getAdminPassword());
                user.setPassword(input.getPassword());
                user.setMobileNumber(input.getMobileNumber());
                user.setStatus("V");
                userRepository.save(user);
            } else {
                return output.generateErrorResponse("Already exist");
            }

        }
        return output.generateSuccessResponse(user, "Success");
    }

    public ResponseDTO getList() {
        List<User> users = userRepository.findAllByStatus("V");
        if (users == null) {
            return output.generateErrorResponse("No data found");
        } else {
            return output.generateSuccessResponse(users, "Success!");
        }
    }

    public ResponseDTO get(ObjectId id) {
        User user = userRepository.findByIdAndStatus(id, "V");
        if (user == null) {
            return output.generateErrorResponse("No data found");

        } else {
            return output.generateSuccessResponse(user, "Success");
        }

    }

    public ResponseDTO delete(ObjectId id, com.inventory.model.dummy.User requester)
    {
        if (requester.hasAuthority(Authority.ROLE_ADMIN))
        {
            User user = userRepository.findByIdAndStatus(id, "V");
            if (user == null) {
                return output.generateErrorResponse("You have entered a wrong id");
            } else {
                user.setStatus("D");
                userRepository.save(user);
                return output.generateSuccessResponse(user, "success");
            }

        }else
        {
            return output.generateErrorResponse("Permission Denied!!");
        }

    }


}
