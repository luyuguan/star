package com.lyg.mybatisdb;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface Mapper3 {

	@Insert("insert into studenttb3(id, name, age, otherinfo) values(#{id}, #{name},#{age},#{otherinfo})")
	public void insertStudent(Student std);
	
	@Update("update studenttb3 set name=#{name},age=#{age}, otherinfo=#{otherinfo} where id=#{id}")
	public void updateStudent(Student std);
	
	@Select("select * from studenttb3 where id=#{id}")
	public Student getStudent(int id);
	
	@Select("select * from studenttb3")
	public List<Student> getAllStudents();
	
}
