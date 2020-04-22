package com.etoak.utils;


import com.etoak.exception.ParamException;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

public class ValidationUtil {
    private static Validator validator;
    static{
       ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        validator =factory.getValidator();
    }
    public static void validate(Object object){
        Set<ConstraintViolation<Object>> violations=validator.validate(object);
       if(!CollectionUtils.isEmpty(violations)) {
           Iterator<ConstraintViolation<Object>> iterator = violations.iterator();
           StringBuffer mb = new StringBuffer();
           while (iterator.hasNext()) {
               ConstraintViolation<Object> violation=iterator.next();
               String message = violation.getMessage();
               mb.append(message).append(";");
               System.out.println(message);
           }
       }
       throw  new ParamException("参数校验异常");
    }
}
