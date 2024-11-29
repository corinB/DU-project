package com.project.dudu.service;

import com.project.dudu.entities.ManagerEntity;
import com.project.dudu.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerLoginService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerLoginService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    // 인증 로직
    public boolean authenticate(String email, String password) {
        // 이메일로 관리자 정보 조회
        ManagerEntity manager = managerRepository.findByEmail(email).orElse(null);
        if (manager != null) {
            // 비밀번호 확인 (비밀번호 암호화가 되어 있다면 복호화 또는 해시 비교 로직 필요)
            return manager.getPassword().equals(password);
        }
        return false;
    }
}
