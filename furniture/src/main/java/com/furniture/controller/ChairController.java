package com.furniture.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.UserException;
import com.furniture.entity.Chair;
import com.furniture.repository.ChairRepository;
import com.furniture.service.ChairService;
import com.furniture.model.UserStatus;
@RestController
@RequestMapping(value = "/chair")
public class ChairController {
	@Autowired
	ChairService chairService;
	@Autowired
	ChairRepository chairRepository;

	@PostMapping
	public ResponseEntity<?> saveChair(@RequestBody Chair chair) {
		ResponseEntity<?> responseEntity = null;

		if (chair.getCname() == null || chair.getCtype() == null) {
			UserStatus status = new UserStatus();
			status.setCode(400);
			status.setMessage("data can't be inserted into DB");
			status.setType("Bad request");
			responseEntity = new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);

		} else {
			 chairRepository.save(chair);
			responseEntity = new ResponseEntity<>(chairRepository.save(chair)+"hellooooo", HttpStatus.CREATED);
		}
		return responseEntity;

	}
	
	@GetMapping
	public ResponseEntity<?> getChair(){
		 ResponseEntity<?> responseEntity = null;
		try {
			List<Chair> chair= chairRepository.findAll();
			
			if(chair==null || chair.isEmpty()) {
				 UserStatus status = new UserStatus();
				 status.setMessage("the list is empty");
				 responseEntity = new ResponseEntity<>(status.getMessage(), HttpStatus.BAD_REQUEST);
			 }
			 else
				 responseEntity = new ResponseEntity<>(chair, HttpStatus.OK);
			 return responseEntity;
		} catch (Exception e) {
			UserStatus status = new UserStatus();
			status.setCode(400);
			status.setMessage("internal server error");
			responseEntity =new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return responseEntity;
		}
		 
		 
	}
	
	@GetMapping("/{cid}")
	public ResponseEntity<?> getChairById(@PathVariable Integer cid) {
		ResponseEntity<?> responseEntity = null;
		Optional<Chair> chair = null;
		if (cid != 0) {
			try {
				chair = chairService.getChairById(cid);
				responseEntity = new ResponseEntity<>(chair, HttpStatus.OK);
			} catch (UserException e) {
				UserStatus userStatus = new UserStatus();
				userStatus.setCode(400);
				userStatus.setMessage(e.getMessage());
				userStatus.setType("bad request");
				responseEntity = new ResponseEntity<>(userStatus, HttpStatus.BAD_REQUEST);

			}
		} else {
			responseEntity = new ResponseEntity<>("the id zero is invalid", HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/{cid}")
	public ResponseEntity<?> deleteChair(@PathVariable Integer cid) {
		ResponseEntity<?> responseEntity = null;
		Optional<Chair> c =null;
		if(cid==0) {
			responseEntity = new ResponseEntity<>("chair id zero is not accepted", HttpStatus.BAD_REQUEST);
		}
		else {
			c= chairRepository.findById(cid);
			if(c.isPresent()) {
				chairRepository.deleteById(cid);
				UserStatus status = new UserStatus();
				status.setCode(201);
				status.setMessage("data has been deleted from DB ");
				status.setType("OK");
				responseEntity = new ResponseEntity<>(status, HttpStatus.OK);
			}else {
				UserStatus status = new UserStatus();
				status.setCode(400);
				status.setMessage("there is no cid with "+cid+" so data removal is not possible");
				status.setType("Bad Request");
				responseEntity = new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
			}
			
				
			
		}return responseEntity;
		
	
	}
}
