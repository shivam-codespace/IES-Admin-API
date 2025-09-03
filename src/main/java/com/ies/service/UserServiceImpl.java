package com.ies.service;

import com.ies.bindings.DasboardCards;
import com.ies.bindings.LoginForm;
import com.ies.bindings.UserAccForm;
import com.ies.constants.AppConstants;
import com.ies.entities.EligEntity;
import com.ies.entities.UserEntity;
import com.ies.repositories.EligRepo;
import com.ies.repositories.PlanRepository;
import com.ies.repositories.UserRepository;
import com.ies.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PlanRepository planRepo;

    @Autowired
    private EligRepo eligRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public String login(LoginForm loginForm) {
        logger.info("Attempting login for email: " + loginForm.getEmail());

        UserEntity entity = userRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());

        if (entity == null) {
            logger.warning("Invalid credentials for email: " + loginForm.getEmail());
            return AppConstants.INVALID_CRED;
        }

        if (AppConstants.Y_STR.equals(entity.getActiveSw()) && AppConstants.UNLOCKED.equals(entity.getAccStatus())) {
            logger.info("Login successful for email: " + loginForm.getEmail());
            return AppConstants.SUCCESS;
        } else {
            logger.warning("Account locked for email: " + loginForm.getEmail());
            return AppConstants.ACC_LOCKED;
        }
    }

    @Override
    public boolean recoverPwd(String email) {
        logger.info("Initiating password recovery for email: " + email);

        UserEntity userEntity = userRepo.findByEmail(email);
        if (userEntity == null) {
            logger.warning("No user found with email: " + email);
            return false;
        } else {
            String subject = AppConstants.RECOVER_SUB;
            String body = readEmailBody(AppConstants.PWD_BODY_FILE, userEntity);

            boolean sent = emailUtils.sendEmail(subject, body, email);
            if (sent) {
                logger.info("Password recovery email sent successfully to: " + email);
            } else {
                logger.warning("Failed to send password recovery email to: " + email);
            }
            return sent;
        }
    }

    @Override
    public DasboardCards fetchDashboardInfo() {
        logger.info("Fetching dashboard information...");

        long plansCount = planRepo.count();
        List<EligEntity> eligList = eligRepo.findAll();

        Long approvedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.AP)).count();
        Long deniedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.DN)).count();
        Double total = eligList.stream().mapToDouble(EligEntity::getBenefitAmt).sum();

        DasboardCards card = new DasboardCards();
        card.setPlansCnt(plansCount);
        card.setApprovedCnt(approvedCnt);
        card.setDeniedCnt(deniedCnt);
        card.setBeniftAmtGiven(total);

        logger.info("Dashboard data fetched: Plans=" + plansCount + 
                    ", Approved=" + approvedCnt + 
                    ", Denied=" + deniedCnt + 
                    ", BenefitAmt=" + total);

        return card;
    }

    @Override
    public UserAccForm getUserByEmail(String email) {
        logger.info("Fetching user by email: " + email);

        UserEntity userEntity = userRepo.findByEmail(email);
        if (userEntity == null) {
            logger.warning("No user found with email: " + email);
            return null;
        }

        UserAccForm user = new UserAccForm();
        BeanUtils.copyProperties(userEntity, user);
        logger.info("User details retrieved for email: " + email);
        return user;
    }

    private String readEmailBody(String filename, UserEntity user) {
        logger.info("Reading email body template: " + filename);

        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                line = line.replace(AppConstants.FNAME, user.getFullName());
                line = line.replace(AppConstants.PWD, user.getPwd());
                line = line.replace(AppConstants.EMAIL, user.getEmail());
                sb.append(line);
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading email body file: " + filename, e);
        }
        return sb.toString();
    }
}
