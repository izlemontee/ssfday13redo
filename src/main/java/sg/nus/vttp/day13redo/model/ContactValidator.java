package sg.nus.vttp.day13redo.model;

import java.time.LocalDate;
import java.time.Period;
//import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactValidator implements ConstraintValidator<ContactValidation,LocalDate>{

    public boolean isValid(LocalDate DOB, ConstraintValidatorContext cxt){
        LocalDate now = LocalDate.now();

        if(DOB == null){return false;}

        int age = Period.between(DOB, now).getYears();
        int minAge = 10;
        int maxAge = 100;
        if((age < minAge) || (age > maxAge)){
            return false;
        }
        else{
            return true;
        }
    }
    
}
