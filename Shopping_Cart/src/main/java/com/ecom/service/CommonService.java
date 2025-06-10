package com.ecom.service;



public interface CommonService {
	public abstract void removeSessionMessage(); 
	
	public abstract Double calculateDiscountPrice(Integer discount, Double price);
}
