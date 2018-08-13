package com.naah.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author dazhi
 *
 */
@Getter
@Setter
public class Admin implements Serializable {


	private String id;
	private String pwd;
}
