package com.institute.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "institute")
public class Institute {
    protected Institute(){}

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;

    @Column(name = "institute_name")
    private String name;
    
    
	
	/*
	 * public Institute(String name, String code) { this.code = code; this.name =
	 * name; }
	 */
    
    public String getCode() {
    	return code;
    }
    
    public String getName() {
    	return name;
    }
    @Override
    public String toString()
    {
    	return "Institute [code =" + code + ", name = " + name + "]";
    }

}
