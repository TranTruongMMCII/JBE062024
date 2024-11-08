package com.r2s.java_backend_06.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TKB {
//	@EmbeddedId
//	private TKBId id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Date startDate;

	private String professor;

	@ManyToOne
//	@MapsId(value = "courseId")
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private Course course;

	@ManyToOne
//	@MapsId(value = "userId")
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}

//@Embeddable // -> composite id
//class TKBId {
//
//	@JoinColumn(name = "user_id")
//	private Integer userId;
//
//	@JoinColumn(name = "course_id")
//	private Integer courseId;
//	// bo sung nhung khoa ngoai khac de lam khoa chinh
//}
