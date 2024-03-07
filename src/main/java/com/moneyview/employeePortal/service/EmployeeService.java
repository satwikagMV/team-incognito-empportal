package com.moneyview.employeePortal.service;

import com.moneyview.employeePortal.dto.EmployeeDto;
import com.moneyview.employeePortal.dto.EmployeeRequest;
import com.moneyview.employeePortal.entity.Employee;
import com.moneyview.employeePortal.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final CloudinaryService cloudinaryService;

    public void createEmployee(EmployeeRequest req) throws Throwable{
//        check if employee Exist or Not
        if (doesEmployeeExist(req.getUsername())){
            throw new Exception();
        };
//        if not Exist Creating Employee
        Employee emp=Employee.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .name(req.getName().toLowerCase())
                .designation(req.getDesignation())
                .level(req.getLevel())
                .phoneNo(req.getPhoneNo())
                .slackId(req.getSlackId())
                .build();
        if (req.getManagerUsername()!=null){
            emp.setManager(
                    employeeRepository.findOneByUsername(
                            req.getManagerUsername()
                    )
            );
        }
        if (req.getBadgeImg()!=null){
            String badgeImgUrl = cloudinaryService.uploadFile(req.getBadgeImg(),req.getUsername());
            emp.setBadgeImgUrl(badgeImgUrl);
        }
        employeeRepository.save(emp);
    }
//
    public void addOrUpdateDisplayImage(EmployeeRequest req){
        Employee currEmployee= employeeRepository.findOneByUsername(req.getUsername());
        if (req.getDisplayImg()!=null) {
            if (currEmployee.getDisplayImgUrl()!=null)
                cloudinaryService.deleteFile(
                    currEmployee.getDisplayImgUrl()
                );
            String displayImgUrl = cloudinaryService.uploadFile(req.getDisplayImg(),req.getUsername());
            currEmployee.setDisplayImgUrl(displayImgUrl);
        }
    }

    public void updateEmployeeDetails(EmployeeRequest req){
        Employee reqEmp=employeeRepository.findOneByUsername(req.getUsername());
        if (req.getEmail()!=null) reqEmp.setEmail(req.getEmail());
        if (req.getName()!=null) reqEmp.setName(req.getName());
        if (req.getPhoneNo()!=null) reqEmp.setPhoneNo(req.getPhoneNo());
        if (req.getSlackId()!=null) reqEmp.setSlackId(req.getSlackId());
        if (req.getLevel()!=null) reqEmp.setLevel(req.getLevel());
        if (req.getDesignation()!=null) reqEmp.setDesignation(req.getDesignation());
        if (req.getManagerUsername()!=null)
            reqEmp.setManager(
                    employeeRepository.findOneByUsername(
                            req.getManagerUsername()
                    )
            );
    }
    public Employee getEmployeeDetails(String username) {
        // finding Employee
        return employeeRepository.findOneByUsername(username);
    }
    public List<EmployeeDto> getReporetee(String username){
        return employeeRepository.findOneByUsername(username)
                .getReportee()
                .stream()
                .map(EmployeeService::mapToDto)
                .toList();
    }

    public EmployeeDto getManagerDetails(String username){
        return mapToDto(
                employeeRepository
                        .findOneByUsername(username)
                        .getManager());
    }
    public List<EmployeeDto> getAllEmployeesMatching(String pattern){
        return employeeRepository.findByNameorUsernameLike("%"+pattern+"%")
                .stream()
                .map(EmployeeService::mapToDto)
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
                .displayImgUrl(e.getDisplayImgUrl())
        .build();
    }


}
