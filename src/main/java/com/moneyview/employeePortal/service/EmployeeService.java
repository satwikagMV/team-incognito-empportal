package com.moneyview.employeePortal.service;

import com.moneyview.employeePortal.dto.EmployeeDto;
import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.dto.SearchEmpDto;
import com.moneyview.employeePortal.entity.Employee;
import com.moneyview.employeePortal.entity.Tag;
import com.moneyview.employeePortal.entity.Type;
import com.moneyview.employeePortal.repository.EmployeeRepository;
import com.moneyview.employeePortal.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TagRepository tagRepository;
    private final CloudinaryService cloudinaryService;

    public Boolean verifyCredentials(String username, String password) throws Throwable {
//        System.out.println("Reached Verify");
        Employee emp =employeeRepository.findOneByUsername(username);
        System.out.println(emp);
        if (emp==null) throw new NullPointerException();
        return Objects.equals(emp.getPassword(), password);
    }

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
                .name(req.getName().toLowerCase())
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
            String badgeImgUrl = cloudinaryService.uploadFile(req.getBadgeImg(),req.getUsername());
            emp.setBadgeImgUrl(badgeImgUrl);
        }
        employeeRepository.save(emp);
    }

    public String addOrUpdateDisplayImage(EmployeeRequest req){
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

    public void updateEmployeeDetails(EmployeeRequest req){
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
            String badgeImgUrl = cloudinaryService.uploadFile(req.getBadgeImg(),req.getUsername());
            reqEmp.setBadgeImgUrl(badgeImgUrl);
        }

        employeeRepository.save(reqEmp);
    }
    public EmployeeDto getEmployeeDetails(String username) {
        // finding Employee
        return mapToDto(employeeRepository.findOneByUsername(username));
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
    public EmployeeDto getManagerDetails(String username){
        Employee manager= employeeRepository
                .findOneByUsername(username)
                .getManager();
        if (manager!=null) return mapToDto(manager);
        return null;
    }
    public List<SearchEmpDto> getAllEmployeesMatching(String pattern){
        return employeeRepository.findByNameLike("%"+pattern+"%")
                .stream()
                .map(m->new SearchEmpDto(m.getName(),m.getDisplayImgUrl()))
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
