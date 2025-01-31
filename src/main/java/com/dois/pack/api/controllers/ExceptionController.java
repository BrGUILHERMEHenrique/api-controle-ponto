package com.dois.pack.api.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dois.pack.api.exceptions.DiaForaDeVigenciaException;
import com.dois.pack.api.exceptions.EmptyHourException;
import com.dois.pack.api.exceptions.SameCnpjException;
import com.dois.pack.api.exceptions.SameCpfException;
import com.dois.pack.api.exceptions.WrongTimeException;

import javassist.NotFoundException;

@RestControllerAdvice
public class ExceptionController {
		
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> constraintViolation(ConstraintViolationException exception){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Códigos de matricula repetidos, por favor varefique o mesmo");
		String msg = "Por favor verifique os campos, algum campo está vazio ou o código de matrícula está repetido";
		return ResponseEntity.badRequest().headers(headers).body(exception.getConstraintName());
	}
	

	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> NullPointer(NullPointerException exception){
		String msg = "Algum parâmetro foi passado de forma errada, por favor verifique os dados passados";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("message", msg)
				.body(exception.getCause());
	}

	@ExceptionHandler(SameCpfException.class)
	public ResponseEntity<?> sameCpf(SameCpfException exception){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", exception.getMsg());
		return ResponseEntity.badRequest().headers(headers).body(exception.getMsg());
	}
	
	@ExceptionHandler(SameCnpjException.class)
	public ResponseEntity<?> sameCnpj(SameCnpjException exception){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", exception.getMsg());
		return ResponseEntity.badRequest().headers(headers).body(exception.getMsg());
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException exception){
		HttpHeaders headers = new HttpHeaders();
		String msg = "O dado solicitado não foi encontrado, por favor verifique o mesmo";
		headers.add("message", msg);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(msg);
	}
	
	@ExceptionHandler(EmptyHourException.class)
	public ResponseEntity<?> emptyHours(EmptyHourException excception){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", excception.getMsg());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(excception.getMsg());
	}
	
	@ExceptionHandler(WrongTimeException.class)
	public ResponseEntity<?> wrongTime(WrongTimeException exception) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", exception.getMsg());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(exception.getMsg());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> illegalArgument(IllegalArgumentException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(DiaForaDeVigenciaException.class)
	public ResponseEntity<?> diaForaDeVigencia(DiaForaDeVigenciaException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
	}

}
