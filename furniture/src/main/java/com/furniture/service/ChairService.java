package com.furniture.service;

import java.util.Optional;

import com.furniture.UserException;
import com.furniture.entity.Chair;

public interface ChairService {

	public Optional<Chair> getChairById(Integer cid) throws UserException;
		
	
}
