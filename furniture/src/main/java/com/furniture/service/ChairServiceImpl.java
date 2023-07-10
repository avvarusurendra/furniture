package com.furniture.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furniture.UserException;
import com.furniture.entity.Chair;
import com.furniture.repository.ChairRepository;

@Service
public class ChairServiceImpl implements ChairService{
	@Autowired
ChairRepository chairRepository;
	@Override
	public Optional<Chair> getChairById(Integer cid) throws UserException {
		Optional<Chair> c = chairRepository.findById(cid);
		
		if(c.isPresent()) {
			return c;
		}else {
			throw new UserException("user is not found with ID "+cid);
		}
		
	}

	
}
