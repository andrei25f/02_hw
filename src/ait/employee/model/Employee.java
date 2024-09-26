package ait.employee.model;

import ait.utils.Id;
import ait.utils.Index;
import ait.utils.Table;

import java.time.LocalDate;

@Table
public class Employee {
    @Id
    @Index(unique = true)
    private int id;
    private String name;
    @Index
    private int salary;
    @Index
    private LocalDate birthDate;
    @Index(unique = true)
    private String email;

    public Employee() {
    }

    public Employee(int id, String name, int salary, LocalDate birthDate, String email) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.birthDate = birthDate;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", salary=").append(salary);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Employee employee = (Employee) object;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
