package com.moneyview.employeePortal.service;

import com.moneyview.employeePortal.dto.EmployeeDto;
import com.moneyview.employeePortal.dto.EmployeeResponse;
import com.moneyview.employeePortal.dto.TagDto;
import com.moneyview.employeePortal.dto.TagResponse;
import com.moneyview.employeePortal.entity.Employee;
import com.moneyview.employeePortal.entity.Tag;
import com.moneyview.employeePortal.entity.Type;
import com.moneyview.employeePortal.repository.TagRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    public void createorGetTag(String tagName , Type tagType) throws Throwable {
        Tag tg=tagRepository.findByNameAndType(tagName,tagType);
        if (tg!=null){
        }
        Tag tag=Tag.builder()
                .name(tagName)
                .type(tagType)
                .build();

        tagRepository.save(tag);
    }
    

    public List<EmployeeDto> getAssociatedEmployees(String tagName,Type tagType){
        return tagRepository.findByNameAndType(tagName,tagType)
                .getAssociatedEmployees()
                .stream()
                .map(TagService::mapToDto)
                .toList();
    }

    public List<TagDto> getCommunities(){
        return tagRepository.findByType(Type.COMMUNITY).stream().map(TagService::mapToDto).toList();
    }

    private static TagDto mapToDto(Tag t){
        return TagDto.builder()
                .name(t.getName())
                .type(t.getType())
                .build();
    }

    private static EmployeeDto mapToDto(Employee e){
        if (e==null) return null;
        return EmployeeDto.builder()
                .username(e.getUsername())
                .email(e.getEmail())
                .name(e.getName())
                .designation(e.getDesignation())
                .level(e.getLevel())
                .badgeImgUrl(e.getBadgeImgUrl())
                .displayImgUrl(e.getDisplayImgUrl())
                .assignedTags(e.getAssignedTags())
                .manager(e.getManager())
                .reportee(e.getReportee())
                .build();
    }
}
