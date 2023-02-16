package ru.hogwarts.school.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentService {

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }


    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Faculty findFacultyByStudent(long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Collection<Student> findByFaculty(long faculty) {
        return studentRepository.findStudentByFaculty_Id(faculty);
    }

    public List<String> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> student.getName())
                .collect(Collectors.toList());
    }

    public void printNameStudentsThread() {

        List<String> students = getAllStudents();
        System.out.println(students.get(0));
        System.out.println(students.get(1));

        new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        }).start();
    }

    public void printNameStudentsSynchronizedThread() {

        List<String> students = getAllStudents();
        printNameStudent(students.get(0));
        printNameStudent(students.get(1));

        new Thread(() -> {
            printNameStudent(students.get(2));
            printNameStudent(students.get(3));
        }).start();

        new Thread(() -> {
            printNameStudent(students.get(4));
            printNameStudent(students.get(5));
        }).start();
    }

    public synchronized void printNameStudent(String name) {
        System.out.println(name);
    }

}