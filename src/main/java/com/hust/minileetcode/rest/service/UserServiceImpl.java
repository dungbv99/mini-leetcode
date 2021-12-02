package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.*;
import com.hust.minileetcode.rest.model.*;
import com.hust.minileetcode.rest.repo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityExistsException;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
@javax.transaction.Transactional
public class UserServiceImpl implements UserService {
    private final UserLoginRepo userLoginRepo;

    private final StatusItemRepo statusItemRepo;

    private final UserRegisterRepo userRegisterRepo;

    private final MailService mailService;

    private final NotificationsService notificationsService;

    private final static ExecutorService EMAIL_EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private final Configuration config;

    private final SecurityGroupService groupService;

//    private final PartyService partyService;

    private final SecurityGroupRepo securityGroupRepo;

    private final PersonRepo personRepo;

    private StatusRepo statusRepo;


    @Override
    public UserLogin findById(String userLoginId) {
        return userLoginRepo.findByUserLoginId(userLoginId);

    }

    @Override
    @Transactional
    public SimpleResponse register(RegisterIM im) {
        //log.info("register, affiliations = " + im.getAffiliations().get(0));

        SimpleResponse res;
        String userLoginId = im.getUserLoginId();

        //if (userRegisterRepo.existsByUserLoginIdOrEmail(userLoginId, email) || userLoginRepo.existsById(userLoginId)) {
        if (userLoginRepo.existsById(userLoginId)) {
            res = new SimpleResponse(
                    400,
                    "existed",
                    "Tên người dùng hoặc email đã được sử dụng");
        } else {
            StatusItem statusItem = statusItemRepo
                    .findById("USER_REGISTERED")
                    .orElseThrow(NoSuchElementException::new);
            log.info("statusItem {}", statusItem);
            String firstName = StringUtils.normalizeSpace(im.getFirstName());
            String middleName = StringUtils.normalizeSpace(im.getMiddleName());
            String lastName = StringUtils.normalizeSpace(im.getLastName());
            String fullName = String.join(" ", firstName, middleName, lastName);
            String email = im.getEmail();
            log.info("------------------------------------");
//            UserRegister userRegister = new UserRegister(
//                    im.getUserLoginId(),
//                    im.getPassword(),
//                    email,
//                    firstName,
//                    middleName,
//                    lastName,
//                    String.join(",", im.getRoles()),
//                    String.join(",", im.getAffiliations()),
//                    statusItem);
//            log.info("String.join(\",\", im.getRoles()) {}", String.join(",", im.getRoles()));
            UserRegister userRegister = UserRegister.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .middleName(middleName)
                    .statusItem(statusItem)
                    .userLoginId(im.getUserLoginId())
                    .password(im.getPassword())
                    .registeredRoles(String.join(",", im.getRoles()))
                    .build();
            log.info("user register {}", userRegister);
            userRegisterRepo.save(userRegister);
            log.info("--------------------------------------");
            // push notifications
            notificationsService.create(
                    im.getUserLoginId(),
                    "admin",
                    fullName + " đã đăng kí tài khoản. Phê duyệt ngay.",
                    "/user-group/user/approve-register");

            // send email.
            EMAIL_EXECUTOR_SERVICE.execute(() -> {
                try {
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", fullName);
                    model.put("username", im.getUserLoginId());

                    Template template = config.getTemplate("successfully-register-mail-template.html");
                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

                    MimeMessageHelper helper = mailService.createMimeMessage(
                            new String[]{email},
                            "Open ERP - Đăng ký tài khoản thành công",
                            html,
                            true);
                    File resource = ResourceUtils.getFile("classpath:templates/logo.png");
                    helper.addInline("logo", resource);

                    mailService.sendMultipleMimeMessages(helper.getMimeMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            res = new SimpleResponse(200, null, null);
        }

        return res;
    }

    @Override
    @Transactional(readOnly = true)
    public GetAllRegistersOM getAllRegisters() {
        List<RegistsOM> userRegisters = userRegisterRepo.getAllRegists("USER_REGISTERED");

        if (null == userRegisters) {
            return new GetAllRegistersOM();
        }

        return new GetAllRegistersOM(userRegisters, groupService.getRoles());    }

    @Override
    @Transactional
    public UserLogin createAndSaveUserLogin(String userName, String password) {

//        Party party = partyService.save("PERSON");
//        UserLogin userLogin = new UserLogin(userName, password, null, true);
        UserLogin userLogin = UserLogin.builder()
                .userLoginId(userName)
                .password(UserLogin.PASSWORD_ENCODER.encode(password))
                .roles(null)
                .enabled(true)
                .build();
//        userLogin.setParty(party);
        if (userLoginRepo.existsById(userName)) {
            throw new EntityExistsException("userLoginId = " + userLogin.getUserLoginId() + ", already exist!");
        }
        return userLoginRepo.save(userLogin);
    }

    @Override
    @Transactional
    public SimpleResponse approve(ApproveRegistrationIM im) {
        log.info("approve {}", im);
        UserRegister userRegister = userRegisterRepo.findById(im.getUserLoginId()).orElse(null);

        if (null == userRegister) {
            return new SimpleResponse(404, "not existed", "Đăng ký không tồn tại hoặc đã bị xoá");
        }

        if ("USER_APPROVED".equals(userRegister.getStatusItem().getStatusId())) {
            return new SimpleResponse(400, "approved", "Tài khoản đã được phê duyệt trước đó");
        }

//        createAndSaveUserLogin(new PersonModel(
//                userRegister.getUserLoginId(),
//                userRegister.getPassword(),
//                im.getRoles(),
//                userRegister.getUserLoginId(),
//                userRegister.getFirstName(),
//                userRegister.getLastName(),
//                userRegister.getMiddleName(),
//                null,
//                null,userRegister.getAffiliations()));

        createAndSaveUserLogin(PersonModel.builder()
                .userName(userRegister.getUserLoginId())
                .password(userRegister.getPassword())
                .roles(im.getRoles())
                .firstName(userRegister.getFirstName())
                .lastName(userRegister.getLastName())
                .middleName(userRegister.getMiddleName())
                .gender(null)
                .birthDate(null)
                .affiliations(userRegister.getAffiliations())
                .email(userRegister.getEmail())
                .build());

        StatusItem userApproved = statusItemRepo.findById("USER_APPROVED").orElseThrow(NoSuchElementException::new);
        userRegister.setStatusItem(userApproved);

        userRegisterRepo.save(userRegister);

        // send email
        UserRegister ur = userRegisterRepo.findById(im.getUserLoginId()).orElse(null);
        if(ur != null) {
            String fullName = String.join(" ", ur.getFirstName(), ur.getMiddleName(), ur.getLastName());
            String email = ur.getEmail();
            log.info("approve, email = " + email + " fullname = " + fullName);
            EMAIL_EXECUTOR_SERVICE.execute(() -> {
                try {
                    log.info("email executor ");
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", fullName);
                    model.put("username", im.getUserLoginId());

                    Template template = config.getTemplate("successfully-approve-register-mail-template.html");
                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

                    MimeMessageHelper helper = mailService.createMimeMessage(
                            new String[]{email},
                            "Open ERP - Tài khoản đã được phê duyệt",
                            html,
                            true);
                    File resource = ResourceUtils.getFile("classpath:templates/logo.png");
                    helper.addInline("logo", resource);

                    mailService.sendMultipleMimeMessages(helper.getMimeMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return new SimpleResponse(200, null, null);
    }

    @Override
    public SimpleResponse disableUserRegistration(DisableUserRegistrationIM im) {
        StatusItem statusItem = statusItemRepo.findByStatusId(UserRegister.STATUS_DISABLED);
        UserRegister u = userRegisterRepo.findById(im.getUserLoginId()).orElse(null);
        if(u != null){
            u.setStatusItem(statusItem);
            u = userRegisterRepo.save(u);
            log.info("disableUserRegistration OK");

            // send notification email to userLoginId

            String fullName = u.getLastName() + " " + u.getMiddleName() + " " + u.getFirstName();
            String email = u.getEmail();
            // send email.
            EMAIL_EXECUTOR_SERVICE.execute(() -> {
                try {
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", fullName);
                    model.put("username", im.getUserLoginId());

                    Template template = config.getTemplate("not-approve-register-mail-template.html");
                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

                    MimeMessageHelper helper = mailService.createMimeMessage(
                            new String[]{email},
                            "Open ERP - Đăng ký tài khoản KHÔNG thành công",
                            html,
                            true);
                    File resource = ResourceUtils.getFile("classpath:templates/logo.png");
                    helper.addInline("logo", resource);

                    mailService.sendMultipleMimeMessages(helper.getMimeMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
        return new SimpleResponse(200,null,null);
    }

    @Override
    @Transactional
    public void createAndSaveUserLogin(PersonModel personModel) {
        log.info("create and save user login personModel {}", personModel);
//        void party = partyRepo.save(new void(
//                personModel.getPartyCode(),
//                partyTypeRepo.getOne(PartyTypeEnum.PERSON.name()),
//                "",
//                statusRepo
//                        .findById(StatusEnum.PARTY_ENABLED.name())
//                        .orElseThrow(NoSuchElementException::new),
//                false));
//
//        personRepo.save(new Person(
//                party.getPartyId(),
//                personModel.getFirstName(),
//                personModel.getMiddleName(),
//                personModel.getLastName(),
//                personModel.getGender(),
//                personModel.getBirthDate()));

        Person person = personRepo.save(Person.builder()
                .firstName(personModel.getFirstName())
                .lastName(personModel.getLastName())
                .middleName(personModel.getMiddleName())
                .gender(personModel.getGender())
                .status(statusRepo.findStatusById(Status.StatusEnum.PERSON_ENABLED.getValue()))
                .birthDate(personModel.getBirthDate())
                .build());

        Set<SecurityGroup> roles = securityGroupRepo.findAllByGroupIdIn(personModel.getRoles());
        UserLogin userLogin = UserLogin.builder()
                .userLoginId(personModel.getUserName())
                .password(UserLogin.PASSWORD_ENCODER.encode(personModel.getPassword()))
                .roles(roles)
                .email(personModel.getEmail())
                .enabled(true)
                .person(person)
                .build();
        log.info("save, roles = " + personModel.getRoles().size());
        if (userLoginRepo.existsById(personModel.getUserName())) {
            throw new RuntimeException();
        }

        userLoginRepo.save(userLogin);

    }


}
