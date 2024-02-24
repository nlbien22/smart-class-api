package com.capstone.smartclassapi.users_classes;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_classes")
@IdClass(UsersClassesId.class)
public class UsersClasses {

    @Id
    private Long id;

    @Id
    private Long classId;

}

class UsersClassesId implements Serializable {

    private Long id;

    private Long classId;

}