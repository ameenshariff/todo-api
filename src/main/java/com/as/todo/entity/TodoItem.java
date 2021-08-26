package com.as.todo.entity;

import com.as.todo.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_item")
@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "todo_item_id")
    private Long id;
    @NotEmpty
    @Size(max = 50)
    private String name;
    @Size(max = 150)
    private String description;
    @CreationTimestamp
    private Date createdDate;
    @CreationTimestamp
    private Date updateDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String category;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRegistration UserRegistration;
}
