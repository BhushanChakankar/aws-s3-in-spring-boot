package com.extwebtech.bean;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@lombok.Data
@Table(name = "media")
public class Data {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private double duration;
	@CreationTimestamp
	private Timestamp uploadedAt;
	private int userId;
	private int repeatCount;
	private String fileType;
	private String videoUrl;
	private Boolean isAlive;
}
