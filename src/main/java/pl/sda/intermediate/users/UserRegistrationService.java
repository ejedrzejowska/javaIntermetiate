package pl.sda.intermediate.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    private UserDAO userDAO;

    public void registerUser(UserRegistrationDTO userDTO) {
        if (userDAO.checkIfUserExists(userDTO.getEmail())) {
            throw new UserExistsException("User already exists!");
        }
        userDAO.saveUser(rewriteDTOtoUser(userDTO));
    }

    private User rewriteDTOtoUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPasswordHashed(DigestUtils.sha512Hex(userRegistrationDTO.getPassword()));
        user.setUserAddress(UserAddress.builder().city(userRegistrationDTO.getCity()).country(userRegistrationDTO.getCountry())
                .street(userRegistrationDTO.getStreet()).zipCode(userRegistrationDTO.getZipCode()).build());
        user.setPesel(userRegistrationDTO.getPesel());
        user.setBirthDate(userRegistrationDTO.getBirthDate());
        user.setPhone(userRegistrationDTO.getPhone());
        user.setPreferEmails(userRegistrationDTO.isPreferEmails());
        return user;
    }
}
