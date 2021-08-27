package com.as.todo.entity;

import com.as.todo.util.Gender;
import com.as.todo.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
    private Long id;
    @NotEmpty
    @Size(max=20)
    private String firstName;
    private String lastName;
    @NotEmpty
    @Size(max=50)
    @Email
    private String email;
    private String mobileNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String birthday;
    @NotEmpty
    @Size(max=120)
    private String password;
    @OneToMany(targetEntity = TodoItem.class, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_fk", referencedColumnName = "id")
    private List<TodoItem> todoItems;
}
