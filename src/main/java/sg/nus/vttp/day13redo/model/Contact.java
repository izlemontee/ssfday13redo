package sg.nus.vttp.day13redo.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Contact {
    
   // @Valid
    @NotEmpty(message = "Name required")
    @Size(min = 3, max = 64, message="Must be between 3 and 64 characters")
    private String name;

    //@Valid
    @NotEmpty(message = "Email required")
    @Email(message = "Must be valid email format")
    private String email;

    //@Valid
    @NotEmpty(message = "Phone number required")
    //@Digits(integer = 7,fraction = 0)
    //@Size(min = 7, message = "At least 7 characters")
    //@Min(1000000)
    @Pattern(regexp = "\\d+", message = "only digits")
    // the regex above indicates one or more digits with the plus
    private String phoneNo;

   // @Valid
    @NotNull(message = "Date required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Must be in the past")
    @ContactValidation(message = "Age must be between 10 and 100 years")
    private LocalDate dob;

    private String id;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    
}
