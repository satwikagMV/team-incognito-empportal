package com.moneyview.employeePortal.service;

import com.moneyview.employeePortal.dto.DocumentRequest;
import com.moneyview.employeePortal.dto.EmployeeDto;
import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.dto.SearchEmpDto;
import com.moneyview.employeePortal.entity.Document;
import com.moneyview.employeePortal.entity.Employee;
import com.moneyview.employeePortal.entity.Tag;
import com.moneyview.employeePortal.entity.Type;
import com.moneyview.employeePortal.repository.DocumentRepository;
import com.moneyview.employeePortal.repository.EmployeeRepository;
import com.moneyview.employeePortal.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TagRepository tagRepository;
    private final CloudinaryService cloudinaryService;
    private final FirebaseService firebaseService;
    private final DocumentRepository documentRepository;

    public void createEmployee(EmployeeRequest req) throws Throwable{
//        check if employee Exist or Not
        if (doesEmployeeExist(req.getUsername())){
            throw new Exception();
        };
//        if not Exist Creating Employee
        Employee emp=Employee.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(req.getPassword())
                .name(StringUtils.capitalize(req.getName()))
                .designation(req.getDesignation())
                .level(req.getLevel())
                .phoneNo(req.getPhoneNo())
                .slackId(req.getSlackId())
                .build();

        if (req.getManagerUsername()!=null){
            Employee manager = employeeRepository.findOneByUsername(
                    req.getManagerUsername()
            );
            emp.setManager(manager);
        }
        if (req.getBadgeImg()!=null){
            String badgeImgUrl = firebaseService.uploadFile(req.getBadgeImg());
            emp.setBadgeImgUrl(badgeImgUrl);
        }
        employeeRepository.save(emp);
    }

    public void updateEmployeeDetails(EmployeeRequest req) throws IOException {
        Employee reqEmp=employeeRepository.findOneByUsername(req.getUsername());
        if (req.getEmail()!=null) reqEmp.setEmail(req.getEmail());
        if (req.getName()!=null) reqEmp.setName(req.getName());
        if (req.getPhoneNo()!=null) reqEmp.setPhoneNo(req.getPhoneNo());
        if (req.getSlackId()!=null) reqEmp.setSlackId(req.getSlackId());
        if (req.getLevel()!=null) reqEmp.setLevel(req.getLevel());
        if (req.getDesignation()!=null) reqEmp.setDesignation(req.getDesignation());
        if (req.getManagerUsername()!=null) {
            Employee manager = employeeRepository.findOneByUsername(
                    req.getManagerUsername()
            );
            reqEmp.setManager(manager);
        }

        if (req.getBadgeImg()!=null){
            cloudinaryService.deleteFile(reqEmp.getBadgeImgUrl());
            String badgeImgUrl = firebaseService.uploadFile(req.getBadgeImg());
            reqEmp.setBadgeImgUrl(badgeImgUrl);
        }

        employeeRepository.save(reqEmp);
    }

    public String addOrUpdateDisplayImageCloudinary(EmployeeRequest req){
        Employee currEmployee= employeeRepository.findOneByUsername(req.getUsername());

        if (req.getDisplayImg()!=null) {
            if (currEmployee.getDisplayImgUrl()!=null)
                cloudinaryService.deleteFile(
                    currEmployee.getDisplayImgUrl()
                );
            String displayImgUrl = cloudinaryService.uploadFile(req.getDisplayImg(),req.getUsername());
            currEmployee.setDisplayImgUrl(displayImgUrl);

        }
        employeeRepository.save(currEmployee);
        return currEmployee.getDisplayImgUrl();
    }

    public String addOrUpdateDisplayImageFirebase(EmployeeRequest req){
        Employee currEmployee= employeeRepository.findOneByUsername(req.getUsername());
        try {
            if (currEmployee.getDisplayImgUrl()!=null){
                firebaseService.deleteFile(currEmployee.getDisplayImgUrl());
            }
            String displayImgUrl = firebaseService.uploadFile(req.getDisplayImg());
            currEmployee.setDisplayImgUrl(displayImgUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeeRepository.save(currEmployee);
        return currEmployee.getDisplayImgUrl();
    }

    public String uploadDocument(DocumentRequest req) throws IOException {
        System.out.println(req.getUsername());
        Employee currEmp=employeeRepository.findOneByUsername(req.getUsername());
        System.out.println(currEmp);
        String docUrl;
        System.out.println(req.getFile());
        docUrl=firebaseService.uploadFile(req.getFile());
        System.out.println(docUrl);
        Document doc=Document.builder().name(req.getFileName()).url(docUrl).employee(currEmp).build();
        documentRepository.save(doc);
        System.out.println(doc);
//        currEmp.getDocuments().add(doc);
//        employeeRepository.save(currEmp);
        return docUrl;
    }

    public Employee getEmployeeDetails(String username) {
        // finding Employee
        return employeeRepository.findOneByUsername(username);
    }
    public List<Employee> getReportee(String username){
        return employeeRepository.findOneByUsername(username)
                .getReportee()
                .stream()
                .toList();
    }


    public void assignTag(String username, String tagName, Type type){
        Tag reqTag= tagRepository.findByNameAndType(tagName,type);
        if (reqTag==null) reqTag=Tag.builder().name(tagName).type(type).build();
        tagRepository.save(reqTag);
        Employee currEmp=employeeRepository.findOneByUsername(username);
        currEmp.getAssignedTags().add(reqTag);
        employeeRepository.save(currEmp);
    }

    public void unAssignTag(String username, String tagName, Type type){
        Tag reqTag= tagRepository.findByNameAndType(tagName,type);
        Employee currEmp=employeeRepository.findOneByUsername(username);
        currEmp.getAssignedTags().remove(reqTag);
        employeeRepository.save(currEmp);
    }

    public EmployeeDto getManagerDetails(String username){
        Employee manager = employeeRepository.findOneByUsername(username).getManager();
        return manager != null ? mapToDto(manager) : null;
    }
    public List<SearchEmpDto> getAllEmployeesMatching(String pattern){
        return employeeRepository.findByNameLike("%"+pattern+"%")
                .stream()
                .map(m->new SearchEmpDto(m.getUsername(),m.getName(),m.getDisplayImgUrl()))
                .toList();
    }


    private Boolean doesEmployeeExist(String username){
        return employeeRepository.findOneByUsername(username) !=null;
    }

    private static EmployeeDto mapToDto(Employee e){
        if (e==null) return null;
        return EmployeeDto.builder()
                .username(e.getUsername())
                .email(e.getEmail())
                .name(e.getName())
                .designation(e.getDesignation())
                .level(e.getLevel())
                .phoneNo(e.getPhoneNo())
                .slackId(e.getSlackId())
                .badgeImgUrl(e.getBadgeImgUrl())
                .displayImgUrl(e.getDisplayImgUrl())
                .assignedTags(e.getAssignedTags())
                .reportee(e.getReportee())
        .build();
    }
}
