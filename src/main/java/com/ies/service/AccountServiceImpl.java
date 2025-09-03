package com.ies.service;

import com.ies.bindings.UnlockAccForm;
import com.ies.bindings.UserAccForm;
import com.ies.entities.UserEntity;
import com.ies.repositories.UserRepository;
import com.ies.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public boolean createUserAccount(UserAccForm accForm) {
        logger.info("Creating user account for email: {}", accForm.getEmail());

        try {
            // Convert binding object → entity object
            UserEntity entity = new UserEntity();
            BeanUtils.copyProperties(accForm, entity);

            // set random pwd & default status
            entity.setPwd(generatePwd());
            entity.setAccStatus("LOCKED");
            entity.setActiveSw("Y");

            userRepo.save(entity);
            logger.info("User saved successfully with ID: {}", entity.getUserId());

            // send email
            String subject = "User Registration";
            String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
            boolean sent = emailUtils.sendEmail(subject, body, accForm.getEmail());

            if (sent) {
                logger.info("Registration email sent successfully to {}", accForm.getEmail());
            } else {
                logger.warn("Failed to send registration email to {}", accForm.getEmail());
            }
            return sent;

        } catch (Exception e) {
            logger.error("Error creating user account for email: {}", accForm.getEmail(), e);
            return false;
        }
    }

    @Override
    public List<UserAccForm> fetchUserAccounts() {
        logger.info("Fetching all user accounts");

        List<UserEntity> userEntities = userRepo.findAll();
        logger.debug("Total users fetched: {}", userEntities.size());

        List<UserAccForm> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserAccForm user = new UserAccForm();
            BeanUtils.copyProperties(userEntity, user);
            users.add(user);
        }

        logger.info("Returning {} user accounts", users.size());
        return users;
    }

    @Override
    public UserAccForm getUserAccById(Integer accId) {
        logger.info("Fetching user account by ID: {}", accId);

        Optional<UserEntity> optional = userRepo.findById(accId);
        if (optional.isPresent()) {
            UserEntity userEntity = optional.get();
            UserAccForm user = new UserAccForm();
            BeanUtils.copyProperties(userEntity, user);
            logger.info("User found for ID: {}", accId);
            return user;
        }

        logger.warn("No user found for ID: {}", accId);
        return null;
    }

    @Override
    public String changeAccStatus(Integer userId, String status) {
        logger.info("Changing account status for userId: {} → {}", userId, status);

        int cnt = userRepo.updateAccStatus(userId, status);
        if (cnt > 0) {
            logger.info("Status changed successfully for userId {}", userId);
            return "Status Changed!!";
        }

        logger.warn("Failed to change status for userId {}", userId);
        return "Failed To Change";
    }

    @Override
    public String unlockUserAccount(UnlockAccForm unlockAccForm) {
        logger.info("Unlocking account for email: {}", unlockAccForm.getEmail());

        UserEntity entity = userRepo.findByEmail(unlockAccForm.getEmail());
        if (entity == null) {
            logger.error("No user found with email: {}", unlockAccForm.getEmail());
            return "Account Not Found";
        }

        entity.setPwd(unlockAccForm.getNewPwd());
        entity.setAccStatus("UNLOCKED");

        userRepo.save(entity);

        logger.info("Account unlocked successfully for email: {}", unlockAccForm.getEmail());
        return "Account Unlocked";
    }

    // This method generates a random password
    private String generatePwd() {
        logger.debug("Generating random password");

        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int length = 6;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            sb.append(alphaNumeric.charAt(index));
        }

        String pwd = sb.toString();
        logger.debug("Generated password: {}", pwd);
        return pwd;
    }

    // Email body generator
    private String readEmailBody(String filename, UserEntity user) {
        logger.debug("Reading email template from {}", filename);

        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                line = line.replace("${FNAME}", user.getFullName());
                line = line.replace("${TEMP_PWD}", user.getPwd());
                line = line.replace("${EMAIL}", user.getEmail());
                sb.append(line);
            });
            logger.info("Email template processed successfully for {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Error reading email body template: {}", filename, e);
        }
        return sb.toString();
    }
}
