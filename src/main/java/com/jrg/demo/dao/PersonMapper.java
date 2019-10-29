package com.jrg.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.jrg.demo.entity.Person;

public interface PersonMapper {
    int insert(Person record);

    int insertSelective(Person record);

	List<Person> findPersonByCertNo(String certno);
    @Select("select * from person where name like concat('%',#{name},'%')")
	List<Person> findPersonLikeName(String name);
    @Select("select * from person where name = #{name}")
    Person findPersonByName(String name);
}