package com.oracle.oBootMybatis01.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
// JPA 연동
@Getter
@Setter
@Entity
@Table(name = "member3")
public class Member {
	@Id
	private Long 	id;
	private String 	name;
	private String 	password;
	private Date	reg_date;
	
}
