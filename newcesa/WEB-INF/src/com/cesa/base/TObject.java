package com.cesa.base;

/**
 * 최상위 클래스<br>
 * 모든 클래스는 TObject 를 상속받도록 한다.
 * 
 * @version 1.0
 * @author moon jong doek, 2008-10-22
 */
public class TObject {
	
	/**
	 * 기본 생성자
	 */
    public TObject() {
        super();
    }
    
    /**
	 * 클래스의 이름을 담는다.
	 */
	private String _name = "";

	/**
	 * 클래스 이름을 지정한다.
	 * @param String name
	 */
	public void _setName(String name) {
		this._name = name;
	}
	
	/**
	 * 클래스 이름을 리턴한다.
	 */
	public String _getName() {
		return this._name;
	}

	/**
	 * 스트링으로 리턴한다.<br>
	 * 현재는 상속받아서 만들어진 클래스의 이름을 간단히 리턴하도록 한다.
	 */
	public String toString() {
		return _name;
	}
}
