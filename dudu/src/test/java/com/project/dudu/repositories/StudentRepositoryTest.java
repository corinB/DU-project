package com.project.dudu.repositories;

import com.project.dudu.entities.StudentEntity;
import com.project.dudu.enums.Colleges;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {
    @Autowired StudentRepository studentRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void addStudent() {
        String name = "test";
        for(long i = 0; i < 10; i++) {
            var student = StudentEntity.builder().studentId(i).studentName(name+i).department(Colleges.Engineering).build();
            studentRepository.save(student);
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void findStudent() {
        List<StudentEntity> studentEntities = new ArrayList<>();
        for(long i = 0; i < 10; i++) {
            studentRepository.findById(i).ifPresent(studentEntity -> {

                        if (studentEntity.getStudentId()%2 == 0){
                            studentEntities.add(studentEntity);
                        }
                }
            );
        }
        studentEntities.forEach(s -> System.out.println("학생: "+s.getStudentName()));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateStudent() {
        studentRepository.findById(0L).ifPresent(studentEntity ->{
                studentEntity.setStudentName("update_name");
                studentEntity.setDepartment(Colleges.InformationCommunication);
                studentRepository.save(studentEntity);
            }
        );
    }
}