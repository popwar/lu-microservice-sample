package org.lu.data;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
@Document(collection = "customer")
public class Customer {
	@Id
	private String id;

	@NotEmpty
	private String userName;

	@NotEmpty
	private String password;

	@Digits(fraction = 0, integer = 2)
	@DecimalMax(value = "99")
	private int age;

	private Set<String> permissions = new HashSet<>();

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
}
