package info.kgeorgiy.ja.yulcova.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@SuppressWarnings("unchecked")
public class StudentDB implements StudentQuery{

    public static final Comparator<Student> BY_NAME_COMPARATOR = Comparator.comparing(Student::getLastName, Comparator.reverseOrder())
            .thenComparing(Student::getFirstName, Comparator.reverseOrder())
            .thenComparingInt(Student::getId);

    private static Stream<String> mapStudent(final List<Student> students, final Function<Student, String> fun) {
        return students.stream().map(fun);
    }

    private static List<String> mapStudentList(final List<Student> students, final Function<Student, String> fun) {
        return mapStudent(students, fun).collect(Collectors.toList());
    }

    private static List<Student> sortedList(final Collection<Student> students, final Predicate<Student> predicate) {
        return students.stream().filter(predicate).sorted(BY_NAME_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(final List<Student> students) {
        return mapStudentList(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(final List<Student> students) {
        return mapStudentList(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(final List<Student> students) {
        return students.stream().map(Student::getGroup).collect(Collectors.toList());
    }

    @Override
    public List<String> getFullNames(final List<Student> students) {
        return mapStudentList(students, student -> student.getFirstName()+ " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(final List<Student> students) {
        return mapStudent(students, Student::getFirstName).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMaxStudentFirstName(final List<Student> students) {
        return students.stream().max(Student::compareTo).map(Student::getFirstName).orElse("");
    }

    @Override
    public List<Student> sortStudentsById(final Collection<Student> students) {
        return students.stream().sorted(Student::compareTo).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(final Collection<Student> students) {
        return sortedList(students, student -> true);
    }

    private static <T> List<Student> findBase(final Collection<Student> students, final Function<Student, T> fun, final T comp){
        return sortedList(students, student -> comp.equals(fun.apply(student)));
    }
	
    @Override
    public List<Student> findStudentsByFirstName(final Collection<Student> students, final String name) {
        return findBase(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(final Collection<Student> students, final String name) {
        return findBase(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(final Collection<Student> students, final GroupName group) {
        return findBase(students, Student::getGroup, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(final Collection<Student> students, final GroupName group) {
        return findStudentsByGroup(students, group).stream().collect(Collectors.toMap(
                Student::getLastName,
                Student::getFirstName,
                BinaryOperator.minBy(String::compareTo)
        ));
    }
}
