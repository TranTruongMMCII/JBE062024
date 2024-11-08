package com.r2s.java_backend_06.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "user", description = "db user model")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String userName;

	@Hidden
	private String password;

	private String email;

	private Integer age;

	@Column(columnDefinition = "bit default 0")
//	@Transient
	private Boolean isDeleted = Boolean.FALSE;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
//	@JsonBackReference
	private Profile profile;

	@ManyToOne
	@JoinColumn(name = "class_id", referencedColumnName = "id")
	private ClassRoom classRoom;

//	@ManyToMany
//	@JoinTable(name = "tkb", 
//	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
//	inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
//	private List<Course> courses = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<TKB> tkbs = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "role_user", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();
}
