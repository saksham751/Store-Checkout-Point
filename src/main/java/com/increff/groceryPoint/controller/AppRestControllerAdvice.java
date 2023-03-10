package com.increff.groceryPoint.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.increff.groceryPoint.model.MessageData;
import com.increff.groceryPoint.dto.ApiException;

@RestControllerAdvice
public class AppRestControllerAdvice {

	@ExceptionHandler(ApiException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageData handle(ApiException e) {
		MessageData data = new MessageData();
		data.setMessage(e.getMessage());
		return data;
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageData handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
	{
		MessageData data = new MessageData();
		data.setMessage("Please Enter a Valid Input");
		return data;
	}
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public MessageData handle(Throwable e) {
		MessageData data = new MessageData();
		data.setMessage("An unknown error has occurred - " + e.getMessage());
		return data;
	}
}