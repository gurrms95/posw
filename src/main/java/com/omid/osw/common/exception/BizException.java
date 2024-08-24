package com.omid.osw.common.exception;

/**
 * 백엔드 예외 처리 클래스
 * 
 * @author psw
 * @date 2024. 08. 07
 */
public class BizException extends RuntimeException {

	public BizException(String message) {
		super(message);
	}

}
