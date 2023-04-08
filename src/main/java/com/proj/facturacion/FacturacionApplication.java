/*
* Segunda Entrega
*
 * Readme:
 * La aplicación crea el esquema de la base de datos desde Hibernate basandose en las definiciones de
 * los modelos y carga la data desde el archivo data.sql.
 * Sin embargo, la aplicación también puede crear el modelo de la base de datos desde el archivo schema.sql
 * Para crear el schema desde el archivo schema.sql, se deben de realizar los sguientes pasos:
 * 1.- Asegurarse que el nombre del archivo sea schema.sql, de no ser renombrar.
 * 2.- En la sección application.properties, comentar y descomentar las anotaciones para la inicialización desde el
 *     schema.sql
* */

package com.proj.facturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacturacionApplication {
	public static void main(String[] args) {
		SpringApplication.run(FacturacionApplication.class, args);
	}
}
