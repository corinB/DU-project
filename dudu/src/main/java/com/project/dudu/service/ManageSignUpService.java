package com.project.dudu.service;

import com.project.dudu.dto.ManagerDto;
import com.project.dudu.entities.ManagerEntity;
import com.project.dudu.enums.Colleges;
import com.project.dudu.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ManageSignUpService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManageSignUpService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    // 관리자 회원가입 처리 메서드
    public ManagerDto registerManager(ManagerDto managerDto) {
        // 입력값 검증
        if (managerDto.getEmail() == null || managerDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
        }
        if (managerDto.getPassword() == null || managerDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
        if (managerDto.getName() == null || managerDto.getName().isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
        if (managerRepository.existsByEmail(managerDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // department 문자열을 Colleges enum으로 변환
        Colleges departmentEnum = Colleges.getByName(managerDto.getDepartment());
        if (departmentEnum == null) {
            throw new IllegalArgumentException("유효하지 않은 학과입니다.");
        }

        // 비밀번호 암호화 (필요 시 구현)
        String password = managerDto.getPassword();
        // TODO: 비밀번호 암호화 로직 추가

        // ManagerEntity 생성 및 필드 설정
        ManagerEntity managerEntity = ManagerEntity.builder()
                .email(managerDto.getEmail())
                .password(password) // 암호화된 비밀번호 사용
                .name(managerDto.getName())
                .department(departmentEnum)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        // 저장
        ManagerEntity savedEntity = managerRepository.save(managerEntity);

        // 저장된 엔티티를 DTO로 변환하여 반환
        return convertToDto(savedEntity);
    }

    // Entity를 DTO로 변환하는 헬퍼 메서드
    private ManagerDto convertToDto(ManagerEntity entity) {
        return ManagerDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .department(entity.getDepartment().getName()) // 한글 학과명 사용
                .build();
    }
}
