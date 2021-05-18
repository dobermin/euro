package com.example.euro2020.repository;

import com.example.euro2020.entity.Navigation;
import com.example.euro2020.security.model.enums.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavigationRepository extends CrudRepository<Navigation, Long> {

//	@Query("SELECT n FROM Navigation n WHERE n.user = 'USER'")
	List<Navigation> findAllByUserOrderBySectionAscPositionAscTitleAsc (Roles user);

	@Query("SELECT n FROM Navigation n WHERE n.user <> 'NONE'")
	List<Navigation> findAllByAdmin ();
}
