package com.sms.project.user.entity;

import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.utility.errorcode.ErrorCodes;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Where(clause = "deleted_at is null")
public class User extends CommonEntity {

    @NotNull(message = ErrorCodes.USER_NAME_NOTNULL)
    private String username;

    @Column(unique = true)
    @NotNull(message = ErrorCodes.EMAIL_NOT_NULL)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String role;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Subscription.class,
            cascade = CascadeType.DETACH)
    private List<Subscription> subscriptions;

}
