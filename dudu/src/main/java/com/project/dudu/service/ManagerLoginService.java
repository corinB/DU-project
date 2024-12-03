package com.project.dudu.service;

import com.project.dudu.dto.ManagerDto;
import com.project.dudu.entities.ManagerEntity;
import com.project.dudu.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerLoginService {

    private final ManagerRepository managerRepository;


    // 인증 로직
    public ManagerDto authenticate(String email, String password) {
        // 이메일로 관리자 정보 조회
        ManagerEntity manager = managerRepository.findByEmail(email).orElse(null);
        if (manager != null) {
            // 비밀번호 확인 (비밀번호 암호화가 되어 있다면 해시 비교 로직 필요)
            if (manager.getPassword().equals(password)) {
                // 인증 성공 시 ManagerDto로 변환하여 반환
                return convertToDto(manager);
            }
        }
        return null; // 인증 실패 시 null 반환
    }

    // Entity를 DTO로 변환하는 메서드
    private ManagerDto convertToDto(ManagerEntity entity) {
        return ManagerDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .department(entity.getDepartment().getName())
                .build();
    }
}
