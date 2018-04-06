package com.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

		private static final long serialVersionUID = 1L;
		private String FieldName;
		private String FieldMessage;
		
		
		public FieldMessage() {
			
		}


		public FieldMessage(String fieldName, String fieldMessage) {
			super();
			FieldName = fieldName;
			FieldMessage = fieldMessage;
		}


		public String getFieldName() {
			return FieldName;
		}


		public void setFieldName(String fieldName) {
			FieldName = fieldName;
		}


		public String getFieldMessage() {
			return FieldMessage;
		}


		public void setFieldMessage(String fieldMessage) {
			FieldMessage = fieldMessage;
		}
		
		
		
		
	
		
		

}
